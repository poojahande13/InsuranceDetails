
package com.Xangars;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class WhatsappoptinStep1 implements Processor {



    static String MOBILE_NUMBER  = "mobileNumber"   ;




    public void process(Exchange exchange) throws Exception {

        try {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(double.class, new DoubleTypeAdapter())
                    .registerTypeAdapter(Double.class, new DoubleTypeAdapter()).create();

            String receievedBody = exchange.getIn().getBody().toString();


            System.out.println("received Body" + receievedBody);


            Object obj = new JSONParser().parse(receievedBody);

            JSONArray jsonarr;

            jsonarr = new JSONArray();
            jsonarr.add(obj);
            System.out.println("mapper : Found Single JSON Object");

            PolicyTable whatopt1 = new PolicyTable();


            ChangeRequest  newRequestForwhat1= new ChangeRequest();
//            System.out.println("getting todays Date");
//            String DateOfInitiation = getDate();



            for(int i = 0; i<jsonarr.size();i++) {

                JSONObject jobj = (JSONObject)jsonarr.get(i);
                System.out.println(jobj);


                if(jobj.get(WhatsappoptinStep1.MOBILE_NUMBER) != null)  {
                    whatopt1.setMOBILE_NUMBER(jobj.get(WhatsappoptinStep1.MOBILE_NUMBER).toString());
                }

                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "Admin", "admin1234");
                    Statement stmt = con.createStatement();

                    // String query = "Select * from Policy_Table  where EMAIL_ADDRESS = " + Emaildetails.getEMAIL_ADDRESS() + "AND DOB = "+ Emaildetails.getDOB();
                    String query = " Select MOBILE_NUMBER from Whatsapp_Optin_Table where MOBILE_NUMBER = "+whatopt1.getMOBILE_NUMBER();

                    System.out.println("Query is " + query);
                    ResultSet rs = stmt.executeQuery(query);

                    boolean recordFound = false;

                    if (rs.next()) {
                        recordFound = true;
                        System.out.println("found records in Whatsapp_Optin_Table");
                    }
                    if (recordFound) {
                        newRequestForwhat1.setMessage("Thank You for Optin Request Completed");
                        newRequestForwhat1.setStatus(1001);
                        System.out.println("Mobile Number found in Whatsapp_Optin_Table ");
                    } else {
                        //newRequestForwhat1.setMessage("");

                        System.out.println("Mobile number not opted fir whatsapp alerts");
                        stmt = con.createStatement();
                        String Checkquery = " Select MOBILE_NUMBER from Policy_table where MOBILE_NUMBER = " + whatopt1.getMOBILE_NUMBER();
                        System.out.println("Query is " + Checkquery);
                        boolean recordFound_1 = false;
                        rs = stmt.executeQuery(Checkquery);
                        if (rs.next()) {
                            recordFound_1 = true;
                            //whatopt1.setMOBILE_NUMBER(rs.getString("MOBILE_NUMBER"));
                            System.out.println("Mobile Number found in  Policy_table");
                        }
                        if(recordFound_1) {

                            String OTP = GenerateOTP.generateOTP(GenerateOTP.OTP_LENGTH);
                            //stmt=con.createStatement();

                            String currentTime = GenerateOTP.getDateTime();
                            String ExpiryTime = GenerateOTP.ExpiryTime();
                            System.out.println(currentTime + " " + OTP + ExpiryTime);



                            String insertQuery = "INSERT INTO Temp_User_Table(Mobile_Number,OTP,Expire_Time,Created_Time) VALUES ("+
                                    whatopt1.getMOBILE_NUMBER() + " ,'" + OTP + "',TO_DATE('"+ExpiryTime+"','YYMMDDHH24MISS'),TO_DATE('"+currentTime+"','YYMMDDHH24MISS'))";
                                     System.out.println(insertQuery);
                                     stmt.executeUpdate(insertQuery);
                            newRequestForwhat1.setMessage("Optin OTP Sent on the Mobile Number");
                            newRequestForwhat1.setStatus(1003);
                            System.out.println("sent otp to registered customer");
                        } else {
                            // set errror status
                            newRequestForwhat1.setMessage("Mobile is not a registred with Policy Holder");
                            System.out.println("Mobile is not a registred with Policy Holder");
                        }

                    }
                    con.close();
                } catch(Exception e)
                {
                    System.out.println(e);
                }
                //System.out.println(whatopt1.toString());
            }
            String responseString = null;
            //System.out.println(ListOfPaymentPOJO);

            responseString = gson.toJson(newRequestForwhat1);

            System.out.println("\nfinal  what opt 1 JSON Body " + responseString);
            exchange.getIn().setBody(responseString);
        }


        catch (Exception e ) {
            System.out.println("Error Occured in InputFileParser.java file... Unsucessful termination");
            e.printStackTrace();
        }

    }


}


