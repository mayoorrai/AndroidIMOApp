package com.imoandroid.imoandroidapp.APICallerRound2.Unirest.DELETE;

import android.os.AsyncTask;
import android.util.Log;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by samarthchopra on 4/12/15.
 */

// == WORKS ==
public class DELETEPatient extends AsyncTask<String, Integer, HttpResponse<String>> {

    protected HttpResponse<String> doInBackground(String... msg) {
        HttpResponse<String> request = null;

        try {
            request = Unirest.delete("http://66.252.70.193/{method}")
                    .routeParam("method","patients")
                    .queryString("apiKey","QhIno484vsazggxvZgf2EGZjkYunH24f7MNz5JXmI83bDMTOgmwVw6eqss7I18U7")
                    .queryString("firstName",msg[0])
                    .queryString("lastName",msg[1])
                    .queryString("id",msg[2])
                    .asString();

        } catch (UnirestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return request;
    }

    protected void onProgressUpdate(Integer...integers) {
    }
    protected void onPostExecute(HttpResponse<String> response) {

    }
}
