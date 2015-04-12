package com.imoandroid.imoandroidapp;

/**
 * Created by namrataprabhu on 4/11/15.
 */
public class Procedure {

    public String InterfaceSource;
    public String InterfaceCode;
    public String InterfaceTitle;
    public String AdminSource;
    public String AdminCode;
    public String AdminTitle;

    public Procedure(){

    }

    public Procedure(String interfaceSource , String interfaceCode , String interfaceTitle , String adminSource , String adminCode , String adminTitle){
        this.InterfaceSource = interfaceSource;
        this.InterfaceCode = interfaceCode;
        this.InterfaceTitle = interfaceTitle;
        this.AdminSource = adminSource;
        this.AdminCode = adminCode;
        this.AdminTitle = adminTitle;
    }
}
