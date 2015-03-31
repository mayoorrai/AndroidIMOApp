package com.imoandroid.imoandroidapp;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TabWidget;

import com.imoandroid.imoandroidapp.model.NavDrawerItem;

import java.util.ArrayList;


public class FoundationActivity extends FragmentActivity {
    public final String TAG = FoundationActivity.class.getSimpleName();

    /**
     * Navbar logic
     */
    private DrawerLayout mDrawerLayout;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;            // used to store app title
    private ArrayList<String> navDrawerPatientNames;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerPatients;

    /**
     * Tab FragmentHost
     */
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foundation);

        initializeTabs();
        initializeNavbar();

    }

    private void initializeNavbar() {
        mTitle = "All Patients";
        mDrawerTitle = getTitle();

        /**
         * TODO listview logic
         */
    }

    private void initializeTabs() {
        // Initialize tabhost (parent)
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        // Initialize tabs and tabContent (children)
        TabWidget tabWidget = mTabHost.getTabWidget();
        FrameLayout tabContent = mTabHost.getTabContentView();

        // Dx tab
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Dx", null),
                DxFragment.class, null);

        // Rx tab
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Rx", null),
                RxFragment.class, null);

        // Hx tab
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Hx", null),
                HxFragment.class, null);

        // Tx tab
        mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator("Tx", null),
                TxFragment.class, null);

        // set Dx tab as default
        mTabHost.setCurrentTab(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foundation, menu);
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
}
