package com.imoandroid.imoandroidapp.APICallerRound2.Unirest.ParserWrapper;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.GET.GETSpecificPatient;
import com.imoandroid.imoandroidapp.Demo;
import com.imoandroid.imoandroidapp.Patient;
import com.imoandroid.imoandroidapp.Problem;
import com.imoandroid.imoandroidapp.Term;
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

    public static void getPatient(String firstName, String lastName, String id){
        try {
            ObjectMapper mapper = new ObjectMapper();
            HttpResponse<JsonNode> back = new GETSpecificPatient().execute(firstName, lastName, id).get();



            JSONObject body = back.getBody().getObject();
            JSONObject patient = body.getJSONObject("Patient");

//            Patient demo = mapper.readValue(patient.toString(), Patient.class);

//            Log.v("---", demo.toString());

//            JSONObject demographicJSON = patient.getJSONObject("Demographics");
//            JSONArray problemsJSON = patient.getJSONArray("Problem");
//            JSONArray medicationsJSON = patient.getJSONArray("Medication");
//            JSONArray allergyJSON = patient.getJSONArray("Allergy");
//            JSONArray chargeJSON = patient.getJSONArray("Charge");
//            JSONArray immunizationJSON = patient.getJSONArray("Immunization");
//            JSONArray procedureJSON = patient.getJSONArray("Allergy");
//            JSONArray familyHxJSON = patient.getJSONArray("FamilyHx");
//            JSONArray surgeryHxJSON = patient.getJSONArray("SurgeryHx");
//            JSONArray PMHxJSON = patient.getJSONArray("PMHx");

//            Demo demo = mapper.readValue(demographicJSON.toString(), Demo.class);

//            Log.v("---", demo.toString());



//            List<Term> problems = mapper.readValue(problemsJSON.toString(), new TypeReference<List<Term>>() {
//            });
//            List<Term> medications = mapper.readValue(problemsJSON.toString(), new TypeReference<List<Term>>() {
//            });
//            List<Term> allergy = mapper.readValue(problemsJSON.toString(), new TypeReference<List<Term>>() {
//            });
//            List<Term> charge = mapper.readValue(problemsJSON.toString(), new TypeReference<List<Term>>() {
//            });
//            List<Term> immunization = mapper.readValue(problemsJSON.toString(), new TypeReference<List<Term>>() {
//            });
//            List<Term> procedure = mapper.readValue(problemsJSON.toString(), new TypeReference<List<Term>>() {
//            });
//            List<Term> familyHx = mapper.readValue(problemsJSON.toString(), new TypeReference<List<Term>>() {
//            });
//            List<Term> surgeryHx = mapper.readValue(problemsJSON.toString(), new TypeReference<List<Term>>() {
//            });
//            List<Term> PMHx = mapper.readValue(problemsJSON.toString(), new TypeReference<List<Term>>() {
//            });



//            Log.v("-->", familyHx.toString());


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
