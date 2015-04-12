package com.imoandroid.imoandroidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mayoorrai on 2/8/15.
 */
public class DisplayPatientAdapter extends ArrayAdapter<Patient> {
    Context mContext;
    int layoutResourceID;
    ArrayList<Patient> data = new ArrayList<Patient>();

    // Adapter Constructor
    public DisplayPatientAdapter(Context mContext, int layoutResourceID, ArrayList<Patient> data) {
        super(mContext, layoutResourceID, data);

        this.mContext = mContext;
        this.layoutResourceID = layoutResourceID;
        this.data = data;
    }


    @Override
    public View getView(int position, View child, ViewGroup parent) {
        TextView pat;
        if (child == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = inflater.inflate(layoutResourceID, parent, false);
            pat = (TextView) child.findViewById(R.id.title);
            child.setTag(pat);
        }
        else {
            pat = (TextView) child.getTag();
        }
        Patient p = data.get(position);
        pat.setTag(p);
        pat.setText(p.demo.getFirstName() + " " + p.demo.getLastName());

        return child;
    }
}
