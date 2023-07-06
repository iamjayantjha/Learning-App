package com.learningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.learningapp.Adapter.ChatResponseAdapter;
import com.learningapp.Modal.Message;
import com.razorpay.Checkout;
import com.razorpay.PayloadHelper;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SecondActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText message;
    TextView sendBtn,title;
    List<Message> messageList;
    ChatResponseAdapter chatResponseAdapter;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Checkout.preload(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        message = findViewById(R.id.message);
        sendBtn = findViewById(R.id.sendBtn);
        title = findViewById(R.id.title);
        messageList = new ArrayList<>();
        chatResponseAdapter = new ChatResponseAdapter(messageList);
        recyclerView.setAdapter(chatResponseAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        message.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled=false;
            if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                String msg = message.getText().toString();
                if (msg.isEmpty()) {
                    Toast.makeText(SecondActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                } else {
                    if (title.getVisibility() == View.VISIBLE) {
                        title.setVisibility(View.GONE);
                    }
                    message.setText("");
                    messageList.add(new Message(msg, Message.SENT_BY_USER));
                    chatResponseAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(chatResponseAdapter.getItemCount());
                    getResponse(msg);
                }
                handled=true;
            }
            return handled;
        });
        sendBtn.setOnClickListener(v -> {
            String msg = message.getText().toString();
            if (msg.isEmpty()) {
                Toast.makeText(SecondActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
            } else {
                if (title.getVisibility() == View.VISIBLE) {
                    title.setVisibility(View.GONE);
                }
                message.setText("");
                messageList.add(new Message(msg, Message.SENT_BY_USER));
                chatResponseAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(chatResponseAdapter.getItemCount());
                getResponse(msg);
            }
        });
    }

    private void getResponse(String msg) {
        messageList.add(new Message("Thinking...", Message.SENT_BY_BOT));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model","gpt-3.5-turbo");
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("role","user");
            jsonObject1.put("content",msg);
            jsonArray.put(jsonObject1);
            jsonObject.put("messages",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonObject.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Authorization", "Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> {
                    messageList.remove(messageList.size()-1);
                    messageList.add(new Message("Sorry what did you just said? "+e.getMessage(), Message.SENT_BY_BOT));
                    chatResponseAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(chatResponseAdapter.getItemCount());
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String res = Objects.requireNonNull(response.body()).string();
                    Log.d("TAG", "onResponse: "+res);
                    try {
                        JSONObject jsonObject1 = new JSONObject(res);
                        String text = jsonObject1.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                        runOnUiThread(() -> {
                            messageList.remove(messageList.size()-1);
                            messageList.add(new Message(text.trim(), Message.SENT_BY_BOT));
                            chatResponseAdapter.notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(chatResponseAdapter.getItemCount());
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    runOnUiThread(() -> {
                        messageList.remove(messageList.size()-1);
                        messageList.add(new Message("Sorry what did you just said? "+response.message(), Message.SENT_BY_BOT));
                        chatResponseAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(chatResponseAdapter.getItemCount());
                    });
                }
            }
        });
    }
}