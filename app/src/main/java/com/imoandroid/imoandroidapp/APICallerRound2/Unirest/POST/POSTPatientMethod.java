package com.imoandroid.imoandroidapp.APICallerRound2.Unirest.POST;

import android.os.AsyncTask;
import android.util.Log;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by samarthchopra on 4/12/15.
 */
// == WORKS == => yet to test with actual patient object, though should be able to pass that in body
public class POSTPatientMethod extends AsyncTask<String, Integer, HttpResponse<JsonNode>> {

    protected HttpResponse<JsonNode> doInBackground(String... msg) {
        HttpResponse<JsonNode> request = null;

        try {
            HttpResponse<String> back = Unirest.post("http://66.252.70.193/patients/{id}/{first}/{last}/{method}")
                    .header("accept", "application/json")
                    .routeParam("id", msg[0])
                    .routeParam("first", msg[1])
                    .routeParam("last", msg[2])
                    .routeParam("method" , msg[3])
                    .queryString("apiKey", "QhIno484vsazggxvZgf2EGZjkYunH24f7MNz5JXmI83bDMTOgmwVw6eqss7I18U7")
                    .body(msg[4])
                    .asString();

            Log.v("MSG0", msg[0]);
            Log.v("MSG1", msg[1]);

            Log.v("CREATED", back.getBody().toString());

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