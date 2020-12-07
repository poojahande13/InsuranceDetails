package com.Xangars;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.simple.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

public class processData implements Processor {


    public void process(Exchange exchange) throws Exception {


        JSONObject obj = new JSONObject();
        obj.put("converstnId",exchange.getProperty("converstnId") );
        obj.put("msgId",exchange.getExchangeId() );
        obj.put("serviceName",exchange.getProperty("serviceName") );
        obj.put("message",exchange.getProperty("LogMessage") );
        obj.put("logType",exchange.getProperty("LogType") );
        obj.put("headers",exchange.getMessage().getHeaders().toString() );
        obj.put("properties",exchange.getProperties().toString() );
        obj.put("serviceId", exchange.getProperty("ServiceId"));

        if(exchange.getIn().getBody()!=null){
            obj.put("body",exchange.getIn().getBody().toString());
        }
        else{
            obj.put("body","");
        }

        if (exchange.getProperty("boId") == null)
        {
            obj.put("boId",null);
        }
        else
        {
            obj.put("boId",exchange.getProperty("boId"));
        }
        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        if (e == null && exchange.getProperty("LogType").equals("ERROR"))
        {
            obj.put("exceptionMessage", exchange.getProperty("errorDescription"));
        }

        if(e != null) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            obj.put("exceptionMessage", e.getMessage());
            obj.put("exceptionStackTrace", exceptionAsString);
        }
        exchange.getIn().setBody(obj.toJSONString());

    }
}