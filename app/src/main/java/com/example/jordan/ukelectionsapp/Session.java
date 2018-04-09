package com.example.jordan.ukelectionsapp;

/**
 * Created by Jordan on 30/03/2018.
 */

public class Session {
    private static String id;
    private static Session mInstance;
    private static String niNumber;

    private Session(String id) {
        this.id = id;
    }

    public static synchronized Session getInstance() {
        if (mInstance == null) {
            mInstance = new Session(id);
        }
        return mInstance;
    }

    public void setID(String the_id) {
        id = the_id;
    }

    public String getID() {
        return id;
    }

    public void setNiNumber(String n) {
        niNumber = n;
    }

    public String getNiNumber() {
        return niNumber;
    }
}
