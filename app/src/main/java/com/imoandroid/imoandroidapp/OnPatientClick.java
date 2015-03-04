package com.imoandroid.imoandroidapp;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

/**
 * Created by mayoorrai on 2/8/15.
 */
public class OnPatientClick implements AdapterView.OnItemClickListener{

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Context context = view.getContext();
        TextView patientDetails = (TextView) view.findViewById(R.id.title);
        Patient pat = (Patient) patientDetails.getTag();
        ((PatientListActivity) context).patientClickHandler(pat);
    }
}
