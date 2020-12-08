
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

public class MobileNumberValidation implements Processor {



    static String DOB = "dob"     ;
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

            PolicyTable mobiledetails = new PolicyTable();



            for(int i = 0; i<jsonarr.size();i++) {
                JSONObject jobj = (JSONObject)jsonarr.get(i);
                System.out.println(jobj);

                if (jobj.get(MobileNumberValidation.DOB)!= null) {
                    mobiledetails.setDOB(jobj.get(MobileNumberValidation.DOB).toString());
                }
                if (jobj.get(MobileNumberValidation.MOBILE_NUMBER) != null) {
                    mobiledetails.setMOBILE_NUMBER(jobj.get(MobileNumberValidation.MOBILE_NUMBER).toString());
                }


                if(!Validator.validateMobileNumber((mobiledetails.getMOBILE_NUMBER()))) {
                        mobiledetails.setMESSAGE("Mobile Number You entered is not  a Valid Mobile Number");


                } else {
                    try{
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Admin","admin1234");
                        Statement stmt=con.createStatement();
                        // String query = "Select * from Policy_Table  where EMAIL_ADDRESS = " + Emaildetails.getEMAIL_ADDRESS() + "AND DOB = "+ Emaildetails.getDOB();
                        String query= " Select MOBILE_NUMBER from Policy_Table  where MOBILE_NUMBER =" + mobiledetails.getMOBILE_NUMBER() + " AND DOB = TO_DATE('" +mobiledetails.getDOB() + "', 'DD-MM-YYYY')";
                        System.out.println("Query is " + query);
                        ResultSet rs=stmt.executeQuery(query);
                        boolean recordFound = false;
                        if(rs.next())  {
                            recordFound=true;

                        }

                        if (recordFound){

                            mobiledetails.setMESSAGE("MOBILE NUMBER Sucessfully Validated");
                            mobiledetails.setStatus(1005);

                        } else {
                            mobiledetails.setMESSAGE("MOBILE NUMBER OR  DOB provided is not Present In DataBase");
                        }

                        con.close();

                    }catch(Exception e)
                    {
                        System.out.println(e);
                    }
                }

                System.out.println(mobiledetails.toString());
            }
            String MobileValidationstr = null;


        MobileValidationstr = gson.toJson(mobiledetails);

            System.out.println("\nfinal  MobileValidationstr  JSON Body " + MobileValidationstr);
            exchange.getIn().setBody(MobileValidationstr);
        }
        catch (Exception e ) {
            System.out.println("Error Occured in MobileNumberValidation.java file... Unsucessful termination");
            e.printStackTrace();
        }

    }


}
