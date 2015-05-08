package com.imoandroid.imoandroidapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.imoandroid.imoandroidapp.APICaller.APICaller;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.DELETE.DELETEPatient;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.GET.GETSpecificPatient;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.GET.GetPatients;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.ParserWrapper.POSTPatientWrapper;
import com.mashape.unirest.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.concurrent.ExecutionException;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    /**
     *
     *
     */

    public final String TAG = Patient.class.getSimpleName();


    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private Button createButton;
    private ListView mDrawerListView;
    private EditText patientSearch;
    private View mFragmentContainerView;
    private ImageButton clear;
    private Spinner sorts;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    private DisplayPatientAdapter listAdapter;

    private List<Patient> allPatients = new ArrayList<Patient>();

    private List<Patient> tempPatients = new ArrayList<Patient>();

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        HttpResponse<JsonNode> patientData = null;





        try {
            patientData = new GetPatients().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        Log.v("NEWNEW--", patientData.getBody().toString());

        PatientParser p = new PatientParser();
        allPatients = p.parsePatients(patientData.getBody().toString());

        tempPatients = new ArrayList<Patient>(allPatients);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_navigation_drawer  , container , false);


        mDrawerListView = (ListView) v.findViewById(R.id.patientLV);
        patientSearch = (EditText) v.findViewById(R.id.etPatients);
        createButton = (Button) v.findViewById(R.id.addPatientButton);
        createButton.setOnClickListener(this);
        sorts = (Spinner)v.findViewById(R.id.spinSort);
        clear = (ImageButton)v.findViewById(R.id.clearPat);
        clear.setVisibility(View.GONE);
        clear.setOnClickListener(this);

        listAdapter = new DisplayPatientAdapter(v.getContext() , R.layout.activity_patient_holder , (ArrayList<Patient>) tempPatients);
        mDrawerListView.setAdapter(listAdapter);

        patientSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean exists = s.length() > 0;
                clear.setVisibility(exists? View.VISIBLE: View.GONE);

                List<Patient> searchPatients = new ArrayList<Patient>();
                for(int i = 0 ; i < allPatients.size() ; i++){
                    Patient p = allPatients.get(i);
//                    Log.v("------>", p.getDemo().getFirstName());
//                    Log.v("------>", p.getDemo().getLastName());

                    Demographics d = p.getDemo();
//                    Log.v("-->First",d.getFirstName() );

                    if((d.getFirstName()).toLowerCase().contains(s) || (d.getLastName()).toLowerCase().contains(s)) {
                        searchPatients.add(p);
                    }
                }
                Collections.sort(searchPatients,sorts.getSelectedItemPosition() == 0 ?
                        new Constants.NameComp():new Constants.AgeComparator());
                listAdapter.updateDisplay((ArrayList<Patient>)searchPatients);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDrawerListView.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Patient p = (Patient)parent.getItemAtPosition(position);
       // Toast.makeText(getActivity().getApplicationContext(), p.getDemo().getFirstName(), Toast.LENGTH_SHORT).show();
       ((NavigationDrawerPatient) getActivity()).givePatientToActivity(p);
       // Toast.makeText(getActivity().getApplicationContext(), "Does it work" + returnValue, Toast.LENGTH_SHORT).show();


    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);

        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_example) {
            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }

    @Override
    public void onClick(View v)
    {
        if(v==clear)
        {
            patientSearch.setText("");
        }
        else if(v==createButton)
        {
            Intent i = new Intent(getActivity(),PatientInputForm.class);
            i.putExtra("create",true);
            startActivityForResult(i,1);
        }
        else {
            final Patient del = (Patient) v.getTag();
            final Demographics demo = del.getDemo();
            Log.v(TAG,"TO DELETE: "+demo.createFullNameGenerator()+"- "+String.valueOf(demo.getId()));
            new AlertDialog.Builder(getActivity())
                    .setTitle("Delete patient")
                    .setMessage("Are you sure you want to delete "+demo.createFullNameGenerator()+"?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            HttpResponse<String> response;
                            try{
                                response = new DELETEPatient().execute(demo.getFirstName(),demo.getLastName(),
                                        String.valueOf(demo.getId())).get();
                                Log.v(TAG,"DELETION STATUS: "+response.getBody());
                                if(response.getBody().contains("success"))
                                {
                                    listAdapter.RemovePatient(del);
                                    return;
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getActivity(),"Could not delete patient!",Toast.LENGTH_SHORT);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==1)
        {
            if(resultCode==Constants.RESULT_OK)
            {
                Patient created = data.getParcelableExtra("patient");

                try {
                    String msg = POSTPatientWrapper.poster(created);
                    if(msg.contains("success")) {
                        Demographics demo = created.getDemo();
                        HttpResponse<JsonNode> patientData;
                        JSONArray array;
                        try {
                            patientData = new GetPatients().execute().get();
                            JSONObject json = new JSONObject(patientData.getBody().toString());
                            array = json.getJSONArray("patients");
                            for(int i = 0;i<array.length();i++)
                            {
                                JSONObject patient = array.getJSONObject(i);

                                if(patient.getString("first_name").equals(demo.getFirstName()) &&
                                        patient.getString("last_name").equals(demo.getLastName()))
                                {
                                    created.getDemo().setId(patient.getInt("id"));
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getActivity(), created.getDemo().createFullNameGenerator() + " created", Toast.LENGTH_SHORT).show();
                        listAdapter.AddPatient(created);
                        mDrawerListView.setSelection(0);
                        mDrawerListView.performItemClick(mDrawerListView.getChildAt(0), 0, listAdapter.getItemId(0));
                    }

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),created.getDemo().createFullNameGenerator()+ " Could not create!!!",Toast.LENGTH_SHORT).show();
                }
                //Add patient to TabHost selected
            }
            else if( resultCode == Constants.RESULT_CANCEL)
            {
                Toast.makeText(getActivity(),"Did not create patient",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class DisplayPatientAdapter extends ArrayAdapter<Patient>{
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

        public void AddPatient(Patient p)
        {
            data.add(0,p);
            notifyDataSetChanged();
        }

        public void RemovePatient(Patient p)
        {
            try {
                data.remove(p);
                notifyDataSetChanged();

                if(Constants.CurrentPat.equals(p)) { //TODO: should override .equals
                    mDrawerListView.setSelection(0);
                    mDrawerListView.performItemClick(mDrawerListView.getChildAt(0), 0, listAdapter.getItemId(0));
                }
            }
            catch (Exception e)
            {
                Log.v(TAG,"SDFDSGFDGFD Error removing patient");
                return;
            }
        }

        public void updateDisplay(ArrayList<Patient> update)
        {
            data.clear();
            for(int i=0;i<update.size();i++){
                data.add(update.get(i));
            }
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View child, ViewGroup parent) {
            TextView pat;
            Button del;
            if (child == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                child = inflater.inflate(layoutResourceID, parent, false);
            }

            pat = (TextView) child.findViewById(R.id.title);
            del = (Button) child.findViewById(R.id.delPat);
            Patient p = data.get(position);
            pat.setTag(p);
            del.setTag(p);
            pat.setText(p.getDemo().createFullNameGenerator());

            del.setOnClickListener(NavigationDrawerFragment.this);

            return child;
        }


    }
}
