
package com.Xangars;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.*;

public class WhatsappoptinStep2 implements Processor {


    static String  POLICY_NUMBER = "policyNumber";
    static String CUSTOMER_ID =    "customerID";
    static String CUSTOMER_NAME  = "customerName"   ;
    static String INSURED_NAME   = "insuredName"   ;
    static String PRODUCT_END_DATE = "productEndDate" ;
    static String DOB = "dob"     ;
    static String  EMAIL_ADDRESS = "emailAddress"   ;
    static String MOBILE_NUMBER  = "mobileNumber"   ;
    static String PREMIUM_MODE   =  "premiumMode"  ;
    static String POLICY_STATUS  =  "policyStatus"  ;
    static String CUSTOMER_PAN_NO=  "customerPanNo"  ;
    static String POLICY_ISSUANCE_DATE = "policyIssuranceDate" ;
    static String CONTACT_NUMBER_LAST_UPDATED = "contactNumberLastUpdated";
    static String EMAIL_ADDRESS_LAST_UPDATED = "emailAddressLastUpdated" ;
    static String WHATSAPP_OPT_IN_STATUS   = "whatsappOptInStatus"  ;
    static String PRODUCT_NAME    = "productName"           ;
    static String PRODUCT_ID = "productId"              ;
    static String REINVEST_APPLICABLE   = "reinvestApplicable"     ;
    static String OUTSTANDING_PAYOUT    = "outstandingPayout"   ;
    static String UNCLAIMED_AMOUNT      = "unclaimedAmount"  ;
    static String NEFT_REGISTERED       = "neftRegistered" ;
    static String LAST_PREMIUM_PAID     = "lastPremiumPaid"    ;
    static String OTP = "OTP";



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

            PolicyTable whatopt2 = new PolicyTable();


            ChangeRequest  newRequestForwhat2= new ChangeRequest();
//            System.out.println("getting todays Date");
//            String DateOfInitiation = getDate();



            for(int i = 0; i<jsonarr.size();i++) {

                JSONObject jobj = (JSONObject)jsonarr.get(i);
                System.out.println(jobj);


                if(jobj.get(WhatsappoptinStep2.MOBILE_NUMBER) != null)  {
                    whatopt2.setMOBILE_NUMBER(jobj.get(WhatsappoptinStep2.MOBILE_NUMBER).toString());
                }

                if (jobj.get(WhatsappoptinStep2.OTP)!= null) {
                    whatopt2.setOTP(jobj.get(WhatsappoptinStep2.OTP).toString());

                }
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "Admin", "admin1234");
                    Statement stmt = con.createStatement();

                    String currentTime = GenerateOTP.getDateTime();
                    String query = " Select MOBILE_NUMBER  from Temp_User_Table  where MOBILE_NUMBER = " + whatopt2.getMOBILE_NUMBER() + " AND OTP = " + whatopt2.getOTP()+" AND "+
                            " EXPIRE_TIME >= TO_DATE('" + currentTime + "','YYMMDDHH24MISS')";
                    System.out.println("Query is " + query);
                    ResultSet rs = stmt.executeQuery(query);
                    boolean recordFoundinTempUser = false;
                    if (rs.next()) {
                        recordFoundinTempUser = true;
                        System.out.println("Record Found in Temp USer Table");
                    }

                    if (recordFoundinTempUser) {
                        String GETQUERY = " Select POLICY_NUMBER from Policy_table where MOBILE_NUMBER = " + whatopt2.getMOBILE_NUMBER();
                        System.out.println("Query is " + GETQUERY);
                        boolean recordFound_1 = false;
                        rs = stmt.executeQuery(GETQUERY);
                        if (rs.next()) {
                            recordFound_1 = true;
                            whatopt2.setPOLICY_NUMBER(rs.getString("POLICY_NUMBER"));
                            System.out.println("POLICY_NUMBER found in  Policy_table");
                        }
                        if (recordFound_1) {
                            //stmt = con.createStatement();
                            System.out.println("Inserting record in whatsoptin table");
                            String OPTINID = "OPT" + whatopt2.getPOLICY_NUMBER();
                            String insertQuery = "INSERT INTO Whatsapp_Optin_Table(OPTIN_ID,MOBILE_NUMBER,POLICY_NUMBER,OPTIN_DATE) VALUES ('" +
                                    OPTINID + "'," + whatopt2.getMOBILE_NUMBER()+ "," +whatopt2.getPOLICY_NUMBER() + ",TO_DATE('" + currentTime + "','YYMMDDHH24MISS'))";
                            System.out.println(insertQuery);
                            stmt.executeUpdate(insertQuery);
                            newRequestForwhat2.setMessage("Thank You for Optin Request Completed");
                            newRequestForwhat2.setStatus(1001);
                        } else {
                            newRequestForwhat2.setMessage("MOBILE_NUMBER not exist in POLICY_DETAILS");
                            System.out.println("MOBILE_NUMBER not exist in POLICY_DETAILS");
                        }

                    } else {
                        newRequestForwhat2.setMessage("OTP/Mobile Number is incorrect or expired");
                    }
                    con.close();
                }catch(Exception e)
                { System.out.println(e);

                }
                /* System.out.println(mobileUpdatedetails.toString());*/
            }
            String newRequestForwhatsapp2 = null;
            //System.out.println(ListOfPaymentPOJO);

            newRequestForwhatsapp2 = gson.toJson(newRequestForwhat2);

            System.out.println("\nfinal New Request For Email  JSON Body " + newRequestForwhatsapp2);
            exchange.getIn().setBody(newRequestForwhatsapp2);

        }

        catch (Exception e ) {
            System.out.println("Error Occured in InputFileParser.java file... Unsucessful termination");
            e.printStackTrace();
        }

    }
}
