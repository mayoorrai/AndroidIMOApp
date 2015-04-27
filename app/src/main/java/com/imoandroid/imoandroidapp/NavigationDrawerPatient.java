package com.imoandroid.imoandroidapp;

import android.app.Activity;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;


public class NavigationDrawerPatient extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private FragmentTabHost mTabHost;

    public final String TAG = NavigationDrawerPatient.class.getSimpleName();

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    TabWidget tabWidget;

    Patient p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_navigation_drawer_patient);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        initializeTabs(null);
    }



    public void initializeTabs(final Patient p) {
        // Initialize tabhost (parent)
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);


        // Initialize tabs and tabContent (children)
        tabWidget = mTabHost.getTabWidget();
        FrameLayout tabContent = mTabHost.getTabContentView();


        // Dx tab

        Bundle b = new Bundle();

        if(p != null){
          mTabHost.setCurrentTab(0);
          mTabHost.clearAllTabs();
            b.putString("firstName" , p.getDemo().getFirstName());
            b.putString("lastName" , p.getDemo().getLastName());
        }
        else{
            b = null;
        }


        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Dx", null),
                DxFragment.class, b);


        // Rx tab
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Rx", null),
                RxFragment.class, b);

        // Hx tab
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Hx", null),
                HxFragment.class, b);

        // Tx tab
        mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator("Tx", null),
                TxFragment.class, b);

   /*   mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
              if("tab1".equals(tabId)) {
                    if(p != null) {
                        Log.v(TAG, "+++++++" + "-----DX");
                        updateDx(p);
                    }

             }
               if("tab2".equals(tabId)) {
                     Log.v(TAG, "+++++++" + "-----RX");
                 }
            }});*/

        // set Dx tab as default*/
        mTabHost.setCurrentTab(0);

        //Log.v(TAG , "Current tab is " + mTabHost.getChildAt(5).toString());
    }

    private void updateDx(Patient patient){

        DxFragment dx = (DxFragment) getSupportFragmentManager().findFragmentByTag("tab1");
        dx.refresh(patient);


    }

    private void updateRx(Patient patient){

        RxFragment rx = (RxFragment) getSupportFragmentManager().findFragmentByTag("tab2");
        rx.refresh(patient);

    }

    private void updateTx(Patient patient){

        TxFragment tx = (TxFragment) getSupportFragmentManager().findFragmentByTag("tab4");
        tx.refresh(patient);
    }

    private void updateHx(Patient patient){

        HxFragment hx = (HxFragment) getSupportFragmentManager().findFragmentByTag("tab3");
        hx.refresh(patient);

    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void givePatientToActivity(Patient p) {
        if (p != null) {

            Constants.CurrentPat = p;
           int tab = mTabHost.getCurrentTab();

           switch (tab) {
               case Constants.DX_TAB:
                   updateDx(p);
                   break;
               case Constants.RX_TAB:
                   updateRx(p);
                   break;
               case Constants.HX_TAB:
                   updateHx(p);
                   break;
               case Constants.TX_TAB:
                   updateTx(p);
                   break;
               default:
           }



        }

    }

    public void onSectionAttached(int number) {
        mTitle = "Patient Details";
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.navigation_drawer_patient, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_navigation_drawer_patient, container, false);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((NavigationDrawerPatient) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
