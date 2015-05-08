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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imoandroid.imoandroidapp.APICaller.APICaller;

import com.imoandroid.imoandroidapp.APICallerRound2.GETTx;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.GET.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SearchDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    public final String TAG = Patient.class.getSimpleName();

    private int mTab;

    private ArrayList<Map> items;

    Map[] listItems = {};

    long time;
    int tally;
    private int width;
    boolean frameDisplayed;

    private String source = null;
    // hacky
    private HashMap<Map,Boolean> isAdded = new HashMap<Map,Boolean>();
    private int rl;
    private long chartInterval = 0;
    private int termSelected = -1;
    private View before = null;

    private ListView lvResults;
    private ListView lvChart;
    private View chartFrag;
    private View detailsFrag;
    private DisplayTermAdapter listAdapter;
    private DisplayTermAdapter chartAdapter;
    private ArrayAdapter<String> listAdapterAPIResults;

    private Button clear;
    private Button submit;
    private Button addDetail;
    private Button viewChart;
    private ImageButton closeChart;
    private EditText et;
    private View searchView;
    private View frameView;
    private TextView IMOTerm;
    private TextView ConTerm;
    private TextView ICD9;
    private TextView ICD10;



    private Button bSaveChart;

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
        IMOTerm = (TextView)v.findViewById(R.id.tvIMOTerm);
        ConTerm = (TextView)v.findViewById(R.id.tvConsumerTerm);
        ICD9 = (TextView)v.findViewById(R.id.tvICD9);
        ICD10 = (TextView)v.findViewById(R.id.tvICD10);
        addDetail = (Button)v.findViewById(R.id.bAddDetails);
        addDetail.setOnClickListener(this);
        viewChart = (Button)v.findViewById(R.id.bViewChart);
        viewChart.setOnClickListener(this);
        closeChart = (ImageButton)v.findViewById(R.id.bCloseChart);
        closeChart.setOnClickListener(this);
        chartFrag = v.findViewById(R.id.fragChart);
        detailsFrag = v.findViewById(R.id.fragTermDetails);
        before = detailsFrag;
        bSaveChart = (Button) v.findViewById(R.id.bSaveChart);

        bSaveChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for(int i = 0 ; i < chartAdapter.data.size(); i++){

                    Map item = (Map) chartAdapter.data.get(i);
                   Log.v(TAG , "#$#$#$#@@@@" + item.toString());
                    Term term = new Term();
                    term.InterfaceTitle = item.get("title").toString();
                    term.InterfaceCode = item.get("code").toString();

                    term.InterfaceSource = source;
                    term.AdminSource = item.get("kndg_source").toString();
                    try {
                        term.AdminTitle = item.get("kndg_title").toString();
                    }catch(Exception e){
                        term.AdminTitle = item.get("title").toString();
                    }
                    term.AdminCode = item.get("kndg_code").toString();


                    switch(source){
                        case "ProblemIT Professional" :
                            try {
                                Constants.CurrentPat.AddProblem(term);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "MedicationIT Core":
                            try {
                                Constants.CurrentPat.AddMedication(term);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            break;
                        case "ProcedureIT Orderable":
                            try {
                                Constants.CurrentPat.AddProcedure(term);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            break;
                        default:
                            break;
                    }





                    Log.v(TAG , "#$#%%^%$&$" + Constants.CurrentPat.getDemo().createFullNameGenerator());

                }
            }
        });


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

              /*  try {
                    APICall(s.toString());
                }

                catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }*/

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    private void setDetails(Map toSet)
    {
        IMOTerm.setText((String)toSet.get("title"));
        ConTerm.setText((String)toSet.get("title"));
        ICD9.setText(toSet.get("kndg_code")+"- "+toSet.get("kndg_title"));
        ICD10.setText(toSet.get("ICD10CM_CODE")+"- "+toSet.get("ICD10CM_TITLE"));
        addDetail.setTag(toSet);
        addDetail.setEnabled(!isAdded.get(toSet));
    }

    private void clearDetails()
    {
        IMOTerm.setText("");
        ConTerm.setText("");
        ICD9.setText("");
        ICD10.setText("");
        addDetail.setTag(null);
        addDetail.setEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        closeKeyboard();

        listAdapter.setSelected(position);

        setDetails(listAdapter.Get(position));
        detailsFrag.bringToFront();
        displayFrame();
    }

    @Override
    public void onClick(View v)
    {
        if (v instanceof ImageButton)
        {
            if(before == null)
            {
                frameDisplayed=false;
                frameView.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = searchView.getLayoutParams();
                params.width = width/3;
                searchView.setLayoutParams(params);
            }
            before.bringToFront();

            return;
        }

        Button b = (Button) v;
        if (v == clear)
        {
            et.setText("");
        }
        else if (v==submit)
        {
            closeKeyboard();
            try{
            Log.v(TAG , "!!!!!!!" + et.getText().toString());
            APICall(et.getText().toString());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else if(v==addDetail)
        {
            listAdapter.setAdded((Map) v.getTag());
            chartAdapter.Add((Map) v.getTag());

            Toast.makeText(getActivity().getApplicationContext(), "Added \"" + (String) ((Map) v.getTag()).get("code")
                    + "\" to chart"
                    , Toast.LENGTH_SHORT).show();

            addDetail.setEnabled(false);
        }
        else if(v==viewChart)
        {
            chartFrag.bringToFront();
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

            if (!b.getText().equals("-")) {
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
        params.width = width*2/3+5;
        frameView.setLayoutParams(params);
        frameView.setVisibility(View.VISIBLE);
    }

    private void APICall(String s) throws ExecutionException, InterruptedException {
        ArrayList<Map> APIResults;

        if(!Constants.isNetworkAvailable(getActivity().getSystemService(Context.CONNECTIVITY_SERVICE))){
            Toast.makeText(getActivity().getApplicationContext() , "NO NETWORK >:" , Toast.LENGTH_LONG);
            SystemClock.sleep(2000);
            getActivity().finish();
            System.exit(0);
        }

        HttpResponse<JsonNode> allTerms = null;

        try {

            switch (mTab) {
                case 0:
                    allTerms = new GETDx().execute(s).get();
                    break;
                case 1:
                    allTerms = new GETRx().execute(s).get();
                    break;
                case 2: //idk about Hx
                    allTerms = new GETDx().execute(s).get();
                    break;
                case 3:
                    allTerms = new GETTx().execute(s).get();
                    break;
                default:
                    allTerms = new GETDx().execute(s).get();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = allTerms.getBody().getObject();


        try {

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = mapper.readValue(allTerms.getRawBody(), Map.class);
            Map data = (Map) jsonMap.get("data");

            switch(data.get("source").toString()){
                case "ProblemIT" :
                    source = "ProblemIT Professional";
                    break;
                case "MedicationIT":
                    source = "MedicationIT Core";
                    break;
                case "ProcedureIT":
                    source = "ProcedureIT Orderable";
                    break;
                default:
                    source = null;
                    break;
            }

            items = (ArrayList<Map>) data.get("items");

        }
        catch(JsonMappingException e){

        }catch(JsonParseException e){

        }catch(IOException e){

        }

        APIResults = items;

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

        public Map Get(int position)
        {
            return data.get(position);
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
            if(addDetail.getTag().equals(s))
            {
                addDetail.setEnabled(true);
            }

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
            boolean setWhite = position % 2 != 0;
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
                child.setBackgroundColor(setWhite ? getResources().getColor(R.color.white) :
                        getResources().getColor(R.color.light_blue));
            }
            else {
                Object narrow = t.get("POST_COORD_LEX_FLAG");
                if(narrow != null && narrow.equals("3"))
                {
                    addTerm.setText("v");
                }

                if (!isAdded.containsKey(t)) {
                    isAdded.put(t, false);
                }
                if (isAdded.get(t)) {
                    child.setBackgroundColor(getResources().getColor(R.color.light_green));
                    addTerm.setEnabled(false);
                }
                else {
                    child.setBackgroundColor(setWhite ? getResources().getColor(R.color.white) :
                                                        getResources().getColor(R.color.light_blue));
                    addTerm.setEnabled(true);
                }
                if (termSelected == position) {
                    child.setBackgroundColor(getResources().getColor(R.color.highlighted_text_material_light));
                }
            }
            setWhite = !setWhite;
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
