package com.example.jordan.ukelectionsapp;

import android.support.annotation.NonNull;
import android.util.Log;

import java.sql.Array;

/**
 * Created by Jordan on 22/03/2018.
 */

public class ScannedResult {
    private static String fullCode;
    private int numCandidates;
    private String[] splitCode;
    private String correctSourceCode;

    private static ScannedResult instance;


    private ScannedResult() {
        correctSourceCode = "UKVOTINGV1";
    }

    public static ScannedResult getInstance(){
        if (instance == null){ //if there is no instance available... create new one
            instance = new ScannedResult();
        }

        return instance;
    }

    public void create(String code) {
        this.fullCode = code;
        this.splitCode = splitCode();
        fromCorrectSource();

    }

    public int getNumCandidates() {
        numCandidates = fullCode.length() - 1; // 1 subtracted to account for source check

        return numCandidates;
    }

    public String[] getCandidateCodes() {
       String[] candidateCodes = new String[splitCode.length - 1];
        for (int x = 1; x < splitCode.length; x++) {
            candidateCodes[x - 1] = splitCode[x];
        }
        return candidateCodes;
    }


    @NonNull
    private String[] splitCode() {
        return fullCode.split(",");

    }

    private boolean fromCorrectSource() {
        if (splitCode[0].equals(correctSourceCode)) {
            Log.d("Code Check", "Correct Code Source" + splitCode[0]);
            return true;
        } else {
            Log.d("Code Check", "Code not from Correct Source: " + splitCode[0]);
            return false;
        }
    }
}
