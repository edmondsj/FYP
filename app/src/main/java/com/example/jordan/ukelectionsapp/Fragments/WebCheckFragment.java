package com.example.jordan.ukelectionsapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jordan.ukelectionsapp.R;
import com.example.jordan.ukelectionsapp.Session;
import com.example.jordan.ukelectionsapp.URL;
import com.example.jordan.ukelectionsapp.VolleySingleton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebCheckFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebCheckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebCheckFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String session;

    private Handler handler;
    private Runnable runnable;

    private OnFragmentInteractionListener mListener;

    private Menu menu;

    public WebCheckFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WebCheckFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebCheckFragment newInstance(String param1, String param2) {
        WebCheckFragment fragment = new WebCheckFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        session = Session.getInstance().getSessionNumber();

        setHasOptionsMenu(true); //Inform gradle that menu needs changing

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Log.d("HandlerIteration", "Running" );
                check();
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 1500);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //session = Session.getInstance().getSessionNumber();
        Log.d("SessionID Login", session);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web__check_, container, false);
    }

    public void check() {

        String url = URL.CHECKWEB + session;
        Log.d("Testing", url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Sign In Response", response);
                        // Display the first 500 characters of the response string.
                        if (response.equals("Sign in Web")) {

                            FlipFragment newFragment = new FlipFragment();
                            Bundle args = new Bundle();
                            newFragment.setArguments(args);

                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                            // Replace whatever is in the fragment_container view with this fragment,
                            // and add the transaction to the back stack so the user can navigate back
                            transaction.replace(R.id.fragment_container, newFragment);

                            // Commit the transaction
                            transaction.commit();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Sign In Response", error.getStackTrace().toString());

            }
        });

// Add the request to the RequestQueue.
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //Toast.makeText(getActivity(), "Preparing Menu", Toast.LENGTH_LONG).show();
        menu.findItem(R.id.logOut).setVisible(false);
        MenuItem menuItem = menu.findItem(R.id.menu_sessionID);
        menuItem.setTitle("Session:" + Session.getInstance().getSessionNumber());
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

}
