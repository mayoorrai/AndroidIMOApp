package com.imoandroid.imoandroidapp;

import java.util.Date;

/**
 * Created by namrataprabhu on 4/11/15.
 */
public class Demographics {

    String firstName; //patient's first name
    String lastName; //the patient's last name
    int age; //the patient's age

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
    Gender _gender;
    String language;
    Date DOB;
    int id;

    String picture;

    PatientAddress address;
    String notes;

    Insurance insurance;

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

    public Gender get_gender() {
        return _gender;
    }

    public void set_gender(Gender _gender) {
        this._gender = _gender;
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

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }


}
