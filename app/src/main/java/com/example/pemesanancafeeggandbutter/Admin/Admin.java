package com.example.pemesanancafeeggandbutter.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pemesanancafeeggandbutter.LoginDanReqister.Login;
import com.example.pemesanancafeeggandbutter.R;

public class Admin extends AppCompatActivity {
    private CardView btnManageUser, btnManagePesanan, btnManageProduk, btnAdminKeluar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnManageUser = findViewById(R.id.cvManageUser);
        btnManageProduk = findViewById(R.id.cvManangeProduk);
        btnAdminKeluar = findViewById(R.id.cvManangeLogout);
        btnManagePesanan = findViewById(R.id.cvManagePesanan);

        btnManageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manageusers = new Intent(Admin.this, ManageUser.class);
                startActivity(manageusers);
            }
        });

        btnManageProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manageproduk = new Intent(Admin.this, ManageProduk.class);
                startActivity(manageproduk);
            }
        });

       btnManagePesanan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent managepesanan = new Intent(Admin.this, ManagePesanan.class);
               startActivity(managepesanan);
           }
       });

        btnAdminKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}