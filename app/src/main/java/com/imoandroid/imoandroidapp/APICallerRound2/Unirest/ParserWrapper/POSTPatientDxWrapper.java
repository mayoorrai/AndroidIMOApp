package com.imoandroid.imoandroidapp.APICallerRound2.Unirest.ParserWrapper;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.POST.POSTPatient;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.POST.POSTPatientDx;
import com.imoandroid.imoandroidapp.Demographics;
import com.imoandroid.imoandroidapp.Patient;
import com.imoandroid.imoandroidapp.Term;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samarthchopra on 4/26/15.
 */
public class POSTPatientDxWrapper {
    public static void poster(Patient patient, Term term) throws JsonProcessingException, JSONException {
        JSONObject initObj = new JSONObject(term.toJSONString());






        new POSTPatientDx().execute(String.valueOf(patient.getDemo().getId()) , patient.getDemo().getFirstName(), patient.getDemo().getLastName() , initObj.toString());

    }
}
