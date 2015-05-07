package com.imoandroid.imoandroidapp.APICallerRound2.Unirest.ParserWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.POST.POSTPatientMethod;
import com.imoandroid.imoandroidapp.Patient;
import com.imoandroid.imoandroidapp.Term;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samarthchopra on 4/26/15.
 */
public class POSTPatientRxWrapper {
    public static void poster(Patient patient, Term term) throws JsonProcessingException, JSONException {
        JSONObject initObj = new JSONObject(term.toJSONString());


        new POSTPatientMethod().execute(String.valueOf(patient.getDemo().getId()) , patient.getDemo().getFirstName(), patient.getDemo().getLastName() ,"rx", initObj.toString());

    }
}
