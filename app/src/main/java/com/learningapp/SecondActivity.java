package com.learningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    String name;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = findViewById(R.id.textView);
       // text = getIntent().getStringExtra("userText");
        SharedPreferences preferences = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        name = preferences.getString("name","Null");
        textView.setText(name);
      //  Toast.makeText(getApplicationContext(),"Second Page Started",Toast.LENGTH_SHORT).show();
    }
}