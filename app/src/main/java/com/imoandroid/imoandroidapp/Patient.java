package com.imoandroid.imoandroidapp;

import java.util.Date;

public class Patient {

    // Basic Patient Details
    String firstName;
    String lastName;
    int age;
    public enum Gender {
        Male(0),
        Female(1),
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

    // Primary Address
    PatientAddress address;

    // Notes
    String notes;

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
}
