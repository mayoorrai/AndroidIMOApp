package com.imoandroid.imoandroidapp;

import java.util.ArrayList;

/**
 * Created by mayoorrai on 2/8/15.
 */
public class Constants {
    public static final String TAG_FIRST_NAME = "first_name";
    public static final String TAG_LAST_NAME = "last_name";
    public static final String TAG_DOB = "dob";
    public static final String TAG_GENDER = "gender";
    public static final String TAG_LANGUAGE = "language";
    public static final String TAG_PICTURE = "picture";
    public static final String TAG_INSURANCE_CODE = "code";
    public static final String TAG_INSURANCE_NAME = "name";
    public static final String TAG_ADDRESS = "address";
    public static final String TAG_ADDRESS1 = "address1";
    public static final String TAG_ADDRESS2 = "address2";
    public static final String TAG_CITY = "city";
    public static final String TAG_STATE = "state";
    public static final String TAG_ZIP = "zip";
    public static final String TAG_MOBILE = "mobile";
    public static final String TAG_HOME = "home";
    public static final String TAG_OFFICE = "office";
    public static final String TAG_NOTES = "notes";
    public static final String API_SERVER = "http://imoweb.azurewebsites.net/";
    public static final String API_KEY = "FSdgvuujDNpD1YPVjN95XcSFXBdsVwf66qeijgZDdwkji6GiyqYoKw15JRPywYV5";
    public static final String URL = API_SERVER + "apiKey=" + API_KEY;

    public static int RESULT_OK = 0;
    public static int RESULT_CANCEL = 1;

    public static Patient CurrentPat;
    public static final int DX_TAB = 0;
    public static final int RX_TAB = 1;
    public static final int HX_TAB = 2;
    public static final int TX_TAB = 3;
}
