package com.Xangars;

public class ChangeRequest {

    private static int SequenceNo = 1000;
    private static String prefix = "SR";
    private String requestId ;
    private String policyNumber;
    private String fieldName   ;
    private String currentValue ;
    private String newValue    ;
    private String time        ;
    private String processed   ;


    private int  Status;

    private String Message;

    public int getStatus() {
        return Status;
    }



    public void setStatus(int status) {
        Status = status;
    }



    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }



    public void generateRequestId() {

        this.requestId = prefix + SequenceNo;
        SequenceNo++;
    }
    public  String getrequestId() {
        return requestId;
    }

    public void setrequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProcessed() {
        return processed;
    }

    public void setProcessed(String processed) {
        this.processed = processed;
    }

}
