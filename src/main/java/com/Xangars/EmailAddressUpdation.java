
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

public class EmailAddressUpdation implements Processor {


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

            PolicyTable Emailupdatedetails = new PolicyTable();


            ChangeRequest  newRequestForEmail = new ChangeRequest();
//            System.out.println("getting todays Date");
//            String DateOfInitiation = getDate();



            for(int i = 0; i<jsonarr.size();i++) {

                String newEmailAddress = "";
                JSONObject jobj = (JSONObject)jsonarr.get(i);
                System.out.println(jobj);


                if(jobj.get(EmailAddressUpdation.EMAIL_ADDRESS) != null)  {
                    newEmailAddress=jobj.get(EmailAddressUpdation.EMAIL_ADDRESS).toString();


                }

                if (jobj.get(EmailAddressUpdation.POLICY_NUMBER)!= null) {
                    Emailupdatedetails.setPOLICY_NUMBER(jobj.get(EmailAddressUpdation.POLICY_NUMBER).toString());

                }


                try{

                    Class.forName("oracle.jdbc.driver.OracleDriver");


                    Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Admin","admin1234");
                    Statement stmt=con.createStatement();

                   // String query = "Select * from Policy_Table  where EMAIL_ADDRESS = " + Emaildetails.getEMAIL_ADDRESS() + "AND DOB = "+ Emaildetails.getDOB();
                   String query= " Select Email_Address from Policy_Table  where POLICY_NUMBER = " + Emailupdatedetails.getPOLICY_NUMBER();

                    System.out.println("Query is " + query);
                    ResultSet rs=stmt.executeQuery(query);

                    boolean recordFound = false;

                    while(rs.next())  {

                         recordFound = true;
                        Emailupdatedetails.setEMAIL_ADDRESS(rs.getString("EMAIL_ADDRESS"));
                        System.out.println(Emailupdatedetails.getEMAIL_ADDRESS());
                    }
                    if (recordFound){
                        if(newEmailAddress.equals(Emailupdatedetails.getEMAIL_ADDRESS())){
                            newRequestForEmail.setMessage("Email Address is already present");
                            System.out.println("Already present");
                        }else {

                            newRequestForEmail.generateRequestId();
                            newRequestForEmail.setMessage("Service Request for Emai Address Updation Generated");


                            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Admin","admin1234");
                            stmt=con.createStatement();


                            String insertQuery = "INSERT INTO LOG_TABLE(RequestNumber,policyNumber,newValue) VALUES('"+
                                    newRequestForEmail.getrequestId() + "' ," + Emailupdatedetails.getPOLICY_NUMBER() + ",'" + newEmailAddress+ "')";
                            System.out.println(insertQuery);
                            stmt.executeUpdate(insertQuery);

                            con.close();
                        }
                    }else{
                        newRequestForEmail.setMessage("Policy number does not exists in DB ");
                    }
                    con.close();

                }catch(Exception e)
                { System.out.println(e);

                }
                System.out.println(Emailupdatedetails.toString());
            }
            String newRequestForEmailUpdate = null;
            //System.out.println(ListOfPaymentPOJO);

            newRequestForEmailUpdate = gson.toJson(newRequestForEmail);

            System.out.println("\nfinal Paymenet CDM JSON Body " + newRequestForEmailUpdate);
            exchange.getIn().setBody(newRequestForEmailUpdate);
        }
        catch (Exception e ) {
            System.out.println("Error Occured in InputFileParser.java file... Unsucessful termination");
            e.printStackTrace();
        }

    }


}
