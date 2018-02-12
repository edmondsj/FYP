package com.example.jordan.ukelectionsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText niNumberEditText;
    private TextView niTextView;

    private EditText passText;
    private TextView passTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        /* Initializing views */
        niNumberEditText = (EditText) findViewById(R.id.ni_field);
        niTextView = (TextView) findViewById(R.id.show_ni);
        niTextView.setVisibility(View.GONE);

        /* Set Text Watcher listener */
        niNumberEditText.addTextChangedListener(passwordWatcher);

    }

    private final TextWatcher passwordWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            niTextView.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                niTextView.setVisibility(View.GONE);
            } else{
                niTextView.setText("You have entered : " + niNumberEditText.getText());
            }
        }
    };
}
