package com.imoandroid.imoandroidapp;

import android.nfc.Tag;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
        JSONObject jsonNewPatient = null;

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
                    if(firstName.equals("melinda")) {
                        Log.v(TAG , "-----INMELINDA");
                        URL patientDetailsURL = new URL("http://66.252.70.193/patients?firstName=melinda&lastName=test&id=1&apiKey=FSdgvuujDNpD1YPVjN95XcSFXBdsVwf66qeijgZDdwkji6GiyqYoKw15JRPywYV5");
                        HttpURLConnection connection = (HttpURLConnection) patientDetailsURL.openConnection();
                        connection.connect();
                        responseCode = connection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            InputStream inputStream = connection.getInputStream();
                            String newResponseData = slurp(inputStream, inputBuffer);
                            jsonNewPatient = new JSONObject(newResponseData);
                            JSONObject patientDetails = jsonNewPatient.getJSONObject("Patient");
                            Log.v(TAG, "-----" + patientDetails);
                           p.setProblems(setUpProblems(patientDetails));
                            Log.v(TAG, "--------" + p.getProblems().size());
                            p.setMedications(setUpMedications(patientDetails));
                            p.setProcedures(setUpProcedures(patientDetails));
                            p.setDemo(setUpDemographics(patientDetails));
                            Log.v(TAG , "%%%%%%%" + p.getDemo().getLastName());
                            p.getDemo().setId(Integer.parseInt(id));
                            allPatients.add(p);
                        }
                    }
                    else{
                        Log.v("** FIRSTNAME:", firstName);

                        Demographics d = new Demographics();
                        d.setFirstName(firstName);
                        d.setLastName(lastName);
                        d.setId(Integer.parseInt(id));
                        p.setDemo(d);
                        allPatients.add(p);
                    }
                }

        }
            catch(Exception e){
                return allPatients;
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

                newAddress.setAddress1(address.getString("address1"));
                newAddress.setAddress2(address.getString("address2"));
                newAddress.setCity(address.getString("city"));
                newAddress.setState(address.getString("state"));
                newAddress.setZip(Integer.parseInt(address.getString("zip")));
                newAddress.setHomePhone(Long.parseLong(address.getString("home")));
                newAddress.setMobilePhone(Long.parseLong(address.getString("mobile")));
                newAddress.setOfficePhone(Long.parseLong(address.getString("office")));


        } catch(Exception e){
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
            String firstName = demographics.getString("FirstName");
           //String age = demographics.getString("Age");
         /*   String language = demographics.getString("Language");
            String gender = demographics.getString("Gender");


           Insurance insurance1 = setUpInsurance(demographics);
           d.setInsurance(insurance1);

           PatientAddress newAddress = setUpAddress(demographics);
            d.setAddress(newAddress);

            if (gender.equals("M")) {
                d.set_gender(Demographics.Gender.M);
            }
            else if (gender.equals("F")) {
                d.set_gender(Demographics.Gender.F);
            }
            else {
                d.set_gender(Demographics.Gender.Other);
            }
            d.setLanguage(language);*/
           // d.setAge(Integer.parseInt(age));
            d.setFirstName(firstName);
            d.setLastName(lastName);


        }
        catch(Exception e){

            return d;

        }
        return d;
    }

    public static void main(String [] args){
        PatientParser p = new PatientParser();

//        p.parsePatients("");
    }

    protected String slurp(InputStream is, char[] buffer) throws IOException {
        StringBuilder out = new StringBuilder();
        Reader in = null;

        try {

            in = new InputStreamReader(is, "UTF-8");
            for (;;) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0) {
                    break;
                }
                out.append(buffer, 0, rsz);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            in = null;
        }

        return out.toString();
    }


}
