
package com.Xangars;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerPolicyInfo implements Processor {


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

            PolicyTable policydetails = new PolicyTable();

//            System.out.println("getting todays Date");
//            String DateOfInitiation = getDate();



            for(int i = 0; i<jsonarr.size();i++) {
                JSONObject jobj = (JSONObject)jsonarr.get(i);
                System.out.println(jobj);


                if(jobj.get(CustomerPolicyInfo.POLICY_NUMBER) != null) {
                    policydetails.setPOLICY_NUMBER(jobj.get(CustomerPolicyInfo.POLICY_NUMBER).toString());
                }

                try{

                    Class.forName("oracle.jdbc.driver.OracleDriver");


                    Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Admin","admin1234");
                    Statement stmt=con.createStatement();

                    String query = "Select * from Policy_Table  where Policy_Number = " + policydetails.getPOLICY_NUMBER();
                    System.out.println("Query is " + query);
                    ResultSet rs=stmt.executeQuery(query);

                    while(rs.next())  {
                        System.out.println(rs.getInt(1) + rs.getString(2) + " " +rs.getString(3)+" "+rs.getString(4));
                        policydetails.setCUSTOMER_ID(rs.getString("CUSTOMER_ID"));
                        policydetails.setCUSTOMER_NAME(rs.getString("CUSTOMER_NAME"));
                        policydetails.setINSURED_NAME(rs.getString("INSURED_NAME"));
                        policydetails.setPRODUCT_END_DATE(rs.getString("PRODUCT_END_DATE"));
                        policydetails.setDOB(rs.getString("DOB"));
                        policydetails.setEMAIL_ADDRESS(rs.getString("EMAIL_ADDRESS"));
                        policydetails.setMOBILE_NUMBER(rs.getString("MOBILE_NUMBER"));
                        policydetails.setPREMIUM_MODE(rs.getString("PREMIUM_MODE"));
                        policydetails.setPOLICY_STATUS(rs.getString("POLICY_STATUS"));
                        policydetails.setCUSTOMER_PAN_NO(rs.getString("CUSTOMER_PAN_NO"));

                        policydetails.setPOLICY_ISSUANCE_DATE(rs.getString("POLICY_ISSUANCE_DATE"));
                        policydetails.setCONTACT_NUMBER_LAST_UPDATED(rs.getString("CONTACT_NUMBER_LAST_UPDATED"));
                        policydetails.setEMAIL_ADDRESS_LAST_UPDATED(rs.getString("EMAIL_ADDRESS_LAST_UPDATED"));
                        policydetails.setWHATSAPP_OPT_IN_STATUS(rs.getString("WHATSAPP_OPT_IN_STATUS"));
                        policydetails.setPRODUCT_NAME(rs.getString("PRODUCT_NAME"));


                        policydetails.setPRODUCT_ID(rs.getString("PRODUCT_ID"));
                        policydetails.setREINVEST_APPLICABLE(rs.getString("REINVEST_APPLICABLE"));
                        policydetails.setOUTSTANDING_PAYOUT(rs.getString("OUTSTANDING_PAYOUT"));
                        policydetails.setUNCLAIMED_AMOUNT(rs.getString("UNCLAIMED_AMOUNT"));
                        policydetails.setNEFT_REGISTERED(rs.getString("NEFT_REGISTERED"));

                        policydetails.setLAST_PREMIUM_PAID(rs.getString("LAST_PREMIUM_PAID"));





                        // set al the remaining feiids similarly.
                    }

                    System.out.println("in a while loop ");
                    con.close();

                }catch(Exception e)
                { System.out.println(e);

                }
                System.out.println(policydetails.toString());
            }
            String policystr = null;
            //System.out.println(ListOfPaymentPOJO);

            policystr = gson.toJson(policydetails);

            System.out.println("\nfinal Paymenet CDM JSON Body " + policystr);
            exchange.getIn().setBody(policystr);
        }
        catch (Exception e ) {
            System.out.println("Error Occured in InputFileParser.java file... Unsucessful termination");
            e.printStackTrace();
        }

    }


}
