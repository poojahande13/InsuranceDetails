package com.Xangars;

public class PolicyTable {

    private String  POLICY_NUMBER    ;
    private String CUSTOMER_ID       ;
    private String CUSTOMER_NAME     ;
    private String INSURED_NAME      ;
    private String PRODUCT_END_DATE  ;
    private String DOB               ;
    private String  EMAIL_ADDRESS    ;
    private String MOBILE_NUMBER     ;
    private String PREMIUM_MODE      ;
    private String POLICY_STATUS     ;
    private String CUSTOMER_PAN_NO   ;
    private String POLICY_ISSUANCE_DATE ;
    private String CONTACT_NUMBER_LAST_UPDATED;
    private String EMAIL_ADDRESS_LAST_UPDATED ;
    private String WHATSAPP_OPT_IN_STATUS     ;
    private String PRODUCT_NAME               ;
    private String PRODUCT_ID                 ;
    private String REINVEST_APPLICABLE        ;
    private String OUTSTANDING_PAYOUT         ;
    private String UNCLAIMED_AMOUNT           ;
    private String NEFT_REGISTERED            ;
    private String LAST_PREMIUM_PAID          ;
    private String MESSAGE;
    private String OTP;

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }




    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }


    private int  Status;



    public int getStatus() {
        return Status;
    }



    public void setStatus(int status) {
        Status = status;
    }




    public String getPOLICY_NUMBER() {
        return POLICY_NUMBER;
    }

    public void setPOLICY_NUMBER(String POLICY_NUMBER) {
        this.POLICY_NUMBER = POLICY_NUMBER;
    }

    public String getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(String CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }

    public String getINSURED_NAME() {
        return INSURED_NAME;
    }

    public void setINSURED_NAME(String INSURED_NAME) {
        this.INSURED_NAME = INSURED_NAME;
    }

    public String getPRODUCT_END_DATE() {
        return PRODUCT_END_DATE;
    }

    public void setPRODUCT_END_DATE(String PRODUCT_END_DATE) {
        this.PRODUCT_END_DATE = PRODUCT_END_DATE;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEMAIL_ADDRESS() {
        return EMAIL_ADDRESS;
    }

    public void setEMAIL_ADDRESS(String EMAIL_ADDRESS) {
        this.EMAIL_ADDRESS = EMAIL_ADDRESS;
    }

    public String getMOBILE_NUMBER() {
        return MOBILE_NUMBER;
    }

    public void setMOBILE_NUMBER(String MOBILE_NUMBER) {
        this.MOBILE_NUMBER = MOBILE_NUMBER;
    }

    public String getPREMIUM_MODE() {
        return PREMIUM_MODE;
    }

    public void setPREMIUM_MODE(String PREMIUM_MODE) {
        this.PREMIUM_MODE = PREMIUM_MODE;
    }

    public String getPOLICY_STATUS() {
        return POLICY_STATUS;
    }

    public void setPOLICY_STATUS(String POLICY_STATUS) {
        this.POLICY_STATUS = POLICY_STATUS;
    }

    public String getCUSTOMER_PAN_NO() {
        return CUSTOMER_PAN_NO;
    }

    public void setCUSTOMER_PAN_NO(String CUSTOMER_PAN_NO) {
        this.CUSTOMER_PAN_NO = CUSTOMER_PAN_NO;
    }

    public String getPOLICY_ISSUANCE_DATE() {
        return POLICY_ISSUANCE_DATE;
    }

    public void setPOLICY_ISSUANCE_DATE(String POLICY_ISSUANCE_DATE) {
        this.POLICY_ISSUANCE_DATE = POLICY_ISSUANCE_DATE;
    }

    public String getCONTACT_NUMBER_LAST_UPDATED() {
        return CONTACT_NUMBER_LAST_UPDATED;
    }

    public void setCONTACT_NUMBER_LAST_UPDATED(String CONTACT_NUMBER_LAST_UPDATED) {
        this.CONTACT_NUMBER_LAST_UPDATED = CONTACT_NUMBER_LAST_UPDATED;
    }

    public String getEMAIL_ADDRESS_LAST_UPDATED() {
        return EMAIL_ADDRESS_LAST_UPDATED;
    }

    public void setEMAIL_ADDRESS_LAST_UPDATED(String EMAIL_ADDRESS_LAST_UPDATED) {
        this.EMAIL_ADDRESS_LAST_UPDATED = EMAIL_ADDRESS_LAST_UPDATED;
    }

    public String getWHATSAPP_OPT_IN_STATUS() {
        return WHATSAPP_OPT_IN_STATUS;
    }

    public void setWHATSAPP_OPT_IN_STATUS(String WHATSAPP_OPT_IN_STATUS) {
        this.WHATSAPP_OPT_IN_STATUS = WHATSAPP_OPT_IN_STATUS;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public void setPRODUCT_NAME(String PRODUCT_NAME) {
        this.PRODUCT_NAME = PRODUCT_NAME;
    }

    public String getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public void setPRODUCT_ID(String PRODUCT_ID) {
        this.PRODUCT_ID = PRODUCT_ID;
    }

    public String getREINVEST_APPLICABLE() {
        return REINVEST_APPLICABLE;
    }

    public void setREINVEST_APPLICABLE(String REINVEST_APPLICABLE) {
        this.REINVEST_APPLICABLE = REINVEST_APPLICABLE;
    }

    public String getOUTSTANDING_PAYOUT() {
        return OUTSTANDING_PAYOUT;
    }

    public void setOUTSTANDING_PAYOUT(String OUTSTANDING_PAYOUT) {
        this.OUTSTANDING_PAYOUT = OUTSTANDING_PAYOUT;
    }

    public String getUNCLAIMED_AMOUNT() {
        return UNCLAIMED_AMOUNT;
    }

    public void setUNCLAIMED_AMOUNT(String UNCLAIMED_AMOUNT) {
        this.UNCLAIMED_AMOUNT = UNCLAIMED_AMOUNT;
    }

    public String getNEFT_REGISTERED() {
        return NEFT_REGISTERED;
    }

    public void setNEFT_REGISTERED(String NEFT_REGISTERED) {
        this.NEFT_REGISTERED = NEFT_REGISTERED;
    }

    public String getLAST_PREMIUM_PAID() {
        return LAST_PREMIUM_PAID;
    }

    public void setLAST_PREMIUM_PAID(String LAST_PREMIUM_PAID) {
        this.LAST_PREMIUM_PAID = LAST_PREMIUM_PAID;
    }
    @Override
    public String toString() {
        return "PolicyTable{" +
                "POLICY_NUMBER='" + POLICY_NUMBER + '\'' +
                ", CUSTOMER_ID='" + CUSTOMER_ID + '\'' +
                ", CUSTOMER_NAME='" + CUSTOMER_NAME + '\'' +
                ", INSURED_NAME='" + INSURED_NAME + '\'' +
                ", PRODUCT_END_DATE='" + PRODUCT_END_DATE + '\'' +
                ", DOB='" + DOB + '\'' +
                ", EMAIL_ADDRESS='" + EMAIL_ADDRESS + '\'' +
                ", MOBILE_NUMBER='" + MOBILE_NUMBER + '\'' +
                ", PREMIUM_MODE='" + PREMIUM_MODE + '\'' +
                ", POLICY_STATUS='" + POLICY_STATUS + '\'' +
                ", CUSTOMER_PAN_NO='" + CUSTOMER_PAN_NO + '\'' +
                ", POLICY_ISSUANCE_DATE='" + POLICY_ISSUANCE_DATE + '\'' +
                ", CONTACT_NUMBER_LAST_UPDATED='" + CONTACT_NUMBER_LAST_UPDATED + '\'' +
                ", EMAIL_ADDRESS_LAST_UPDATED='" + EMAIL_ADDRESS_LAST_UPDATED + '\'' +
                ", WHATSAPP_OPT_IN_STATUS='" + WHATSAPP_OPT_IN_STATUS + '\'' +
                ", PRODUCT_NAME='" + PRODUCT_NAME + '\'' +
                ", PRODUCT_ID='" + PRODUCT_ID + '\'' +
                ", REINVEST_APPLICABLE='" + REINVEST_APPLICABLE + '\'' +
                ", OUTSTANDING_PAYOUT='" + OUTSTANDING_PAYOUT + '\'' +
                ", UNCLAIMED_AMOUNT='" + UNCLAIMED_AMOUNT + '\'' +
                ", NEFT_REGISTERED='" + NEFT_REGISTERED + '\'' +
                ", LAST_PREMIUM_PAID='" + LAST_PREMIUM_PAID + '\'' +
                '}';
    }

}









