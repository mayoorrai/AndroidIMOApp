package com.imoandroid.imoandroidapp.APICallerRound2.Unirest.ParserWrapper;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.GET.GETSpecificPatient;
import com.imoandroid.imoandroidapp.Problem;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by samarthchopra on 4/14/15.
 */
public class RetreivePatient {

    private RetreivePatient(){

    }

    public static void getProblem(String firstName, String lastName, String id){
        try {
            ObjectMapper mapper = new ObjectMapper();
            HttpResponse<JsonNode> back = new GETSpecificPatient().execute(firstName, lastName, id).get();

            JSONObject body = back.getBody().getObject();
            JSONObject patient = body.getJSONObject("Patient");
            JSONArray problemsJSON = patient.getJSONArray("Problem");
            JSONArray medicationsJSON = patient.getJSONArray("Medication");
            List<Problem> problems = mapper.readValue(problemsJSON.toString(), new TypeReference<List<Problem>>() {
            });
            List<Problem> medications = mapper.readValue(problemsJSON.toString(), new TypeReference<List<Problem>>() {
            });


            Log.v("-->", medications.toString());


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
