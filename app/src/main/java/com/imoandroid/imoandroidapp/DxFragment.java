package com.imoandroid.imoandroidapp;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mayoorrai on 3/31/15.
 */

public  class DxFragment extends Fragment {

    public final String TAG = DxFragment.class.getSimpleName();

    TextView patientName;
    TextView patientAge;
    TextView patientId;
    TextView patientAddress;
    ListView terms;
    TextView type;
    View border;
    View header;
    Button add;
    Button remove;
    Button send;
    Button sort;
    Button edit;
    Button save;
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

        add = (Button) v.findViewById(R.id.btnAdd);
        remove = (Button) v.findViewById(R.id.btnRemove);
        send = (Button) v.findViewById(R.id.btnBilling);
        sort = (Button) v.findViewById(R.id.btnSort);
        edit = (Button) v.findViewById(R.id.btnEdit);
        save = (Button) v.findViewById(R.id.btnSave);

        type = (TextView)v.findViewById(R.id.fragment_type);
        type.setText("PROBLEMS");


        patientName = (TextView)v.findViewById(R.id.fragment_patientName);
        patientAge = (TextView)v.findViewById(R.id.fragment_patientAge);
        patientId = (TextView)v.findViewById(R.id.fragment_patientID);
        patientAddress = (TextView)v.findViewById(R.id.fragment_patientLocation);
        terms = (ListView)v.findViewById(R.id.lvTerm);
        border = v.findViewById(R.id.listBorder);
        header = v.findViewById(R.id.termHeader);
        ArrayList<Term> aTerms = p == null ? new ArrayList<Term>() :p.getProblems();
        adapter = new DisplayPTAdapter(getActivity().getApplicationContext(),R.layout.activity_term_display,
                new ArrayList<Term>());

        terms.setAdapter(adapter);


        updateFields();

        add.setOnClickListener(new View.OnClickListener() {
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
            border.setVisibility(View.GONE);
            header.setVisibility(View.GONE);
            add.setEnabled(false);
            remove.setEnabled(false);
            send.setEnabled(false);
            sort.setEnabled(false);
            edit.setEnabled(false);
            save.setEnabled(false);
            return;
        }
        add.setEnabled(true);
        String s = p.getDemo().createFullNameGenerator();
        patientName.setText(s.length()==0 ? "Patient Name":s);
        patientAge.setText("Age: "+String.valueOf(p.getDemo().getAge()));
        patientId.setText("ID: "+String.valueOf(p.getDemo().getId()));
        patientAddress.setText("Address: "+p.getDemo().getAddress().getAddress1());
        adapter.UpdateTerms(p.getProblems());
        border.setVisibility(p.getProblems().size()>0? View.VISIBLE: View.GONE);
        header.setVisibility(p.getProblems().size()>0? View.VISIBLE: View.GONE);
        Log.v(TAG , "%%%%%%%%%" + p.getProblems().size());
        if(p.getProblems().size() > 0) {
            Log.v(TAG, "^^^^^^^^^^^^" + p.getProblems().get(0).InterfaceTitle);
        }
    }
}
