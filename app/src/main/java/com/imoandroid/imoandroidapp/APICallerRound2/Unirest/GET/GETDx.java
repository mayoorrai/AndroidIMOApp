package com.imoandroid.imoandroidapp.APICallerRound2.Unirest.GET;

import android.os.AsyncTask;
import android.util.Log;

import com.imoandroid.imoandroidapp.Constants;
import com.imoandroid.imoandroidapp.Patient;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by samarthchopra on 4/12/15.
 */
public class GETDx extends AsyncTask<String, Integer, HttpResponse<JsonNode>> {

    public final String TAG = GETDx.class.getSimpleName();
    protected HttpResponse<JsonNode> doInBackground(String... msg) {
        HttpResponse<JsonNode> request = null;

        Log.v(TAG , "$$$$$$$$$$" + msg[0]);

        try {
            request = Unirest.get("http://66.252.70.193/{method}/{method2}")
                    .routeParam("method","vocabulary")
                    .routeParam("method2", Constants.DX)
                    .queryString("query",msg[0])
                    .queryString("limit",10)
                    .queryString("apiKey",Constants.API_KEY)
                    .asJson();


        } catch (UnirestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return request;
    }

    protected void onProgressUpdate(Integer...integers) {
    }
    protected void onPostExecute(HttpResponse<JsonNode> response) {

    }
}