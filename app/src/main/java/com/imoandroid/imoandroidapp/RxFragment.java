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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mayoorrai on 3/31/15.
 */

public  class RxFragment extends Fragment {


    public final String TAG = RxFragment.class.getSimpleName();

    TextView patientName;
    TextView patientAge;
    TextView patientId;
    TextView patientAddress;
    ListView terms;
    View border;
    DisplayPTAdapter adapter;
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
        terms = (ListView)v.findViewById(R.id.lvTerm);
        border = v.findViewById(R.id.listBorder);
        ArrayList<Term> aTerms = p == null ? new ArrayList<Term>() :p.getMedications();
        adapter = new DisplayPTAdapter(getActivity().getApplicationContext(),R.layout.activity_term_display,
                new ArrayList<Term>());

        terms.setAdapter(adapter);


        updateFields();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                SearchDialogFragment s = SearchDialogFragment.newInstance(Constants.RX_TAB);
                s.show(ft, "Search");
            }
        });


        return v;
    }

    private void updateFields(){
        if(p == null){
            border.setVisibility(View.GONE);
            return;
        }
        String s = p.getDemo().getFullName();
        patientName.setText(s.length()==0 ? "Patient Name":s);
        patientAge.setText("Age: "+String.valueOf(p.getDemo().getAge()));
        patientId.setText("ID: "+String.valueOf(p.getDemo().getId()));
        patientAddress.setText("Address: "+p.getDemo().getAddress().getAddress1());
        adapter.UpdateTerms(p.getMedications());
        border.setVisibility(p.getMedications().size() > 0 ? View.VISIBLE : View.GONE);
        Log.v(TAG, "%%%%%%%%%" + p.getMedications().size());
    }
}

