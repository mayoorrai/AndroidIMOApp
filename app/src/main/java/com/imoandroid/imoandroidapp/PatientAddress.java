package com.imoandroid.imoandroidapp;

/**
 * Created by mayoorrai on 2/8/15.
 */
public class PatientAddress {

    // fields
    String address1;
    String address2;
    String city;
    State state;
    int zip;
    long mobilePhone;
    long homePhone;
    long officePhone;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public long getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public long getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(long homePhone) {
        this.homePhone = homePhone;
    }

    public long getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(long officePhone) {
        this.officePhone = officePhone;
    }





}
