package com.imoandroid.imoandroidapp;

import android.graphics.Point;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mayoorrai on 3/31/15.
 */

public  class DxFragment extends Fragment {


    public final String TAG = DxFragment.class.getSimpleName();

    TextView patientName;
    TextView patientAge;
    TextView patientId;
    TextView patientAddress;
    Patient p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void refresh(Patient pat){
       p = pat;
       updateFields();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dx, container, false);
        p = Constants.CurrentPat;

        Button btnSearch = (Button) v.findViewById(R.id.btnAdd);


        patientName = (TextView)v.findViewById(R.id.fragment_patientName);
        patientAge = (TextView)v.findViewById(R.id.fragment_patientAge);
        patientId = (TextView)v.findViewById(R.id.fragment_patientID);
        patientAddress = (TextView)v.findViewById(R.id.fragment_patientLocation);

        updateFields();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                SearchDialogFragment s = SearchDialogFragment.newInstance(Constants.DX_TAB);
                s.show(ft, "Search");
            }
        });


        return v;
    }

    private void updateFields(){
        if(p == null){
            return;
        }
        String s = p.getDemo().getFullName();
        patientName.setText(s.length()==0 ? "Patient Name":s);
        patientAge.setText("Age: "+String.valueOf(p.getDemo().getAge()));
        patientId.setText("ID: "+String.valueOf(p.getDemo().getId()));
        patientAddress.setText("Address: "+p.getDemo().getAddress().getAddress1());
    }
}
