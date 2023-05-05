package com.learningapp;

import android.widget.ArrayAdapter;

public class Comment {
    //listView = findViewById(R.id.list);
    //  searchView = findViewById(R.id.searchView);
    // videoView = findViewById(R.id.videoView);
//        webView = findViewById(R.id.webView);
//        String url = "https://www.google.com";
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl(url);
//        spinner = findViewById(R.id.spinner);
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
    //Toast.makeText(getApplicationContext(),"Service Started",Toast.LENGTH_SHORT).show();
           /* Intent service = new Intent(MainActivity.this, MyService.class);
            startService(service);*/
    /*userInput.setInputType(InputType.TYPE_CLASS_NUMBER);*/
            /*String id = userInput.getText().toString().trim();
            String name = userName.getText().toString().trim();
            String phone_number = "9871982780";
            boolean result = db.updateData(id,name,phone_number);
            if (result){
                Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"Data Insertion Failed",Toast.LENGTH_SHORT).show();
            }*/
     /*Cursor res = db.readData(id);
            String name = "";
            String phoneNumber = "";
            //StringBuffer stringBuffer = new StringBuffer();
            if (res!=null && res.getCount()>0){
                while (res.moveToNext()){
                  //stringBuffer.append("Id",res.getString(0)+"\n") ;
                    //id = id + res.getString(0)+"\n";
                    name = res.getString(1);
                    phoneNumber = res.getString(2);
                }
            }
            text.setText(id+"\n"+name+"\n"+phoneNumber);*/

    //Create
    //Read
    //Update
    //Delete
/*

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
 /*   private static final int[] IMAGES ={R.drawable.send_ic,R.drawable.person_ic,R.mipmap.ic_launcher};
    int position = -1;
    DataBaseHelper db;
    image = findViewById(R.id.image);
    changeImg = findViewById(R.id.changeImg);
    userInput = findViewById(R.id.userInput);
    private Uri getVideoPath(int video) {
        return Uri.parse("android.resource://" + getPackageName() + "/" + video);
        //android.resource://com.learningapp/R.raw.video
    }
    db = new DataBaseHelper(this);
    //SearchView searchView;

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
        });*/

    /*
      <ImageSwitcher
        android:id="@+id/image"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="25dp"
        android:src="@mipmap/ic_launcher"/>

    <Button
        android:id="@+id/changeImg"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:text="Next"
        android:textSize="15sp"/>

   <EditText
    android:id="@+id/userInput"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="20dp"
    android:inputType="number"
    android:layout_marginStart="20dp"
    android:layout_marginEnd="20dp"/>

    <EditText
        android:id="@+id/userName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:layout_below="@id/userInput"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="20dp"/>

    <TextView
        android:id="@+id/text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="10dp"
        android:textStyle="bold"
        android:textColor="@color/black"
        android:layout_below="@id/changeImg"
        android:textSize="20sp"/>

    * */
    /* changeImg.setOnClickListener(v -> {
     *//* SharedPreferences preferences = this.getSharedPreferences("PREFS",Context.MODE_PRIVATE);
     *//**//*SharedPreferences.Editor editor = preferences.edit();
            editor.putString("name","Akshat");
            editor.apply();*//**//*
            preferences.edit().remove("name").apply();
            Intent second = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(second);*//*
        });*/

    /*String id = userInput.getText().toString().trim();
            boolean result = db.deleteData(id);
            Toast.makeText(getApplicationContext(),"Data Deleted", Toast.LENGTH_SHORT).show();*/

    /*

    XML code
    <!-- <Button
        android:id="@+id/saveBtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:text="Login"
        android:textSize="15sp"
        style="@style/Widget.MaterialComponents.Button.TextButton"/>

    &lt;!&ndash;<EditText
        android:id="@+id/userInput"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@+id/text"
        android:layout_marginTop="20dp"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="20dp"/>&ndash;&gt;

    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/textField"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Email"
        android:layout_below="@id/image"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="20dp"
        android:layout_marginTop="20dp"
        style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/editText"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            />

    </com.google.android.material.textfield.TextInputLayout>

    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/textField2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Password"
        android:layout_below="@id/textField"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="20dp"
        android:layout_marginTop="20dp"
        style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/editText2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            />

    </com.google.android.material.textfield.TextInputLayout>

    <ImageView
        android:id="@+id/image"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/text"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="25dp"
        android:src="@mipmap/ic_launcher"/>

    <TextView
        android:id="@+id/text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Login"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="10dp"
        android:textStyle="bold"
        android:textColor="@color/black"
        android:textSize="20sp"/>-->


<!--    <Spinner-->
<!--        android:id="@+id/spinner"-->
<!--        android:layout_centerInParent="true"-->
<!--        android:layout_width="wrap_content"-->
<!--        android:layout_height="wrap_content"/>-->

    <!--<WebView
        android:id="@+id/webView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>-->

<!--    <VideoView-->
<!--        android:id="@+id/videoView"-->
<!--        android:layout_width="wrap_content"-->
<!--        android:layout_height="wrap_content"/>-->

<!--    <SearchView
        android:id="@+id/searchView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        android:iconifiedByDefault="false"/>

    <ListView
        android:id="@+id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/searchView"/>-->
    * */
}
