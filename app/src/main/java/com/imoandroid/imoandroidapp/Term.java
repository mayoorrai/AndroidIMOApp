package com.imoandroid.imoandroidapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vishal on 4/12/2015.
 */
public class Term implements Parcelable {

    public String InterfaceSource;
    public String InterfaceCode;
    public String InterfaceTitle;
    public String AdminSource;
    public String AdminCode;
    public String AdminTitle;

    public Term(){

    }

    public Term(Parcel in)
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

    public Term(String interfaceSource , String interfaceCode , String interfaceTitle , String adminSource , String adminCode , String adminTitle){
        this.InterfaceSource = interfaceSource;
        this.InterfaceCode = interfaceCode;
        this.InterfaceTitle = interfaceTitle;
        this.AdminSource = adminSource;
        this.AdminCode = adminCode;
        this.AdminTitle = adminTitle;
    }

    public static final Parcelable.Creator<Term> CREATOR
            = new Parcelable.Creator<Term>() {
        public Term createFromParcel(Parcel in) {
            return new Term(in);
        }

        public Term[] newArray(int size) {
            return new Term[size];
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

    public String toJSONString() throws JSONException {
        ObjectMapper mapper = new ObjectMapper();
        String back = null;
        try{
            back = mapper.writeValueAsString(this);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return back;

//        JSONObject initObj = new JSONObject(back);
//
//        Log.v("--**", initObj.toString());
//
//        JSONArray arr = new JSONArray();
//        arr.put(initObj);
//        Log.v("--***", arr.toString());
//
//
//        JSONObject obj = new JSONObject();
//
//        obj.put("results", arr);
//
//        Log.v("--***--", obj.toString());


//        return obj.toString();
    }
}
