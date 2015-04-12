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

        ArrayList<Patient> allPatients = new ArrayList<Patient>();

        try {
//            URL patientsURL = new URL("http://66.252.70.193/patients?apiKey=QhIno484vsazggxvZgf2EGZjkYunH24f7MNz5JXmI83bDMTOgmwVw6eqss7I18U7");
//            HttpURLConnection connection = (HttpURLConnection) patientsURL.openConnection();
//            connection.connect();
//
//            responseCode = connection.getResponseCode();
//            // if responseCode == 200, download data
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//
//                InputStream inputStream = connection.getInputStream();
//
//                String responseData = slurp(inputStream, inputBuffer);

                //System.out.println(responseData);

                jsonPatients = new JSONObject(responseData);
                jsonPatientArray = jsonPatients.getJSONArray("Patients");

                for (int i = 0; i < jsonPatientArray.length(); i++) {

                    Patient p = new Patient();
                    JSONObject patient = jsonPatientArray.getJSONObject(i);
                    JSONObject getPatient = patient.getJSONObject("Patient");
                    JSONObject demographics = getPatient.getJSONObject("Demographics");
                    p.problems = setUpProblems(getPatient);
                    p.medications = setUpMedications(getPatient);
                    p.procedures = setUpProcedures(getPatient);
                    p.demo = setUpDemographics(demographics);
                    allPatients.add(p);
                }

           // }


        }
            catch(Exception e){

                e.printStackTrace();

            }

    return allPatients;

    }

    public ArrayList<Medication> setUpMedications(JSONObject getPatient){

        ArrayList<Medication> allMedications = new ArrayList<Medication>();

        try{
            JSONArray medications = getPatient.getJSONArray("Medication");

            for(int i = 0 ; i < medications.length() ; i++){
                JSONObject medication = medications.getJSONObject(i);

                String interfaceSource = medication.getString("InterfaceSource");
                String interfaceCode = medication.getString("InterfaceCode");
                String interfaceTitle = medication.getString("InterfaceTitle");
                String adminSource = medication.getString("AdminSource");
                String adminCode = medication.getString("AdminCode");
                String adminTitle = medication.getString("AdminTitle");
                Medication newMedication = new Medication(interfaceSource , interfaceCode , interfaceTitle , adminSource , adminCode , adminTitle);
                allMedications.add(newMedication);
            }

        }
        catch(Exception e){

            return allMedications;

        }

        return allMedications;
    }

    public ArrayList<Procedure> setUpProcedures(JSONObject getPatient){

        ArrayList<Procedure> allProcedures = new ArrayList<Procedure>();

        try{

            JSONArray procedures = getPatient.getJSONArray("Procedure");

            for(int i = 0 ; i < procedures.length() ; i++){
                JSONObject procedure = procedures.getJSONObject(i);

                String interfaceSource = procedure.getString("InterfaceSource");
                String interfaceCode = procedure.getString("InterfaceCode");
                String interfaceTitle = procedure.getString("InterfaceTitle");
                String adminSource = procedure.getString("AdminSource");
                String adminCode = procedure.getString("AdminCode");
                String adminTitle = procedure.getString("AdminTitle");
                Procedure newProcedure = new Procedure(interfaceSource , interfaceCode , interfaceTitle , adminSource , adminCode , adminTitle);
                allProcedures.add(newProcedure);
            }

        }
        catch(Exception e){

            return allProcedures;

        }

        return allProcedures;
    }

    public ArrayList<Problem> setUpProblems(JSONObject getPatient){


        ArrayList<Problem> allProblems = new ArrayList<Problem>();

        try{
            JSONArray problems = getPatient.getJSONArray("Problem");
            for(int i = 0 ; i < problems.length() ; i++){
                JSONObject problem = problems.getJSONObject(i);

                String interfaceSource = problem.getString("InterfaceSource");
                String interfaceCode = problem.getString("InterfaceCode");
                String interfaceTitle = problem.getString("InterfaceTitle");
                String adminSource = problem.getString("AdminSource");
                String adminCode = problem.getString("AdminCode");
                String adminTitle = problem.getString("AdminTitle");
                Problem newProblem = new Problem(interfaceSource , interfaceCode , interfaceTitle , adminSource , adminCode , adminTitle);
                allProblems.add(newProblem);
            }

        }
        catch(Exception e){

            return allProblems;

        }

        return allProblems;
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
            return null;
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
                //finish STATE
                newAddress.setZip(Integer.parseInt(address.getString("zip")));
                newAddress.setHomePhone(Long.parseLong(address.getString("home")));
                newAddress.setMobilePhone(Long.parseLong(address.getString("mobile")));
                newAddress.setOfficePhone(Long.parseLong(address.getString("office")));


        } catch(Exception e){
            return null;
        }

        return newAddress;
    }

    public Demographics setUpDemographics(JSONObject demographics){

        Demographics d = new Demographics();
        try {
            String lastName = demographics.getString("LastName");
            String firstName = demographics.getString("FirstName");
            String age = demographics.getString("Age");
            String language = demographics.getString("Language");
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
            d.setLanguage(language);
            d.setAge(Integer.parseInt(age));
            d.setFirstName(firstName);
            d.setLastName(lastName);

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
