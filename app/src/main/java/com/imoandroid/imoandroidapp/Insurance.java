package com.imoandroid.imoandroidapp;

/**
 * Created by namrataprabhu on 4/11/15.
 */
public class Insurance{
    int ContractorCode;

    public String getContractorName() {
        return ContractorName;
    }

    public void setContractorName(String contractorName) {
        ContractorName = contractorName;
    }

    public int getContractorCode() {
        return ContractorCode;
    }

    public void setContractorCode(int contractorCode) {
        ContractorCode = contractorCode;
    }

    String ContractorName;

    public Insurance(){

    }

    public Insurance(int contractorCode , String contractorName){
        this.ContractorCode = contractorCode;
        this.ContractorName = ContractorName;
    }


}
