package com.imoandroid.imoandroidapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Created by namrataprabhu on 4/11/15.
 */
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Demographics implements Parcelable{

    String firstName; //patient's first name
    String lastName; //the patient's last name
    int age; //the patient's age
    Gender gender;
    String language;
    Date DOB;
    int id;
    String picture;
    PatientAddress address;
    String notes;
    Insurance insurance;

    public Demographics(){
        address = new PatientAddress();
    }

    public Demographics(Demographics copy)
    {
        firstName = copy.getFirstName();
        lastName = copy.getLastName();
        age = copy.getAge();
        gender = copy.getGender();
        language = copy.getLanguage();
        DOB = copy.getDOB();
        id = copy.getId();
        picture = copy.getPicture();
        address = copy.getAddress();
        notes = copy.getNotes();
        insurance = copy.getInsurance();
    }

    public Demographics(Parcel in)
    {
        String [] vals = new String[5];

        in.readStringArray(vals);
        this.firstName = vals[0];
        this.lastName = vals[1];
        this.language = vals[2];
        this.picture = vals[3];
        this.notes = vals[4];
        this.DOB = new Date(in.readLong());
        this.id = in.readInt();
        this.address = in.readParcelable(PatientAddress.class.getClassLoader());
        this.insurance = in.readParcelable(Insurance.class.getClassLoader());
    }

    public static final Parcelable.Creator<Demographics> CREATOR
            = new Parcelable.Creator<Demographics>() {
        public Demographics createFromParcel(Parcel in) {
            return new Demographics(in);
        }

        public Demographics[] newArray(int size) {
            return new Demographics[size];
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
                        this.firstName,
                        this.lastName,
                        this.language,
                        this.picture,
                        this.notes
                });
        dest.writeLong(DOB.getTime());
        dest.writeInt(id);
        dest.writeParcelable(address,flags);
        dest.writeParcelable(insurance,flags);
    }

    public enum Gender {
        M(0),
        F(1),
        Other(2);

        private int num;

        Gender(int num){
            this.num = num;
        }

        public int getNum()
        {
            return num;
        }
    }

    public Insurance getInsurance(){
        return insurance;
    }

    public void setInsurance(Insurance insurance1){
        this.insurance = insurance1;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public PatientAddress getAddress() {
        return address;
    }

    public void setAddress(PatientAddress address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void set_gender(int id)
    {
        switch(id)
        {
            case 0:
                gender = Gender.M;
                break;
            case 1:
                gender = Gender.F;
                break;
            case 2:
                gender = Gender.Other;
                break;
        }
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(long time) {
        this.DOB = new Date(time);
    }

    public String createFullNameGenerator() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
