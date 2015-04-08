package com.imoandroid.imoandroidapp.APICaller;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mayoorrai on 4/1/15.
 */
public class tester {
    public static void main (String[] args) {
        ArrayList<Map> temp = APICaller.vocabularyGET("arm", 23);
        for(int i = 0; i < temp.size(); i++) {
            System.out.println(temp.get(i));
        }
    }
}
