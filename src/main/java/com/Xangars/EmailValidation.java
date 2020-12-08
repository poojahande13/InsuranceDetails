
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

public class EmailValidation implements Processor {


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

            PolicyTable Emaildetails = new PolicyTable();
            ChangeRequest Emailvalidation =  new ChangeRequest();
//            System.out.println("getting todays Date");
//            String DateOfInitiation = getDate();


            for(int i = 0; i<jsonarr.size();i++) {
                JSONObject jobj = (JSONObject)jsonarr.get(i);
                System.out.println(jobj);

                if (jobj.get(EmailValidation.DOB)!= null) {
                    Emaildetails.setDOB(jobj.get(EmailValidation.DOB).toString());
                }

                if(jobj.get(EmailValidation.EMAIL_ADDRESS) != null)  {
                    Emaildetails.setEMAIL_ADDRESS(jobj.get(EmailValidation.EMAIL_ADDRESS).toString());

                }
                if(!Validator.validateEmailAddress(Emaildetails.getEMAIL_ADDRESS())) {
                    Emaildetails.setMESSAGE("Email Address You entered is not valid");
                }
                else {
                    try{
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Admin","admin1234");
                        Statement stmt=con.createStatement();
                        // String query = "Select * from Policy_Table  where EMAIL_ADDRESS = " + Emaildetails.getEMAIL_ADDRESS() + "AND DOB = "+ Emaildetails.getDOB();
                        String query= " Select Email_Address from Policy_Table  where Email_Address = '" + Emaildetails.getEMAIL_ADDRESS() + "' AND DOB =TO_DATE('" +Emaildetails.getDOB() + "', 'DD-MM-YYYY')";
                        System.out.println("Query is " + query);
                        ResultSet rs=stmt.executeQuery(query);
                        boolean recordFound = false;
                        while(rs.next())  {
                            //System.out.println(rs.getInt(1) + rs.getString(2) + " " +rs.getString(3)+" "+rs.getString(4));
                            recordFound=true;
                            Emaildetails.setEMAIL_ADDRESS(rs.getString("EMAIL_ADDRESS"));
                            // set al the remaining feiids similarly.
                        }
                        if (recordFound){
                            Emaildetails.setMESSAGE("Email Address Sucessfully Validated");
                            Emaildetails.setStatus(1005);
                        } else {
                            Emaildetails.setMESSAGE("Incorrect Email Address OR  DOB provided/Not Present in Database");
                        }
                        con.close();
                    }catch(Exception e)
                    {
                        System.out.println(e);
                    }

                }


                //System.out.println(Emaildetails.toString());
            }
            String EmailValidationstr = null;
            //System.out.println(ListOfPaymentPOJO);

            EmailValidationstr = gson.toJson(Emaildetails);

            System.out.println("\nfinal EmailValidationstr JSON Body " + EmailValidationstr);
            exchange.getIn().setBody(EmailValidationstr);
        }
        catch (Exception e ) {
            System.out.println("Error Occured in EmailValidation.java file... Unsucessful termination");
            e.printStackTrace();
        }

    }


}
