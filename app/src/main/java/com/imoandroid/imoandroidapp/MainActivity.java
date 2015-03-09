package com.imoandroid.imoandroidapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends Activity implements ActionBar.TabListener {
    public final String TAG = MainActivity.class.getSimpleName();

    // Get list of tabs
    List tabFragmentList = new ArrayList();

    // Fields
    EditText etfirstName;
    EditText etlastName;
    EditText etlanguage;
    Spinner etgender;
    DatePicker etdob;
    EditText etaddress1;
    EditText etaddress2;
    EditText etcity;
    EditText etzip;
    EditText etstate;
    EditText etmobile;
    EditText ethome;
    EditText etoffice;
    EditText etnotes;
    TextView age;
    long datePicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Entering MainActivity: onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Logic for Action Bar
        ActionBar actionBar = getActionBar();
        Log.v(TAG, "-------" + actionBar);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // set tab listeners for all tabs
        Tab[] tabs = new Tab[4];
        for (int i = 0; i < 4; i++) {
            tabs[i] = actionBar.newTab();
            switch (i) {
                case 0: tabs[i].setText("Dx");
                    break;
                case 1: tabs[i].setText("Rx");
                    break;
                case 2: tabs[i].setText("Hx");
                    break;
                case 3: tabs[i].setText("Px");
                    break;
            }
            tabs[i].setTabListener(this);
            actionBar.addTab(tabs[i]);
        }

        Log.v(TAG, "MainActivity: completed tab listeners...");
        assignFields();
        Intent intent = getIntent();
        if (intent.getBooleanExtra("create", true) == false) {
            etfirstName.setText(intent.getStringExtra(Constants.TAG_FIRST_NAME));
            etlastName.setText(intent.getStringExtra(Constants.TAG_LAST_NAME));
            etlanguage.setText(intent.getStringExtra(Constants.TAG_LANGUAGE));
            etgender.setSelection(0);
            etaddress1.setText(intent.getStringExtra(Constants.TAG_ADDRESS1));
            etaddress2.setText(intent.getStringExtra(Constants.TAG_ADDRESS2));
            etcity.setText(intent.getStringExtra(Constants.TAG_CITY));
            etstate.setText(intent.getStringExtra(Constants.TAG_STATE));
            etzip.setText(intent.getStringExtra(Constants.TAG_ZIP));
            etmobile.setText(intent.getStringExtra(Constants.TAG_MOBILE));
            ethome.setText(intent.getStringExtra(Constants.TAG_HOME));
            etoffice.setText(intent.getStringExtra(Constants.TAG_OFFICE));
            etnotes.setText(intent.getStringExtra(Constants.TAG_NOTES));
            datePicked = intent.getLongExtra(Constants.TAG_DOB, -1);
            Date date = new Date(datePicked);

            etdob.updateDate(date.getYear(),date.getMonth(),date.getDay());
            updateAge();
        }
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        Fragment fragment = null;
        TabFragment tabFragment = null;

//        if (tabFragmentList.size() > tab.getPosition()) {
//            fragment = tabFragmentList.get(tab.getPosition());
//        }

        if (fragment == null) {
            tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            // set color of backgrounds
            int colorResId = 0;
            if (tab.getPosition() == 0) {
                colorResId = R.color.red;
            }
            else if (tab.getPosition() == 1) {
                colorResId = R.color.blue;
            }
            else if (tab.getPosition() == 2) {
                colorResId = R.color.green;
            }
            else if (tab.getPosition() == 3) {
                colorResId = R.color.yellow;
            }
            // place colorResId in bundle
            bundle.putInt("color", colorResId);
            tabFragment.setArguments(bundle);
            tabFragmentList.add(tabFragment);
        }
        else {
            tabFragment = (TabFragment) fragment;
        }
        ft.replace(android.R.id.content, tabFragment);
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//        if (tabFragmentList.size() > tab.getPosition()) {
//            ft.remove(tabFragmentList.get(tab.getPosition()));
//        }
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {

    }

    private void updateAge() {
        Calendar bday = Calendar.getInstance();
        bday.setTimeInMillis(datePicked);

        Calendar today = Calendar.getInstance();
        int _age = today.get(Calendar.YEAR) - bday.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_YEAR) <= bday.get(Calendar.DAY_OF_YEAR))
        {
            _age--;
        }
        age.setText("Age: "+_age);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void assignFields() {
         etfirstName = (EditText)findViewById(R.id.firstNameBox);
         etlastName = (EditText)findViewById(R.id.lastNameBox);
         etlanguage = (EditText)findViewById(R.id.languageBox);
         etgender = (Spinner)findViewById(R.id.genderSpinner);
         etdob = (DatePicker)findViewById(R.id.dobBox);
         etaddress1 =(EditText)findViewById(R.id.address1Box);
         etaddress2 = (EditText)findViewById(R.id.address2Box);
         etcity = (EditText)findViewById(R.id.cityBox);
         etzip = (EditText)findViewById(R.id.zipBox);
         etstate = (EditText)findViewById(R.id.stateBox);
         etmobile = (EditText)findViewById(R.id.mobileBox);
         ethome = (EditText)findViewById(R.id.homeBox);
         etoffice = (EditText)findViewById(R.id.officeBox);
         etnotes = (EditText)findViewById(R.id.notesBox);
         age = (TextView)findViewById(R.id.ageBox);
    }



}
