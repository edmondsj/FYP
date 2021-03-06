package com.example.jordan.ukelectionsapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jordan.ukelectionsapp.R;
import com.example.jordan.ukelectionsapp.ScannedResult;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CandidateEnterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CandidateEnterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CandidateEnterFragment extends Fragment {

    private EditText box1,box2,box3,box4;
    private Button enterCodeBtn;

    private OnFragmentInteractionListener mListener;

    public CandidateEnterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CandidateEnterFragment.
     */
    public static CandidateEnterFragment newInstance(String param1, String param2) {
        CandidateEnterFragment fragment = new CandidateEnterFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_candidate_enter, container, false);

        box1 = view.findViewById(R.id.can1);
        box2 = view.findViewById(R.id.can2);
        box3 = view.findViewById(R.id.can3);
        box4 = view.findViewById(R.id.can4);


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

        enterCodeBtn = view.findViewById(R.id.chooseBTN);
        enterCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] code = new int[4];
                code[0] = Integer.parseInt("" + box1.getText());
                code[1] = Integer.parseInt("" + box2.getText());
                code[2] = Integer.parseInt("" + box3.getText());
                code[3] = Integer.parseInt("" + box4.getText());

                ScannedResult.getInstance().setSelectedCode(code);

                DisplayFinalCodeFragment newFragment = new DisplayFinalCodeFragment();
                Bundle args = new Bundle();
                newFragment.setArguments(args);

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
