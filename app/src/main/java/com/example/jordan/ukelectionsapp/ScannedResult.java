package com.example.jordan.ukelectionsapp;

import android.support.annotation.NonNull;
import android.util.Log;
/**
 * Created by Jordan on 22/03/2018.
 * Store the results of the scanned QR Code
 */

public class ScannedResult {
    private static String fullCode;
    private String[] splitCode;
    private String correctSourceCode;
    private int[] selectedCode;

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
        fullCode = code;
        this.splitCode = splitCode();
        fromCorrectSource();
        selectedCode = new int[4];
    }

    public void setSelectedCode(int[] code) {
        selectedCode = code;
    }

    public int[] getSelectedCode() {
        return selectedCode;
    }

    public String[] getCandidateCodes() {
       String[] candidateCodes = new String[splitCode.length - 1];
        System.arraycopy(splitCode, 1, candidateCodes, 0, splitCode.length - 1);
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
