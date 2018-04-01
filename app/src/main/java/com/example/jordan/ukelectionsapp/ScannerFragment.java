package com.example.jordan.ukelectionsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import static com.example.jordan.ukelectionsapp.MainActivity.REQUEST_IMAGE_CAPTURE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScannerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScannerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScannerFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Bitmap myBitmap;
    private TextView txtView;
    private ImageView myImageView;
    private Button btn, checkbtn;
    private ScannedResult scannedResult;

    public ScannerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScannerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScannerFragment newInstance(String param1, String param2) {
        ScannerFragment fragment = new ScannerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        Log.d("emptyView" , "returnung fragment");

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("emptyView" , "Fragment OnCreate");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d("emptyView" , "Fragment OnCreate finished");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("emptyView" , "oncreateView");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        Log.d("emptyView" , "view inflated");


        txtView = (TextView) view.findViewById(R.id.txtContent);
        myImageView = (ImageView) view.findViewById(R.id.imgview);

        btn = (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(this);

        Log.d("emptyView" , "Fields Created");

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

    public void scanForQr() {
        Log.d("QR Result", "Function Called ");

        BarcodeDetector detector =
                new BarcodeDetector.Builder(getActivity().getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
        if(!detector.isOperational()){
            txtView.setText("Could not set up the detector!");
            return;
        }
        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();

        SparseArray<Barcode> barcodes = detector.detect(frame);

        // Check That a QR code was detected and successfully returned by Detector
        if(barcodes.size() == 1) {
            Barcode thisCode = barcodes.valueAt(0);
            txtView.setText(thisCode.rawValue);

            //Create Scanned Results Object, this will be used to input the candidate codes
            scannedResult = ScannedResult.getInstance();
            scannedResult.create(thisCode.rawValue);

            // Create fragment and give it an argument specifying the article it should show
            CheckCodesFragment newFragment = new CheckCodesFragment();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);

// Commit the transaction
            transaction.commit();

        }
        else {
            txtView.setText("No QR Code Detected");
        }

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            myBitmap = (Bitmap) extras.get("data");
            myBitmap = myBitmap.copy(Bitmap.Config.ARGB_8888,true);
            myImageView.setImageBitmap(myBitmap);
            Log.d("QR Result", "Pre Function Call ");
            scanForQr();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button:
                dispatchTakePictureIntent();
                break;
            default:
                Log.d("hello", "ll");
        }
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
