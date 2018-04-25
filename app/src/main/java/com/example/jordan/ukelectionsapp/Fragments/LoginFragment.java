package com.example.jordan.ukelectionsapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jordan.ukelectionsapp.R;
import com.example.jordan.ukelectionsapp.Session;
import com.example.jordan.ukelectionsapp.URL;
import com.example.jordan.ukelectionsapp.VolleySingleton;


import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText userNI, mPassword;
    private Button loginButton;
    private TextView textView;

    private String sessionID;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.i("Session Sign in", "Login fragment created");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        userNI = view.findViewById(R.id.editTextUsername);
        mPassword = view.findViewById(R.id.editTextPassword);
        textView = view.findViewById(R.id.loginText);


        loginButton = view.findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = userNI.getText().toString();
                String password = mPassword.getText().toString();

                if( cleanAndCheck(number, password)) {loginRequest(number, password); }
            }
        });
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.logOut).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

 private void loginRequest(final String number, final String password){
     String url = URL.LOGIN;

     StringRequest strRequest = new StringRequest(Request.Method.POST, url,
             new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response)
                 {
                     String[] splitResponse = response.split(",");
                     if(splitResponse[0].equals("1")) {
                         sessionID = splitResponse[1];
                         Session.getInstance().setID(sessionID);

                         Log.i("Session Sign in", "Session ID: " + sessionID + " ; Ni Number: " + number);
                         Session.getInstance().setNiNumber(number);

                         textView.setText("Success" + response);



                         handleLogin();
                     }
                     else {

                         textView.setText("Fail" + response + "::::" + splitResponse[0]);
                     }
                 }
             },
             new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error)
                 {
                     textView.setText(error.getLocalizedMessage());
                 }
             })
     {
         @Override
         protected Map<String, String> getParams() {
             Map<String, String> params = new HashMap<String, String>();
             params.put("UserName", number);
             params.put("Password", password);

             return params;
         }
     };

// Access the RequestQueue through your singleton class.
     VolleySingleton.getInstance(getActivity()).addToRequestQueue(strRequest);
 }

    public void handleLogin() {
        // Create fragment and give it an argument specifying the article it should show
        textView.setText("Attempting to change fragment");
        WebCheckFragment newFragment = new WebCheckFragment();
        Bundle args = new Bundle();
        newFragment.setArguments(args);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the trasaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);

// Commit the transaction
        transaction.commit();
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

    public boolean cleanAndCheck(String number, String pass) {
     Boolean result = true;

     return result;
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
}
