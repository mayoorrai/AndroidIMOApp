package com.imoandroid.imoandroidapp;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_patient);
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
         etlastName = (EditText)findViewById(R.id.lastNameBox);;
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
