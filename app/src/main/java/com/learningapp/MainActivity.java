package com.learningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
//    TextView text;
//    Button saveBtn;
//   // EditText userInput;
//    TextInputEditText editText;
   // Spinner spinner;

    //WebView webView;
    //VideoView videoView;
    ListView listView;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
       // videoView = findViewById(R.id.videoView);
//        webView = findViewById(R.id.webView);
//        String url = "https://www.google.com";
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl(url);
//        spinner = findViewById(R.id.spinner);
        String[] countries = {"India", "USA", "China", "Japan", "Other"};
        ArrayAdapter<String> adapter = new  ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
          //  Toast.makeText(this, countries[position], Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(view, countries[position], Snackbar.LENGTH_LONG);
            snackbar.setAction("Hi", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                }
            });
            snackbar.show();
        });
//        spinner.setAdapter(adapter);
//        text = findViewById(R.id.text);
//        saveBtn = findViewById(R.id.saveBtn);
//        editText = findViewById(R.id.editText);
        //userInput = findViewById(R.id.userInput);
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // text.setText(userInput.getText());
//                text.setText(editText.getText());
//            }
//        });
//        videoView.setVideoURI(getVideoPath(R.raw.video));
//        //videoView.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=H_bB0sAqLNg"));
//        videoView.setMediaController(new MediaController(this));
//        videoView.start();






    }

    private Uri getVideoPath(int video) {
        return Uri.parse("android.resource://" + getPackageName() + "/" + video);
        //android.resource://com.learningapp/R.raw.video
    }
}