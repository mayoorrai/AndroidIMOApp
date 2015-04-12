package com.imoandroid.imoandroidapp;

/**
 * Created by namrataprabhu on 4/11/15.
 */
public class Medication {

    public String InterfaceSource;
    public String InterfaceCode;
    public String InterfaceTitle;
    public String AdminSource;
    public String AdminCode;
    public String AdminTitle;

    public Medication(){

    }

    public Medication(String interfaceSource , String interfaceCode , String interfaceTitle , String adminSource , String adminCode , String adminTitle){
        this.InterfaceSource = interfaceSource;
        this.InterfaceCode = interfaceCode;
        this.InterfaceTitle = interfaceTitle;
        this.AdminSource = adminSource;
        this.AdminCode = adminCode;
        this.AdminTitle = adminTitle;
    }
}
