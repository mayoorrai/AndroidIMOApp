package com.imoandroid.imoandroidapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.imoandroid.imoandroidapp.APICaller.APICaller;

import java.util.ArrayList;

public class SearchDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    private int mTab;

    String[] listItems = {};


    private ListView lvResults;
    private ArrayAdapter<String> listAdapter;
    private ArrayAdapter<String> listAdapterAPIResults;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param Parameter 1.
     * @return A new instance of fragment SearchDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchDialogFragment newInstance(int param) {
        SearchDialogFragment fragment = new SearchDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mTab = getArguments().getInt(ARG_PARAM);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_dialog, container, false);
        EditText et = (EditText) v.findViewById(R.id.etSearch);
        lvResults = (ListView) v.findViewById(R.id.lvResults);

        listAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listItems);


        lvResults.setAdapter(listAdapter);
        lvResults.setOnItemClickListener(this);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        switch (mTab) {
            case 0: et.setHint("Search Dx");
                break;
            case 1: et.setHint("Search Rx");
                break;
            case 2: et.setHint("Search Hx");
                break;
            case 3: et.setHint("Search Tx");
                break;
        }
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                //make api call and get list of objects
                //update list view
                String[] APIResults = APICaller.vocabularyGET(s.toString(), 100);
                ArrayList<String> arrayList = new ArrayList<String>();
                for (int i = 0; i < APIResults.length; i++) {
                    arrayList.add(APIResults[i]);
                }
                listAdapterAPIResults = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, arrayList);

                lvResults.setAdapter(listAdapterAPIResults);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();

        final FragmentActivity fa = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(listAdapterAPIResults.getItem(position))
                .setMessage("Pick an Option");

        builder.setNeutralButton("Narrow Term Result", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(fa, "Narrow Term Result", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Term Detail", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(fa, "Term Detail", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

}
