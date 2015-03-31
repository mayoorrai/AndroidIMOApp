package com.imoandroid.imoandroidapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mayoorrai on 3/31/15.
 */

public class DxFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foundation, container, false);
        TextView tv = (TextView) v.findViewById(R.id.fragment_textView);
        tv.setText(this.getTag() + " Content");
        return v;
    }
}
