package com.imoandroid.imoandroidapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//TODO: Add picture functionality
//TODO: Test that date and age logic is sound

public class PatientInputForm extends ActionBarActivity {

    // Fields
    EditText etfirstName;
    EditText etlastName;
    EditText etlanguage;
    Spinner etgender;
    EditText etaddress1;
    EditText etaddress2;
    EditText etcity;
    EditText etzip;
    EditText etstate;
    EditText etmobile;
    EditText ethome;
    EditText etoffice;
    EditText etnotes;
    TextView tvDate;
    Button setDate;
    int ageInt=-1;
    TextView age;
    Button submit;
    long datePicked;
    SimpleDateFormat sdf;

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
            etgender.setSelection(intent.getIntExtra(Constants.TAG_GENDER,2));
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
            String getDOB = "Date of Birth: " + sdf.format(date);
            tvDate.setText(getDOB);
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
        ageInt = _age;
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
        sdf = new SimpleDateFormat("MM/dd/yyyy");

        etfirstName = (EditText)findViewById(R.id.firstNameBox);
        etlastName = (EditText)findViewById(R.id.lastNameBox);
        etlanguage = (EditText)findViewById(R.id.languageBox);
        etgender = (Spinner)findViewById(R.id.genderSpinner);
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
        tvDate = (TextView)findViewById(R.id.tvDate);
        setDate = (Button)findViewById(R.id.dateButton);
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog();
            }
        });
        submit = (Button)findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAndSend();
            }
        });

    }

    private void dateDialog() {
        DatePickerDialog d = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = (monthOfYear+1) + "/" + dayOfMonth + "/" + year;
                try {
                    Date picked = sdf.parse(date);
                    datePicked = picked.getTime();
                    String getDOB = "Date of Birth: " + sdf.format(picked);
                    tvDate.setText(getDOB);
                    updateAge();
                } catch (Exception e)
                {
                    //TODO: handle error?
                }
            }
        },1990,1,1);

        d.show();
    }

    private boolean checkIfFilled(EditText et, StringBuilder sb)
    {
        if(et.getText().toString().isEmpty())
        {
            et.setBackgroundColor(Color.parseColor("#ffe5e5"));
            sb.append("\n"+et.getHint());
            return false;
        }
        et.setBackgroundColor(Color.parseColor("#ffffff"));
        return true;
    }

    private void verifyAndSend() {
        StringBuilder error = new StringBuilder();
        boolean allGood = true;
        boolean phone = !ethome.getText().toString().isEmpty() ||
                !etmobile.getText().toString().isEmpty() ||
                !etoffice.getText().toString().isEmpty();

        int phoneColor = Color.parseColor(phone ? "#ffffff" : "#ffe5e5");

        ethome.setBackgroundColor(phoneColor);
        etmobile.setBackgroundColor(phoneColor);
        etoffice.setBackgroundColor(phoneColor);

        if(!phone)
        {
            error.append("Phone Number(s)");
        }

        //Verify Basic Details: EditTexts
        boolean basicFields = checkIfFilled(etfirstName, error);
        basicFields = checkIfFilled(etlastName, error) && basicFields;
        basicFields = checkIfFilled(etaddress1, error) && basicFields;
        basicFields = checkIfFilled(etcity, error) && basicFields;
        basicFields = checkIfFilled(etlanguage, error) && basicFields;
        basicFields = checkIfFilled(etstate, error) && basicFields;
        basicFields = checkIfFilled(etzip, error) && basicFields;

        boolean ageSet = ageInt >= 0;
        if(!ageSet)
        {
            error.append("\nDate of Birth");
        }

        allGood = ageSet && phone && basicFields;

        if(allGood)
        {
            //make JSON patient, then API call, then go to Tab Layout
            JSONObject patient = new JSONObject();
            try {
                patient.put("first_name", etfirstName.getText().toString());
                patient.put("last_name", etlastName.getText().toString());
                patient.put("dob", datePicked);
                switch (etgender.getSelectedItemPosition()) {
                    case 0:
                        patient.put("gender", "m");
                        break;
                    case 1:
                        patient.put("gender", "f");
                        break;
                    case 2:
                        patient.put("gender", "other");
                        break;
                }
                patient.put("language", etlanguage.getText().toString());

                JSONObject address = new JSONObject();
                address.put("address1", etaddress1.getText().toString());
                address.put("address2", etaddress2.getText().toString());
                address.put("city", etcity.getText().toString());
                address.put("state", etstate.getText().toString());
                address.put("zip", Integer.parseInt(etzip.getText().toString()));
                String num = etmobile.getText().toString();
                address.put("mobile", num.isEmpty()? null : Long.parseLong(num));
                num = ethome.getText().toString();
                address.put("home", num.isEmpty()? null : Long.parseLong(num));
                num = etoffice.getText().toString();
                address.put("office", num.isEmpty()? null : Long.parseLong(num));

                patient.put("address", address);
                patient.put("notes", etnotes.getText().toString());
            }
            catch (Exception e)
            {
                new AlertDialog.Builder(this)
                        .setTitle("Failed to serialize patient:")
                        .setMessage(e.toString())
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("To be sent:")
                    .setMessage(patient.toString() + "\n" + Constants.API_SERVER+"patients?apiKey="+Constants.API_KEY)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
            /**
            HttpClient httpClient = new DefaultHttpClient();

            //TODO: ask Web Team about how to use api key...
            try {
                HttpPost request = new HttpPost(Constants.URL+"patients");
                StringEntity params =new StringEntity("value=" + patient.toString());
                request.addHeader("content-type", "application/json");
                request.addHeader("Accept","application/json");
                request.setEntity(params);
                HttpResponse response = httpClient.execute(request);

                // handle response here... go to tablayout
            }catch (Exception ex) {
                // handle exception here
            } finally {
                httpClient.getConnectionManager().shutdown();
            }**/
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("The following entries are required:")
                    .setMessage(error.toString())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}

