package com.imoandroid.imoandroidapp;

import android.nfc.Tag;
import android.util.Log;

import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.GET.GETSpecificPatient;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.GET.GetPatients;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by namrataprabhu on 4/11/15.
 */
public class PatientParser {

    public final String TAG = PatientParser.class.getSimpleName();

    public ArrayList<Patient> parsePatients(String responseData){

        int responseCode = -1;
        char[] inputBuffer = new char[256];
        JSONArray jsonPatientArray = null;
        JSONObject jsonPatients = null;
        JSONObject jsonPatient = null;

        ArrayList<Patient> allPatients = new ArrayList<Patient>();

        try {

                jsonPatients = new JSONObject(responseData);
                jsonPatientArray = jsonPatients.getJSONArray("patients");
                for (int i = 0; i < jsonPatientArray.length(); i++) {

                    Patient p = new Patient();
                    JSONObject getPatient = jsonPatientArray.getJSONObject(i);
                    String lastName = getPatient.getString("last_name");
                    String firstName = getPatient.getString("first_name");
                    String id = getPatient.getString("id");


                    HttpResponse<JsonNode> patientData = new GETSpecificPatient().execute(firstName , lastName , id).get();
                    String patientInfo = patientData.getBody().toString();

                    jsonPatient = new JSONObject(patientInfo);
                    JSONObject patientDetails = jsonPatient.getJSONObject("Patient");

                    p.setDemo(setUpDemographics(patientDetails));
                    p.getDemo().setId(Integer.parseInt(id));
                    p.setProblems(setUpProblems(patientDetails));
                    p.setMedications(setUpMedications(patientDetails));
                    p.setProcedures(setUpProcedures(patientDetails));

                    allPatients.add(p);
                }


        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }




    return allPatients;

    }

    public ArrayList<Term> setUpTerms(JSONObject getPatient, TermType type){

        ArrayList<Term> allTerms = new ArrayList<Term>();

        try{
            JSONArray terms = getPatient.getJSONArray(type.toString());

            for(int i = 0 ; i < terms.length() ; i++){
                JSONObject term = terms.getJSONObject(i);

                String interfaceSource = term.getString("InterfaceSource");
                String interfaceCode = term.getString("InterfaceCode");
                String interfaceTitle = term.getString("InterfaceTitle");
                String adminSource = term.getString("AdminSource");
                String adminCode = term.getString("AdminCode");
                String adminTitle = term.getString("AdminTitle");
                Term newTerm = new Term(interfaceSource , interfaceCode , interfaceTitle , adminSource , adminCode , adminTitle);
                allTerms.add(newTerm);
            }

        }
        catch(Exception e){
            return allTerms;
        }

        return allTerms;
    }

    public ArrayList<Term> setUpMedications(JSONObject getPatient){

        return setUpTerms(getPatient,TermType.Medication);
    }

    public ArrayList<Term> setUpProcedures(JSONObject getPatient){

        return setUpTerms(getPatient,TermType.Procedure);
    }

    public ArrayList<Term> setUpProblems(JSONObject getPatient){
        return setUpTerms(getPatient,TermType.Problem);
    }

    public Insurance setUpInsurance(JSONObject demographics){

        Insurance insurance1 = new Insurance();

        try {

            JSONObject insurance = demographics.getJSONObject("Insurance");

            if (insurance != null) {

                String contractorCode = insurance.getString("ContractorCode");
                String contractorName = insurance.getString("ContractorName");
                insurance1.setContractorCode(Integer.parseInt(contractorCode));
                insurance1.setContractorName(contractorName);

            }
        }

            catch(Exception e){
            return new Insurance();
        }

        return insurance1;
    }

    public PatientAddress setUpAddress(JSONObject demographics){
        PatientAddress newAddress = new PatientAddress();

        try{
            JSONObject address = demographics.getJSONObject("Address");

                newAddress.setAddress1(address.getString("Address1"));
                try{

                    newAddress.setAddress2(address.getString("Address2"));
                }
                catch(Exception e){

                }

                newAddress.setCity(address.getString("City"));
                newAddress.setState(address.getString("State"));
                newAddress.setZip(Integer.parseInt(address.getString("Zip")));
                // API doesn't support phone numbers :'(
                //newAddress.setHomePhone(Long.parseLong(address.getString("home")));
                //newAddress.setMobilePhone(Long.parseLong(address.getString("mobile")));
                //newAddress.setOfficePhone(Long.parseLong(address.getString("office")));


        } catch(Exception e){
            Log.v(TAG , "^%^%^%^%");
            return new PatientAddress();
        }

        return newAddress;
    }

    public Demographics setUpDemographics(JSONObject patientDetails){

        JSONObject demographics = null;
        Demographics d = new Demographics();
        try {
            demographics = patientDetails.getJSONObject("Demographics");
            String lastName = demographics.getString("LastName");
            d.setLastName(lastName);
            String firstName = demographics.getString("FirstName");
            d.setFirstName(firstName);
            String language = demographics.getString("Language");
            d.setLanguage(language);
            Long dob = demographics.getLong("DOB");
            d.setDOB(dob);
            String gender;
            try {
                gender = demographics.getString("Gender");
            }
            catch(Exception e){
                gender = "M";
            }
            if (gender.equals("M")) {
                d.set_gender(Demographics.Gender.M);
            }
            else if (gender.equals("F")) {
                d.set_gender(Demographics.Gender.F);
            }
            else {
                d.set_gender(Demographics.Gender.Other);
            }

           /*Insurance insurance1 = setUpInsurance(demographics);
           d.setInsurance(insurance1);*/

           PatientAddress newAddress = setUpAddress(demographics);
           d.setAddress(newAddress);

        }
        catch(Exception e){

            e.printStackTrace();

        }
        return d;
    }

    public static void main(String [] args){
        PatientParser p = new PatientParser();

//        p.parsePatients("");
    }





}
