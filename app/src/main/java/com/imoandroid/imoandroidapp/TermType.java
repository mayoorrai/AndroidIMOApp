package com.imoandroid.imoandroidapp;

/**
 * Created by Vishal on 4/12/2015.
 */
public enum TermType {
    Problem ("Problem"),
    Procedure ("Procedure"),
    Medication ("Medication"),
    Unspecified ("Unspecified");

    private final String name;

    private TermType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
        return name;
    }

}
