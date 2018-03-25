package com.example.jordan.ukelectionsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements ScannerFragment.OnFragmentInteractionListener {


    private TextView txtView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView myImageView;
    private ScannedResult scannedResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("emptyView" , "activity on create");

        setContentView(R.layout.content_main);
        Log.d("emptyView" , "content view made");

        // Create a new Fragment to be placed in the activity layout
        ScannerFragment firstFragment = new ScannerFragment();
        Log.d("emptyView" , "First Fragment (Scanner Fragemt) made");


        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments

        firstFragment.setArguments(getIntent().getExtras());
        Log.d("emptyView" , "set arguments");


        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        Log.d("emptyView" , "commited");

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode,resultCode,data);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
