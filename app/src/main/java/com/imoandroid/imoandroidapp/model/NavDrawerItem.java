package com.imoandroid.imoandroidapp.model;

import com.imoandroid.imoandroidapp.Patient;

/**
 * Created by mayoorrai on 3/1/15.
 */
public class NavDrawerItem {

    private Patient patient;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private int icon;

    public NavDrawerItem() {
        // default constructor
    }

    public NavDrawerItem(Patient patient) {
        this.patient = patient;
        this.title = this.patient.getDemo().getFullName();
       // this.icon = icon;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

