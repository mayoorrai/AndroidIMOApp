package com.imoandroid.imoandroidapp.APICallerRound2.Unirest.GET;

import android.os.AsyncTask;
import android.util.Log;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by samarthchopra on 4/12/15.
 */
public class GETSpecificPatient extends AsyncTask<String, Integer, HttpResponse<JsonNode>> {

    protected HttpResponse<JsonNode> doInBackground(String... msg) {
        HttpResponse<JsonNode> request = null;

        Log.v("MSG: " , msg[0]);

        try {
            request = Unirest.get("http://66.252.70.193/{method}")
                    .routeParam("method","patients")
                    .queryString("apiKey","QhIno484vsazggxvZgf2EGZjkYunH24f7MNz5JXmI83bDMTOgmwVw6eqss7I18U7")
                    .queryString("firstName",msg[0])
                    .queryString("lastName",msg[1])
                    .queryString("id",msg[2])
                    .asJson();
        } catch (UnirestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.v("REQUEST: " , request.toString());

        return request;
    }

    protected void onProgressUpdate(Integer...integers) {
    }
    protected void onPostExecute(HttpResponse<JsonNode> response) {

    }
}
