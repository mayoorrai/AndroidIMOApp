package com.imoandroid.imoandroidapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by namrataprabhu on 4/15/15.
 */
public class DisplayPTAdapter extends ArrayAdapter<Term> {
    Context mContext;
    int layoutResourceID;
    ArrayList<Term> data = new ArrayList<Term>();

    public DisplayPTAdapter(Context c, int lrID, ArrayList<Term> data)
    {
        super(c,lrID,data);

        mContext = c;
        layoutResourceID = lrID;
        this.data=data;
    }

    public void UpdateTerms(ArrayList<Term> list)
    {
        data.clear();
        for(int i =0;i<list.size();i++)
        {
            data.add(list.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View child, ViewGroup parent)
    {
        TextView code;
        TextView title;
        if(child == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = inflater.inflate(layoutResourceID,parent,false);
        }
        code = (TextView) child.findViewById(R.id.tvAC);
        title = (TextView) child.findViewById(R.id.tvIT);
        Term t =  data.get(position);
        code.setText(t.AdminCode);
        title.setText(t.InterfaceTitle);
//        if(setWhite)
//        {
//            child.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//        }
//        else
//        {
//            child.setBackgroundColor(mContext.getResources().getColor(R.color.black));
//        }

        return child;
    }
}
