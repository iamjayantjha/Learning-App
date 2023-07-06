package com.learningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class DetailsActivity extends AppCompatActivity {
    TextInputEditText name, productName, price,email, phone;
    Button payBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        name = findViewById(R.id.nameText);
        productName = findViewById(R.id.productNameText);
        price = findViewById(R.id.priceText);
        payBtn = findViewById(R.id.payBtn);
        email = findViewById(R.id.emailText);
        phone = findViewById(R.id.phoneText);
        payBtn.setOnClickListener(v -> {
            String nameStr = name.getText().toString().trim();
            String productNameStr = productName.getText().toString().trim();
            String priceStr = price.getText().toString().trim();
            if (nameStr.isEmpty() || productNameStr.isEmpty() || priceStr.isEmpty() || email.getText().toString().isEmpty() || phone.getText().toString().isEmpty()) {
                name.setError("Please enter your name");
                productName.setError("Please enter product name");
                price.setError("Please enter price");
                email.setError("Please enter email");
                phone.setError("Please enter phone number");
                Toast.makeText(getApplicationContext(), "Please fill all the details", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("name", nameStr);
                intent.putExtra("productName", productNameStr);
                intent.putExtra("price", priceStr);
                intent.putExtra("email", email.getText().toString().trim());
                intent.putExtra("phone", phone.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}