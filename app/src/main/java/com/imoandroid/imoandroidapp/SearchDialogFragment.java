package com.imoandroid.imoandroidapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v4.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.imoandroid.imoandroidapp.APICaller.APICaller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    private int mTab;

    String[] listItems = {};

    long time;
    int tally;
    private int width;
    boolean frameDisplayed;

    private ListView lvResults;
    private ListView lvChart;
    private DisplayTermAdapter listAdapter;
    private DisplayTermAdapter chartAdapter;
    private ArrayAdapter<String> listAdapterAPIResults;

    private Button clear;
    private Button submit;
    private EditText et;
    private View searchView;
    private View frameView;

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

        time = 0;
        tally=0;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_dialog, container, false);
        searchView = (View) v.findViewById(R.id.searchLayout);
        frameView = (View) v.findViewById(R.id.frame);
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        width = size.x;
        ViewGroup.LayoutParams params = searchView.getLayoutParams();
        params.width = size.x/2;
        searchView.setLayoutParams(params);
        frameView.setVisibility(View.GONE);
        frameDisplayed = false;
        et = (EditText) v.findViewById(R.id.etSearch);
        clear = (Button) v.findViewById(R.id.clearSearch);
        clear.setVisibility(View.GONE);
        submit = (Button) v.findViewById(R.id.bDoSearch);
        submit.setClickable(false);
        lvResults = (ListView) v.findViewById(R.id.lvResults);
        lvChart = (ListView) v.findViewById(R.id.lvChart);

        listAdapter = new DisplayTermAdapter(getActivity().getApplicationContext(),R.layout.activity_term_holder,
                getArrayList(listItems),true);

        chartAdapter = new DisplayTermAdapter(getActivity().getApplicationContext(),R.layout.activity_term_holder,
                getArrayList(listItems),false);

        lvChart.setAdapter(chartAdapter);
        lvResults.setAdapter(listAdapter);
        lvResults.setOnItemClickListener(this);

        clear.setOnClickListener(this);
        submit.setOnClickListener(this);

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
                boolean exists = s.length() > 0;
                clear.setVisibility(exists? View.VISIBLE:View.GONE);
                submit.setClickable(exists);
                if (s.length() < 2) {
                    return;
                }

                if (time == 0) {
                    time = System.currentTimeMillis();
                    return;
                }
                else{
                    long diff = System.currentTimeMillis() - time;
                    time += diff;
                    if (diff < 1500)
                    {
                        tally=0;
                        return;
                    }
                    else if (++tally < 2)
                    {
                        return;
                    }
                }

                tally=0;
                //make api call and get list of objects
                //update list view
                APICall(s.toString());
                /**
                String[] APIResults = APICaller.vocabularyGET(s.toString(), 100);
                ArrayList<String> arrayList = new ArrayList<String>();
                for (int i = 0; i < APIResults.length; i++) {
                    arrayList.add(APIResults[i]);
                }
                listAdapterAPIResults = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, arrayList);

                lvResults.setAdapter(listAdapterAPIResults);**/
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

    @Override
    public void onClick(View v)
    {
        Button b = (Button) v;
        if (v == clear)
        {
            et.setText("");
        }
        else if (v==submit)
        {
            APICall(et.getText().toString());
        }
        else if (b.getTag() instanceof String)
        {
            displayFrame();

            if (b.getText().equals("+")) {
                //add term to chart
                Log.v("as","as");

                chartAdapter.Add((String) v.getTag());

                Toast.makeText(getActivity().getApplicationContext(), "Added \"" + (String) v.getTag()
                        + "\" to chart"
                        , Toast.LENGTH_LONG).show();
            }
            else
            {
                chartAdapter.Delete((String) b.getTag());

                Toast.makeText(getActivity().getApplicationContext(), "Removed \"" + (String) v.getTag()
                        + "\" from chart"
                        , Toast.LENGTH_LONG).show();
            }
        }
    }

    private void displayFrame() {
        if (frameDisplayed)
        {
            return;
        }

        frameDisplayed = true;

        ViewGroup.LayoutParams params = searchView.getLayoutParams();
        params.width = width/3;
        searchView.setLayoutParams(params);

        params = frameView.getLayoutParams();
        params.width = width*2/3;
        frameView.setLayoutParams(params);
        frameView.setVisibility(View.VISIBLE);
    }

    //TODO: Add check for type of search (proc vs diagnosis vs prescription)
    private void APICall(String s)
    {
        String[] APIResults = APICaller.vocabularyGET(s, 100);
        //ArrayList<String> arrayList = new ArrayList<String>();
        //for (int i = 0; i < APIResults.length; i++) {
        //    arrayList.add(APIResults[i]);
        //}
        //listAdapterAPIResults = new DisplayTermAdapter(
               // getActivity().getApplicationContext(),
                //R.layout.activity_term_holder,
                //arrayList,true);
        listAdapter.UpdateData(getArrayList(APIResults));

        //lvResults.setAdapter(listAdapterAPIResults);
    }

    public ArrayList<String> getArrayList(String [] data)
    {
        ArrayList<String> ret = new ArrayList<String>();
        for(int i=0;i<data.length;i++)
        {
            ret.add(data[i]);
        }
        return ret;
    }

    public class DisplayTermAdapter extends ArrayAdapter<String> {
        Context mContext;
        int layoutResourceID;
        ArrayList<String> data = new ArrayList<String>();
        boolean plus;

        // Adapter Constructor
        public DisplayTermAdapter(Context mContext, int layoutResourceID, ArrayList<String> data,
                                  boolean plus) {
            super(mContext, layoutResourceID, data);

            this.mContext = mContext;
            this.layoutResourceID = layoutResourceID;
            this.data = data;
            this.plus = plus;
        }

        public void Add(String s)
        {
            data.add(s);
            notifyDataSetChanged();
        }

        public void Delete(String s)
        {
            data.remove(s);

            notifyDataSetChanged();
        }

        public void UpdateData(ArrayList<String> newData)
        {
            data.clear();
            for (int i=0;i<newData.size();i++)
            {
                data.add(newData.get(i));
            }
            notifyDataSetChanged();
        }


        @Override
        public View getView(int position, View child, ViewGroup parent) {
            TextView term;
            Button addTerm;
            if (child == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                child = inflater.inflate(layoutResourceID, parent, false);
                term = (TextView) child.findViewById(R.id.termTitle);
                child.setTag(term);
            }
            else {
                term = (TextView) child.getTag();
            }
            addTerm = (Button) child.findViewById(R.id.addTermButton);
            String t = data.get(position);
            term.setTag(t);
            term.setText(t);
            addTerm.setTag(t);
            addTerm.setOnClickListener(SearchDialogFragment.this);
            if(!plus)
            {
                addTerm.setText("-");
            }
            return child;
        }
    }

}
