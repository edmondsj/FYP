package com.example.jordan.ukelectionsapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jordan.ukelectionsapp.R;
import com.example.jordan.ukelectionsapp.ScannedResult;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CheckCodesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CheckCodesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckCodesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static ArrayList<Button> codeViews;

    private ScannedResult codeObject;

    private OnFragmentInteractionListener mListener;
    private Button proceedButton;

    private static Integer lastClicked;

    public CheckCodesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckCodesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckCodesFragment newInstance(String param1, String param2) {
        CheckCodesFragment fragment = new CheckCodesFragment();
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

        codeViews = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_codes, container, false);

        codeObject = ScannedResult.getInstance();
        String[] candidateCodes = codeObject.getCandidateCodes();


        LinearLayout linearLayout = view.findViewById(R.id.check_codes_layout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for( int i = 0; i < codeObject.getCandidateCodes().length; i++ )
        {
            LinearLayout innerLayout = new LinearLayout(getActivity().getApplicationContext());
            innerLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView labelView = new TextView(getContext());
            labelView.setText("Candidate " + ( i + 1 ) + ": ");
            innerLayout.addView(labelView);

            Button codeView = new Button(getContext());
            codeView.setText(candidateCodes[i]);

            final int buttonNumber = i; //For Debug Purposes

            codeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CheckButtonPress", "" + buttonNumber);
                lastClicked = buttonNumber;
                ChangeCodeDialog box = new ChangeCodeDialog();
                box.show(getFragmentManager(), "MyDialogFragment");
            }
        });

            codeViews.add(codeView);

            innerLayout.addView(codeView);

            linearLayout.addView(innerLayout);
        }

        proceedButton = view.findViewById(R.id.codes_correct_btn);

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CandidateEnterFragment newFragment = new CandidateEnterFragment();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, newFragment);

                // Commit the transaction
                transaction.commit();

            }
        });

        return view;
    }

    public int getLastClicked() {
        return lastClicked;
    }

    public static void setButtonText(String code) {
        if(lastClicked == null) {
            //do nothing
        }
        else {
            codeViews.get(lastClicked).setText(code);
        }
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //   super.onSaveInstanceState(outState);
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

    public static class ChangeCodeDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final View view = inflater.inflate(R.layout.fragment_change_code_dialog, null);
            builder.setView(view);

            final EditText box1 = view.findViewById(R.id.change1);
            final EditText box2 = view.findViewById(R.id.change2);
            final EditText box3 = view.findViewById(R.id.change3);
            final EditText box4 = view.findViewById(R.id.change4);

            box1.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    box2.requestFocus();
                }
            });

            box2.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    box3.requestFocus();
                }
            });

            box3.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    box4.requestFocus();
                }
            });

            builder.setMessage("Edit Code for Candidate")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String newCode = "" + box1.getText() + box2.getText() + box3.getText() + box4.getText();
                            setButtonText(newCode);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            lastClicked = null;
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

}
