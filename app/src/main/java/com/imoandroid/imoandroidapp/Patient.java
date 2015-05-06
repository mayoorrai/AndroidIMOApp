package com.imoandroid.imoandroidapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.print.PrintAttributes;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.ParserWrapper.POSTPatientDxWrapper;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Patient implements Parcelable{
	// Sammy wrote code!
    // Basic Patient Details

    public final String TAG = Patient.class.getSimpleName();

    private Demographics demo;

    private ArrayList<Term> problems;
    private ArrayList<Term> procedures;
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

    public void AddProblem(Term p) throws JsonProcessingException, JSONException {
        problems.add(p);
        POSTPatientDxWrapper.poster(Constants.CurrentPat, p);
        Log.v(TAG , "&^&^&^&^&" + p.InterfaceTitle);
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

    public int Age()
    {
        Calendar bday = Calendar.getInstance();
        try {
            bday.setTimeInMillis(Long.parseLong(this.demo.DOB.toString()));
        }
        catch (Exception e)
        {
            return 200;
        }

        Calendar today = Calendar.getInstance();
        int _age = today.get(Calendar.YEAR) - bday.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_YEAR) <= bday.get(Calendar.DAY_OF_YEAR))
        {
            _age--;
        }
        return _age;
    }
}
