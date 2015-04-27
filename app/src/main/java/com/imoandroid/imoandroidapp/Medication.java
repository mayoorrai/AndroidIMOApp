package com.imoandroid.imoandroidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by namrataprabhu on 4/11/15.
 */
public class Medication implements Parcelable{

    public String InterfaceSource;
    public String InterfaceCode;
    public String InterfaceTitle;
    public String AdminSource;
    public String AdminCode;
    public String AdminTitle;

    public Medication(){

    }

    public Medication(Parcel in)
    {
        String [] data = new String[6];

        in.readStringArray(data);
        this.InterfaceSource = data[0];
        this.InterfaceCode = data[1];
        this.InterfaceTitle = data[2];
        this.AdminSource = data[3];
        this.AdminCode = data[4];
        this.AdminTitle = data[5];
    }

    public Medication(String interfaceSource , String interfaceCode , String interfaceTitle , String adminSource , String adminCode , String adminTitle){
        this.InterfaceSource = interfaceSource;
        this.InterfaceCode = interfaceCode;
        this.InterfaceTitle = interfaceTitle;
        this.AdminSource = adminSource;
        this.AdminCode = adminCode;
        this.AdminTitle = adminTitle;
    }

    public static final Parcelable.Creator<Medication> CREATOR
            = new Parcelable.Creator<Medication>() {
        public Medication createFromParcel(Parcel in) {
            return new Medication(in);
        }

        public Medication[] newArray(int size) {
            return new Medication[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]
                {
                        this.InterfaceSource,
                        this.InterfaceCode,
                        this.InterfaceTitle,
                        this.AdminSource,
                        this.AdminCode,
                        this.AdminTitle
                });
    }
}
