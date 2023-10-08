package com.example.pemesanancafeeggandbutter.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pemesanancafeeggandbutter.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditDataUser extends AppCompatActivity {
    private EditText etEdUsername,etEdNama, etEdEmail, etEdPhone, etEdPassword;
    private Button btnEdSave, btnEdBatal;

    private String username, fullname, email, phone, password;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_user);

        etEdUsername = findViewById(R.id.etEditUsername);
        etEdNama = findViewById(R.id.etEditFullName);
        etEdEmail= findViewById(R.id.etEditEmail);
        etEdPhone = findViewById(R.id.etEditPhone);
        etEdPassword = findViewById(R.id.etEditPassword);

        btnEdSave = findViewById(R.id.btnEditUserSave);
        btnEdBatal = findViewById(R.id.btnEditUserBatal);



        if (getIntent().hasExtra("username")&& getIntent().hasExtra("nama lengkap")&& getIntent().hasExtra("email")&& getIntent().hasExtra("nohp")&& getIntent().hasExtra("password")){
            username = getIntent().getStringExtra("username");
            fullname = getIntent().getStringExtra("nama lengkap");
            email = getIntent().getStringExtra("email");
            phone = getIntent().getStringExtra("nohp");
            password = getIntent().getStringExtra("password");
        }

        etEdUsername.setText(username);
        etEdNama.setText(fullname);
        etEdEmail.setText(email);
        etEdPhone.setText(phone);
        etEdPassword.setText(password);

        btnEdSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String namaBaru = etEdNama.getText().toString();
                String emailBaru = etEdEmail.getText().toString();
                String phoneBaru = etEdPhone.getText().toString();
                String passwordBaru = etEdPassword.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("nama lengkap",namaBaru);
                hashMap.put("email",emailBaru);
                hashMap.put("phone",phoneBaru);
                hashMap.put("password",passwordBaru);

                database.child(username).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(getApplicationContext(),"update berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ManageUser.class));
                    }
                });


            }
        });
        btnEdBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ManageUser.class));
            }
        });
    }
}