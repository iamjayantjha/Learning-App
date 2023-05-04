package com.learningapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
//    TextView text;
    Button changeImg;
     EditText userInput,userName;
//    TextInputEditText editText;
   // Spinner spinner;

    //WebView webView;
    //VideoView videoView;
    //ListView listView;;
    TextView text;
    ImageSwitcher image;
    private static final int[] IMAGES ={R.drawable.send_ic,R.drawable.person_ic,R.mipmap.ic_launcher};
    int position = -1;
    DataBaseHelper db;


    //SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.image);
        changeImg = findViewById(R.id.changeImg);
        userInput = findViewById(R.id.userInput);
        db = new DataBaseHelper(this);
        userName = findViewById(R.id.userName);
        text = findViewById(R.id.text);
        String[] countries = {"India", "USA", "China", "Japan", "Other"};
        ArrayAdapter<String> adapter = new  ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);

        image.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView image2 = new ImageView(getApplicationContext());
                image2.setScaleType(ImageView.ScaleType.FIT_CENTER);
                image2.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                return image2;
            }
        });
        changeImg.setOnClickListener(v -> {
            String id = userInput.getText().toString().trim();
            boolean result = db.deleteData(id);
            Toast.makeText(getApplicationContext(),"Data Deleted", Toast.LENGTH_SHORT).show();
        });




    }

    private Uri getVideoPath(int video) {
        return Uri.parse("android.resource://" + getPackageName() + "/" + video);
        //android.resource://com.learningapp/R.raw.video
    }
}