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
        if (id == R.id.action_patient_add)
        {
            Intent mainIntent = new Intent(this,PatientInputForm.class);
            mainIntent.putExtra("create", true);
            this.startActivity(mainIntent);

        }
        if(id == R.id.action_patient_edit)
        {
            Intent prevIntent = getIntent();
            Intent intent = new Intent(this,PatientInputForm.class);
            if(prevIntent.getBooleanExtra("create", true) == false)
            {
                intent.putExtra(Constants.TAG_FIRST_NAME, prevIntent.getStringExtra(Constants.TAG_FIRST_NAME));
                intent.putExtra(Constants.TAG_LAST_NAME, prevIntent.getStringExtra(Constants.TAG_LAST_NAME));
                intent.putExtra(Constants.TAG_GENDER, prevIntent.getIntExtra(Constants.TAG_GENDER, 2));
                intent.putExtra(Constants.TAG_DOB, prevIntent.getStringExtra((Constants.TAG_FIRST_NAME)));
                intent.putExtra(Constants.TAG_LANGUAGE, prevIntent.getStringExtra(Constants.TAG_LANGUAGE));
                intent.putExtra(Constants.TAG_ADDRESS1, prevIntent.getStringExtra(Constants.TAG_ADDRESS1));
                intent.putExtra(Constants.TAG_ADDRESS2, prevIntent.getStringExtra(Constants.TAG_ADDRESS2));
                intent.putExtra(Constants.TAG_CITY, prevIntent.getStringExtra(Constants.TAG_CITY));
                intent.putExtra(Constants.TAG_STATE, prevIntent.getStringExtra(Constants.TAG_STATE));
                intent.putExtra(Constants.TAG_ZIP, prevIntent.getStringExtra(Constants.TAG_ZIP));
                intent.putExtra(Constants.TAG_MOBILE, prevIntent.getStringExtra(Constants.TAG_MOBILE));
                intent.putExtra(Constants.TAG_HOME, prevIntent.getStringExtra(Constants.TAG_HOME));
                intent.putExtra(Constants.TAG_OFFICE, prevIntent.getStringExtra(Constants.TAG_OFFICE));
                intent.putExtra(Constants.TAG_NOTES, prevIntent.getStringExtra(Constants.TAG_NOTES));
                intent.putExtra("create", false);
                startActivity(intent);
            }



        }

        return super.onOptionsItemSelected(item);
    }

}
