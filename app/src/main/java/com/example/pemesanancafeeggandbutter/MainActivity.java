package com.example.pemesanancafeeggandbutter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        EditText etCode = findViewById(R.id.tvQrcode);
        Button btnCode = findViewById(R.id.btnQrCode);


    }
}