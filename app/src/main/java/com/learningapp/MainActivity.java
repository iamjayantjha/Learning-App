package com.learningapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
     EditText userInput;
//    TextInputEditText editText;
   // Spinner spinner;

    //WebView webView;
    //VideoView videoView;
    //ListView listView;;
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
        //listView = findViewById(R.id.list);
      //  searchView = findViewById(R.id.searchView);
       // videoView = findViewById(R.id.videoView);
//        webView = findViewById(R.id.webView);
//        String url = "https://www.google.com";
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl(url);
//        spinner = findViewById(R.id.spinner);
        String[] countries = {"India", "USA", "China", "Japan", "Other"};
        ArrayAdapter<String> adapter = new  ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);
       /* listView.setAdapter(adapter);
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                //Toast.makeText(getApplicationContext(), queryText, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_SHORT).show();
                adapter.getFilter().filter(newText);
                return false;
            }
        });*/
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
        image.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView image2 = new ImageView(getApplicationContext());
                image2.setScaleType(ImageView.ScaleType.FIT_CENTER);
                image2.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                return image2;
            }
        });

        /*  if (position<IMAGES.length-1){
                    ++position;
                    image.setBackgroundResource(IMAGES[position]);
                }
                if (position==IMAGES.length-1){
                    position=0;
                }*/
                /*String text = userInput.getText().toString().trim();
                Intent second = new Intent(MainActivity.this,SecondActivity.class);
                second.putExtra("userText",text);
                startActivity(second);*/
                /*AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle("Learning App");
                dialog.setMessage("How was the class today");
                dialog.setPositiveButton("Good", (dialog1, which) -> {
                    Toast.makeText(getApplicationContext(), "Good", Toast.LENGTH_SHORT).show();
                    dialog1.dismiss();
                });
                dialog.setNegativeButton("Bad", (dialog12, which) -> {
                    Toast.makeText(getApplicationContext(), "Bad", Toast.LENGTH_SHORT).show();
                    dialog12.dismiss();
                });
                dialog.create().show();*/
                /*NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this,"MyChannel");
                notification.setSmallIcon(R.mipmap.ic_launcher);
                notification.setContentTitle("Learning App");
                notification.setContentText("Welcome to Learning App");
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1,notification.build());*/
        //MainActivity.this.finish();

        changeImg.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(),"Service Started",Toast.LENGTH_SHORT).show();
           /* Intent service = new Intent(MainActivity.this, MyService.class);
            startService(service);*/
            String id = "1";
            String name = "Jayant";
            String phone_number = "9871982780";
            boolean result = db.insertData(id,name,phone_number);
            if (result){
                Toast.makeText(getApplicationContext(),"Data Inserted Successfully",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"Data Insertion Failed",Toast.LENGTH_SHORT).show();
            }
        });




    }

    private Uri getVideoPath(int video) {
        return Uri.parse("android.resource://" + getPackageName() + "/" + video);
        //android.resource://com.learningapp/R.raw.video
    }
}