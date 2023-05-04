package com.learningapp;

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
}
