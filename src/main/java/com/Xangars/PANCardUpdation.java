
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

public class PANCardUpdation implements Processor {


    static String  POLICY_NUMBER = "policyNumber";
    static String CUSTOMER_PAN_NO=  "customerPanNo"  ;

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

            PolicyTable PanCardUpdatedetails = new PolicyTable();
            ChangeRequest newRequestforPanCard = new ChangeRequest();
//            System.out.println("getting todays Date");
//            String DateOfInitiation = getDate();



            for(int i = 0; i<jsonarr.size();i++) {
                String newPanCardNumber = "";
                JSONObject jobj = (JSONObject)jsonarr.get(i);
                System.out.println(jobj);


                if(jobj.get(PANCardUpdation.CUSTOMER_PAN_NO) != null)  {
                    newPanCardNumber = jobj.get(PANCardUpdation.CUSTOMER_PAN_NO).toString();
                }

                if (jobj.get(PANCardUpdation.POLICY_NUMBER)!= null) {
                    PanCardUpdatedetails.setPOLICY_NUMBER(jobj.get(PANCardUpdation.POLICY_NUMBER).toString());
                }

                try{
                    Class.forName("oracle.jdbc.driver.OracleDriver");

                    Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Admin","admin1234");
                    Statement stmt=con.createStatement();

                   // String query = "Select * from Policy_Table  where EMAIL_ADDRESS = " + Emaildetails.getEMAIL_ADDRESS() + "AND DOB = "+ Emaildetails.getDOB();
                   String query= " Select CUSTOMER_PAN_NO from Policy_Table  where POLICY_NUMBER =" + PanCardUpdatedetails.getPOLICY_NUMBER();

                    System.out.println("Query is " + query);
                    ResultSet rs=stmt.executeQuery(query);

                    boolean recordFound = false;

                    while(rs.next())  {
                        recordFound= true;
                        PanCardUpdatedetails.setCUSTOMER_PAN_NO(rs.getString("CUSTOMER_PAN_NO"));
                        System.out.println(PanCardUpdatedetails.getCUSTOMER_PAN_NO());
                    }
                    con.close();

                    if (recordFound){
                        if(newPanCardNumber.equals(PanCardUpdatedetails.getCUSTOMER_PAN_NO())) {
                            newRequestforPanCard.setMessage("PAN Card number already exists");
                            System.out.println("Already present");

                        }else {

                            newRequestforPanCard.generateRequestId();
                            newRequestforPanCard.setMessage("Service Request for PAN Card Updation Generated");
                            newRequestforPanCard.setStatus(1004);



                            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Admin","admin1234");
                            stmt=con.createStatement();


                            String insertQuery = "INSERT INTO LOG_TABLE(RequestNumber,policyNumber,newValue) VALUES('"+
                                    newRequestforPanCard.getrequestId() + "' ," + PanCardUpdatedetails.getPOLICY_NUMBER() + ",'" + newPanCardNumber+ "')";
                            System.out.println(insertQuery);
                            stmt.executeUpdate(insertQuery);

                            con.close();
                        }

                    } else {
                        // raise error
                        newRequestforPanCard.setMessage("Policy number does not exists in DB ");
                    }

                    con.close();

                }catch(Exception e)
                { System.out.println(e);

                }
               /* System.out.println(mobileUpdatedetails.toString());*/
            }
            String newRequestForPAN = null;
            //System.out.println(ListOfPaymentPOJO);

            newRequestForPAN = gson.toJson(newRequestforPanCard);

            System.out.println("\nfinal New Request For PAN JSON Body " + newRequestForPAN);
            exchange.getIn().setBody(newRequestForPAN);
        }
        catch (Exception e ) {
            System.out.println("Error Occured in InputFileParser.java file... Unsucessful termination");
            e.printStackTrace();
        }

    }


}
