package com.imoandroid.imoandroidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mayoorrai on 2/8/15.
 */
public class PatientAddress implements Parcelable{

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

    public void setState(String in) {
        this.state = State.valueOfNameOrAbbr(in);
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

    public PatientAddress(){}

    public PatientAddress(Parcel in)
    {
        String [] data = new String[4];

        in.readStringArray(data);
        this.address1 = data[0];
        this.address2 = data[1];
        this.city = data[2];
        this.state = State.valueOfNameOrAbbr(data[3]);
        this.zip = in.readInt();
        this.mobilePhone = in.readLong();
        this.homePhone = in.readLong();
        this.officePhone = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]
                {
                        this.address1,
                        this.address2,
                        this.city,
                        this.state.toString()
        });
        dest.writeInt(zip);
        dest.writeLong(this.mobilePhone);
        dest.writeLong(this.homePhone);
        dest.writeLong(this.officePhone);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PatientAddress createFromParcel(Parcel in) {
            return new PatientAddress(in);
        }

        public PatientAddress[] newArray(int size) {
            return new PatientAddress[size];
        }
    };
}
