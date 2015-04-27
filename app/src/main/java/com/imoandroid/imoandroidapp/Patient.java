package com.imoandroid.imoandroidapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.print.PrintAttributes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Patient implements Parcelable{
	// Sammy wrote code!
    // Basic Patient Details

    @JsonProperty("Demographics")
    private Demographics demo;
    @JsonProperty("Problem")
    private ArrayList<Term> problems;
    @JsonProperty("Procedure")
    private ArrayList<Term> procedures;
    @JsonProperty("Medication")
    private ArrayList<Term> medications;

    public Demographics getDemo() {
        return demo;
    }

    public ArrayList<Term> getProblems() {
        return problems;
    }

    public void setProblems(ArrayList<Term> problems) {
        this.problems = problems;
    }

    public ArrayList<Term> getProcedures() {
        return procedures;
    }

    public void setProcedures(ArrayList<Term> procedures) {
        this.procedures = procedures;
    }

    public ArrayList<Term> getMedications() {
        return medications;
    }

    public void setMedications(ArrayList<Term> medications) {
        this.medications = medications;
    }

    public Patient()
    {
        demo = new Demographics();

        problems = new ArrayList<Term>();
        procedures = new ArrayList<Term>();
        medications = new ArrayList<Term>();
    }

    public Patient(Parcel in)
    {
        demo = in.readParcelable(Demographics.class.getClassLoader());

        Parcelable [] p = in.readParcelableArray(Term.class.getClassLoader());
        Term[] t = new Term[p.length];
        System.arraycopy(p,0,t,0,p.length);
        problems = new ArrayList<Term>(Arrays.asList(t));

        p = in.readParcelableArray(Term.class.getClassLoader());
        t = new Term[p.length];
        System.arraycopy(p,0,t,0,p.length);
        procedures = new ArrayList<Term>(Arrays.asList(t));

        p = in.readParcelableArray(Term.class.getClassLoader());
        t = new Term[p.length];
        System.arraycopy(p,0,t,0,p.length);
        medications = new ArrayList<Term>(Arrays.asList(t));
    }

    public Patient(Demographics d)
    {
        demo = new Demographics(d);

        problems = new ArrayList<Term>();
        procedures = new ArrayList<Term>();
        medications = new ArrayList<Term>();
    }

    public void AddProblem(Term p)
    {
        problems.add(p);
        //notify?
    }

    public void AddProcedure(Term p)
    {
        procedures.add(p);
    }

    public void AddMedication(Term p)
    {
        medications.add(p);
    }

    public void setDemo(Demographics d)
    {
        demo = d;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.demo,flags);
        dest.writeParcelableArray(problems.toArray(new Term [problems.size()]),flags);
        dest.writeParcelableArray(procedures.toArray(new Term [procedures.size()]),flags);
        dest.writeParcelableArray(medications.toArray(new Term [medications.size()]),flags);
    }

    public static final Parcelable.Creator<Patient> CREATOR
            = new Parcelable.Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };



    public String toJSON(){
        ObjectMapper mapper = new ObjectMapper();
        String back = null;
        try
        {
            back = mapper.writeValueAsString(this);
        } catch (JsonGenerationException e)
        {
            e.printStackTrace();
        } catch (JsonMappingException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return back;
    }
}
