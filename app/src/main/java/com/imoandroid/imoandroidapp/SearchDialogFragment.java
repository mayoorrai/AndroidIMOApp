package com.imoandroid.imoandroidapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.SystemClock;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imoandroid.imoandroidapp.APICaller.APICaller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    private int mTab;

    Map[] listItems = {};

    long time;
    int tally;
    private int width;
    boolean frameDisplayed;
    // hacky
    private HashMap<Map,Boolean> isAdded = new HashMap<Map,Boolean>();
    private int rl;
    private long chartInterval = 0;
    private int termSelected = -1;

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
        submit.setEnabled(false);
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
                submit.setEnabled(exists);
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

                APICall(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        closeKeyboard();

        listAdapter.setSelected(position);
        // update term details
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
            closeKeyboard();
            APICall(et.getText().toString());
        }
        else if (b.getTag() instanceof Map)
        {
            closeKeyboard();
            displayFrame();
            if (chartInterval == 0) {
                chartInterval = System.currentTimeMillis();
            }
            else {
                long diff = System.currentTimeMillis() - chartInterval;
                if (diff < 150) {
                    return;
                }
                chartInterval += diff;
            }

            if (b.getText().equals("+")) {
                //add term to chart
                Log.v("as","as");

                listAdapter.setAdded((Map) v.getTag());
                chartAdapter.Add((Map) v.getTag());

                Toast.makeText(getActivity().getApplicationContext(), "Added \"" + (String) ((Map) v.getTag()).get("code")
                        + "\" to chart"
                        , Toast.LENGTH_SHORT).show();
            }
            else
            {
                listAdapter.setRemoved(chartAdapter.Delete((Map) b.getTag()));



                Toast.makeText(getActivity().getApplicationContext(), "Removed \"" + (String) ((Map)v.getTag()).get("code")
                        + "\" from chart"
                        , Toast.LENGTH_SHORT).show();
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
        ArrayList<Map> APIResults = APICaller.vocabularyGET(s, 100);

        termSelected = -1;

        listAdapter.UpdateData(APIResults);
    }

    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }
    }

    public ArrayList<Map> getArrayList(Map [] data)
    {
        ArrayList<Map> ret = new ArrayList<Map>();
        for(int i=0;i<data.length;i++)
        {
            ret.add(data[i]);
        }
        return ret;
    }

    public class DisplayTermAdapter extends ArrayAdapter<Map> {
        Context mContext;
        int layoutResourceID;
        ArrayList<Map> data = new ArrayList<Map>();
        boolean plus;

        // Adapter Constructor
        public DisplayTermAdapter(Context mContext, int layoutResourceID, ArrayList<Map> data,
                                  boolean plus) {
            super(mContext, layoutResourceID, data);

            this.mContext = mContext;
            this.layoutResourceID = layoutResourceID;
            this.data = data;
            this.plus = plus;
        }

        public void Add(Map s)
        {
            data.add(s);
            notifyDataSetChanged();
        }

        public Map Delete(Map s)
        {
            data.remove(s);
            isAdded.put(s,false);

            notifyDataSetChanged();

            return s;
        }

        public void UpdateData(ArrayList<Map> newData)
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
            }
            term = (TextView) child.findViewById(R.id.termTitle);
            addTerm = (Button) child.findViewById(R.id.addTermButton);
            Map t = data.get(position);
            child.setTag(t);
            term.setTag(t);
            term.setText((String) t.get("title"));
            addTerm.setTag(t);
            addTerm.setOnClickListener(SearchDialogFragment.this);
            if(!plus)
            {
                addTerm.setText("-");
            }
            else {
                if (!isAdded.containsKey(t)) {
                    isAdded.put(t, false);
                }
                if (isAdded.get(t)) {
                    child.setBackgroundColor(getResources().getColor(R.color.green));
                    addTerm.setEnabled(false);
                }
                else {
                    child.setBackgroundColor(getResources().getColor(R.color.white));
                    addTerm.setEnabled(true);
                }
                if (termSelected == position) {
                    child.setBackgroundColor(getResources().getColor(R.color.highlighted_text_material_light));
                }
            }

            return child;
        }

        public void setAdded(Map item) {
            isAdded.put(item, true);

            notifyDataSetChanged();
        }

        public void setRemoved(Map item) {
            if(isAdded.containsKey(item)) {
                isAdded.put(item, false);

                notifyDataSetChanged();
            }
        }

        public void setSelected(int position) {
            termSelected = position;

            notifyDataSetChanged();
        }
    }

}
