package com.imoandroid.imoandroidapp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by samarthchopra on 4/14/15.
 */
public class Demo {
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("Gender")
    private String gender;
    @JsonProperty("Age")
    private int age;
    @JsonProperty("Language")
    private String language;
    @JsonProperty("Picture")
    private String picture;
    @JsonProperty("Insurance")
    private Insurance insurance;


    public Demo(){

    }

    public Demo(String lastName, String firstName, String gender, int age, String language, String picture, Insurance insurance) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.age = age;
        this.language = language;
        this.picture = picture;
        this.insurance = insurance;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }
}
