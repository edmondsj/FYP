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

public class MainActivity extends AppCompatActivity implements FlipFragment.OnFragmentInteractionListener {


    private TextView txtView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView myImageView;
    private Bitmap myBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_scanner);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        myImageView = (ImageView) findViewById(R.id.imgview);
        myBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.puppy);
        myImageView.setImageBitmap(myBitmap);
//
//        BarcodeDetector detector =
//                new BarcodeDetector.Builder(getApplicationContext())
//                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
//                        .build();
//        if(!detector.isOperational()){
//            txtView.setText("Could not set up the detector!");
//            return;
//        }
//
///*        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
//        SparseArray<Barcode> barcodes = detector.detect(frame);
//
//        Barcode thisCode = barcodes.valueAt(0);
//        TextView txtView = (TextView) findViewById(R.id.txtContent);
//        txtView.setText(thisCode.rawValue);*/

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            myBitmap = (Bitmap) extras.get("data");
            myBitmap = myBitmap.copy(Bitmap.Config.ARGB_8888,true);
            myImageView.setImageBitmap(myBitmap);
            Log.d("QR Result", "Pre Function Call ");

            scanForQr();
        }
    }

    public void scanForQr() {
        Log.d("QR Result", "Function Called ");

        BarcodeDetector detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
        if(!detector.isOperational()){
            txtView.setText("Could not set up the detector!");
            return;
        }
        Log.d("QR Result", "Detector Created ");
        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        Log.d("QR Result", "Frame Created ");


        SparseArray<Barcode> barcodes = detector.detect(frame);
        Log.d("QR Result", String.valueOf(barcodes.size()));

        if(barcodes.size() == 1) {
            //TODO: Error Seems to be here

            Barcode thisCode = barcodes.valueAt(0);

            Log.d("QR Result", "Barcode local variable created ");

            TextView txtView = (TextView) findViewById(R.id.txtContent);

            Log.d("QR Result", "Textview created ");

            txtView.setText(thisCode.rawValue);
            Log.d("QR Result", "TextView Assigned ");
        }
        else {
            //Code Here
        }



    }

    @Override
    public void onFragmentInteraction(Uri uri) { }
}
