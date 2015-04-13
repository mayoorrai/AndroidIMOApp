package com.imoandroid.imoandroidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by namrataprabhu on 4/11/15.
 */
public class Insurance implements Parcelable{
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

    public Insurance(Parcel in)
    {
        this.ContractorCode = in.readInt();
        this.ContractorName = in.readString();
    }

    public Insurance(int contractorCode , String contractorName){
        this.ContractorCode = contractorCode;
        this.ContractorName = ContractorName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ContractorCode);
        dest.writeString(this.ContractorName);
    }

    public static final Parcelable.Creator<Insurance> CREATOR
            = new Parcelable.Creator<Insurance>() {
        public Insurance createFromParcel(Parcel in) {
            return new Insurance(in);
        }

        public Insurance[] newArray(int size) {
            return new Insurance[size];
        }
    };
}
