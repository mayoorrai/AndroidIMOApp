package com.imoandroid.imoandroidapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by mayoorrai on 3/31/15.
 */
public class HxFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foundation, container, false);
        TextView tv = (TextView) v.findViewById(R.id.fragment_textView);
        Button btnSearch = (Button) v.findViewById(R.id.btnSearch);
        tv.setText(this.getTag() + " Content");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                SearchDialogFragment s = SearchDialogFragment.newInstance(2);
                s.show(ft, "Search");
            }
        });

        return v;
    }
}
