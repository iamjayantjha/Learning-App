package com.learningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    String text;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = findViewById(R.id.textView);
        text = getIntent().getStringExtra("userText");
        textView.setText(text);
      //  Toast.makeText(getApplicationContext(),"Second Page Started",Toast.LENGTH_SHORT).show();
    }
}