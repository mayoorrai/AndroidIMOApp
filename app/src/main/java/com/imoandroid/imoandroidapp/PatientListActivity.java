package com.imoandroid.imoandroidapp;
import com.imoandroid.imoandroidapp.model.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.Window;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PatientListActivity extends Activity{
    retrievePatientData thing;
    public List<Patient> patientList = new ArrayList<Patient>();
    public ListView mDrawerList;
    public Button addPatient;
    public final String TAG = PatientListActivity.class.getSimpleName();
    private ActionBarDrawerToggle mDrawerToggle;

    /**
     *     Navbar logic
      */
    private DrawerLayout mDrawerLayout;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    public ArrayList<String> navDrawerPatientNames;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerPatients;
   // private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_patient_list);

        Log.v(TAG, "Hello");
        // Connect to URL
        if (isNetworkAvailable()) {
            thing = new retrievePatientData();
            thing.execute();
        }
        else {
            Toast.makeText(this, "Network Unavailable!",  Toast.LENGTH_LONG).show();
        }


        mTitle = "All Patients";
        mDrawerTitle = getTitle();


        ActionBar actionBar = getActionBar();
        actionBar.show();


        SystemClock.sleep(10000);
        // load slide menu items (with patient names)
        Log.v(TAG, "Entering getPatientNames()...");
        getPatientNames();
        Log.v(TAG, "Length: " + navDrawerPatientNames.size());

        // initialize drawer layouts
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.patientListView);
        Log.v(TAG, "----" + mDrawerList);
        navDrawerPatients = new ArrayList<NavDrawerItem>();

        populatePatientList();

        // Populate navDrawer with patient names
        for (int i = 0; i < patientList.size(); i++) {
            navDrawerPatients.add(new NavDrawerItem(patientList.get(i)));
        }

        // enabling action bar app icon and behaving it as toggle button

getActionBar().setDisplayHomeAsUpEnabled(true);
getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
               getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);



       // addPatient = (Button) findViewById(R.id.buttonAddNewPatient);
//        addPatient.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                patientClickHandler(null);
//            }
//        });
    }


    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

   @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
//
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void getPatientNames() {
        navDrawerPatientNames = new ArrayList<String>();
        for (int i = 0; i < patientList.size(); i++) {
            navDrawerPatientNames.add(patientList.get(i).getDemo().createFullNameGenerator());
        }
    }

    public void toastTheThing(String thing)
    {

        Toast.makeText(this, thing,  Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_patient_add)
        {
            Intent mainIntent = new Intent(this,PatientInputForm.class);
            mainIntent.putExtra("create", true);
            this.startActivity(mainIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void populatePatientList() {
        DisplayPatientAdapter listAdapter = new DisplayPatientAdapter(this, R.layout.activity_patient_holder, (ArrayList<Patient>) patientList);
        mDrawerList.setAdapter(listAdapter);
        mDrawerList.setOnItemClickListener(new OnPatientClick());
    }

    public void patientClickHandler(Patient p) {
        if (p == null) {
            // new patient (empty fields)
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("create", true);
            startActivity(intent);
        }
        else {
            // update patient (nonempty fields)
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(Constants.TAG_FIRST_NAME, p.getDemo().getFirstName());
            intent.putExtra(Constants.TAG_LAST_NAME, p.getDemo().getLastName());
            intent.putExtra(Constants.TAG_GENDER, p.getDemo().get_gender().getNum());
            intent.putExtra(Constants.TAG_DOB, p.getDemo().getDOB().getTime());
            intent.putExtra(Constants.TAG_ADDRESS1, p.getDemo().address.getAddress1());
            intent.putExtra(Constants.TAG_ADDRESS2, p.getDemo().address.getAddress2());
            intent.putExtra(Constants.TAG_ADDRESS2, p.getDemo().address.getAddress2());
            intent.putExtra(Constants.TAG_CITY, p.getDemo().address.getCity());
            intent.putExtra(Constants.TAG_LANGUAGE, p.getDemo().getLanguage());
            intent.putExtra(Constants.TAG_STATE, p.getDemo().address.getState());
            intent.putExtra(Constants.TAG_MOBILE, p.getDemo().address.getMobilePhone());
            intent.putExtra(Constants.TAG_HOME, p.getDemo().address.getHomePhone());
            intent.putExtra(Constants.TAG_OFFICE, p.getDemo().address.getOfficePhone());
            intent.putExtra(Constants.TAG_NOTES, p.getDemo().getNotes());
            intent.putExtra("create", false);
            startActivity(intent);
        }
    }




    // HTTP and JSON Logic
    private class retrievePatientData extends AsyncTask<Object, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(Object... params) {
            int responseCode = -1;
            char[] inputBuffer = new char[256];
            JSONArray jsonPatientArray = null;

            try {
                URL patientsURL = new URL("http://noteworthy.web.engr.illinois.edu/patient1.json");
                HttpURLConnection connection = (HttpURLConnection) patientsURL.openConnection();
                connection.connect();

                responseCode = connection.getResponseCode();
                // if responseCode == 200, download data
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();

                    String responseData = slurp(inputStream, inputBuffer);
                    Log.i(TAG, responseData);
                    jsonPatientArray = new JSONArray(responseData);


                    // Fill patientsList
                    for (int i = 0; i < jsonPatientArray.length(); i++) {
                        JSONObject o = jsonPatientArray.getJSONObject(i);
                        JSONObject jsonAddressDeets = o.getJSONObject(Constants.TAG_ADDRESS);

                        Patient curr = new Patient();
                        curr.getDemo().setFirstName(o.getString(Constants.TAG_FIRST_NAME));
                        curr.getDemo().setLastName(o.getString(Constants.TAG_LAST_NAME));
                        curr.getDemo().setDOB(o.getLong(Constants.TAG_DOB));
                        String g = o.getString(Constants.TAG_GENDER);
                        if (g.equals("m")) {
                            curr.getDemo().set_gender(Demographics.Gender.M);
                        }
                        else if (g.equals("f")) {
                            curr.getDemo().set_gender(Demographics.Gender.F);
                        }
                        else {
                            curr.getDemo().set_gender(Demographics.Gender.Other);
                        }
                        curr.getDemo().setLanguage(o.getString(Constants.TAG_LANGUAGE));
                        curr.getDemo().setNotes(o.getString(Constants.TAG_NOTES));
                        // New Address Object
                        PatientAddress currPatientAddress = new PatientAddress();
                        // JSON: Address Details
                        currPatientAddress.setAddress1(jsonAddressDeets.getString(Constants.TAG_ADDRESS1));
                        currPatientAddress.setAddress2(jsonAddressDeets.getString(Constants.TAG_ADDRESS2));
                        currPatientAddress.setCity(jsonAddressDeets.getString(Constants.TAG_CITY));
                        currPatientAddress.setZip(jsonAddressDeets.getInt(Constants.TAG_ZIP));
                        currPatientAddress.setMobilePhone(jsonAddressDeets.getLong(Constants.TAG_MOBILE));
                        currPatientAddress.setHomePhone(jsonAddressDeets.getLong(Constants.TAG_HOME));
                        currPatientAddress.setOfficePhone(jsonAddressDeets.getLong(Constants.TAG_OFFICE));
                        currPatientAddress.setState(jsonAddressDeets.getString(Constants.TAG_STATE).toUpperCase());
                        curr.getDemo().setAddress(currPatientAddress);
                        Log.i("Test", curr.getDemo().getFirstName());

                        // Add curr to List
                        patientList.add(curr);
                    }

                } else {
                    Log.i(TAG, "Unsuccessful HTTP Response Code: " + responseCode);
                }
            } catch (MalformedURLException e) {
                Log.e(TAG, "Exception caught: ", e);
            } catch (IOException e) {
                Log.e(TAG, "Exception caught: ", e);
            } catch (Exception e) {
                Log.e(TAG, "Exception caught: ", e);
            }
            return null;
        }

        protected void onPostExecute(JSONArray result) {
           // toastTheThing(patientList.get(0).firstName);
            populatePatientList();
        }

        protected String slurp(InputStream is, char[] buffer) throws IOException {
            StringBuilder out = new StringBuilder();
            Reader in = null;
            try {
                in = new InputStreamReader(is, "UTF-8");
                for (;;) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0) {
                        break;
                    }
                    out.append(buffer, 0, rsz);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
                in = null;
            }
            return out.toString();
        }
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;


    }


}
