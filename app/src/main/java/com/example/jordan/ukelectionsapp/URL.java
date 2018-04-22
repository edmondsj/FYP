package com.example.jordan.ukelectionsapp;

/**
 * Created by Jordan on 18/04/2018.
 * Store all URLS required within the application
 *
 * Corresponding notes refer to the fragment/activity of usage
 */

public class URL {

    private static String DOMAIN = "http://localhost:5000/duvote/app/";

    public static String LOGIN = DOMAIN + "login_app.php"; //Login Fragment
    static String LOGOUT = DOMAIN  + "logout_app.php"; //Main Activity
    public static String CHECKWEB = DOMAIN + "check_web.php?number="; //Web Check Fragment, uses GET
    public static String SETSTAGE = DOMAIN + "set_stage.php"; //DisplayFinalCode
    public static String CHECKBALLOT = DOMAIN + "check_ballot"; //DisplayFinalCode
    public static String CHECKCANDIDATE = DOMAIN + "check_candidate"; //ConfirmCandidate
    public static String SENDCONTRACT = DOMAIN + "send_contract"; //ConfirmCandidate


}
