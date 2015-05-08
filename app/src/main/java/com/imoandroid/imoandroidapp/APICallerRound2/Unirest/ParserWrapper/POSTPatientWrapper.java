package com.imoandroid.imoandroidapp.APICallerRound2.Unirest.ParserWrapper;

import android.util.Log;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imoandroid.imoandroidapp.APICallerRound2.Unirest.POST.POSTPatient;
import com.imoandroid.imoandroidapp.Demographics;
import com.imoandroid.imoandroidapp.Patient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by samarthchopra on 4/14/15.
 */
public class POSTPatientWrapper {

    public static String poster(Patient p) throws JsonProcessingException {
        Demographics demo = p.getDemo();
        ObjectMapper mapper = new ObjectMapper();
        String back = null;

        back = mapper.writeValueAsString(demo);

        Log.v("Poster", back);

        try {
            return new POSTPatient().execute(back).get().getBody();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "Failed to create patient";
    }

}