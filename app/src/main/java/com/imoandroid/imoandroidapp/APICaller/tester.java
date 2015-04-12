package com.imoandroid.imoandroidapp.APICaller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mayoorrai on 4/1/15.
 */
public class tester {
    public static void main (String[] args) {
//        ArrayList<Map> temp = APICaller.TxGET("arm", 23);
//        for(int i = 0; i < temp.size(); i++) {
//            System.out.println(temp.get(i));
//        }

       try{
           System.out.println("Hello");
           HttpResponse<JsonNode> response = Unirest.get("http://httpbin.org/{method}")
                   .routeParam("method", "get")
                   .queryString("name", "Mark")
                   .asJson();

           System.out.println(response.toString());

        }
        catch (UnirestException e){

            e.printStackTrace();
        };
    }


}
