package com.example.jordan.ukelectionsapp;

import android.support.annotation.NonNull;
import android.util.Log;

import java.sql.Array;

/**
 * Created by Jordan on 22/03/2018.
 */

public class ScannedResult {
    private String fullCode;
    private int numCandidates;
    private String[] splitCode;
    private String correctSourceCode;

    public ScannedResult(String fullCode) {
        this.fullCode = fullCode;
        correctSourceCode = "UKVOTINGV1";

        this.splitCode = splitCode();

        fromCorrectSource();
    }

    public void setNumCandidates(int numCandidates) {

        if(splitCode != null) {
            //Do Nothing
        }
        numCandidates = splitCode.length;
    }
    public int getNumCandidates() {
        numCandidates = fullCode.length() - 1; // 1 subtracted to account for source check
        Log.d("Code Check", "Number of Candidates:" + numCandidates);

        return numCandidates;
    }

    public String[] getCandidateCodes() {
        return splitCode;
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
