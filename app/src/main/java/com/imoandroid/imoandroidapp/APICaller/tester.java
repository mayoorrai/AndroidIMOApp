package com.imoandroid.imoandroidapp.APICaller;

/**
 * Created by mayoorrai on 4/1/15.
 */
public class tester {
    public static void main (String[] args) {
        String[] temp = APICaller.vocabularyGET("arm", 23);
        for(int i = 0; i < temp.length; i++) {
            System.out.println(temp[i]);
        }
    }
}
