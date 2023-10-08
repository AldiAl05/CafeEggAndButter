package com.example.pemesanancafeeggandbutter.LoginDanReqister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pemesanancafeeggandbutter.Admin.Admin;
import com.example.pemesanancafeeggandbutter.MainMenuActivity;
import com.example.pemesanancafeeggandbutter.R;
import com.example.pemesanancafeeggandbutter.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    private TextView btnLoginToRegister;
    private EditText etUsername, etPassword;
    private Button btnMasuk;
    private DatabaseReference database;
    private Session session;
    String namaLengkap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnMasuk = findViewById(R.id.btnLoginAkun);
        etUsername= findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLoginToRegister = findViewById(R.id.btnRegisterAccount);


        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = etUsername.getText().toString();
                String Password = etPassword.getText().toString();

                database = FirebaseDatabase.getInstance().getReference("users");

                if (Username.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Harap isi email dan password akun anda", Toast.LENGTH_SHORT).show();

                    //login admin
                }else if (Username.equals("admin")&& Password.equals("admin")) {
                    Toast.makeText(getApplicationContext(), "Login Admin", Toast.LENGTH_SHORT).show();
                    Intent Login = new Intent(getApplicationContext(), Admin.class);
                    startActivity(Login);

                }else {
                    //login user
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.child(Username).exists()) {
                                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                    HashMap<String, Object> dataMap = childSnapshot.getValue(new GenericTypeIndicator<HashMap<String, Object>>() {});
                                    namaLengkap = (String) dataMap.get("nama lengkap");
                                    System.out.println("nama Lengkap"+ namaLengkap);
                                }
                                if (snapshot.child(Username).child("password").getValue(String.class).equals(Password)) {
                                    Toast.makeText(getApplicationContext(), "login Berhasil", Toast.LENGTH_SHORT).show();
                                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("username", Username);
                                    editor.putString("namaLengkap",namaLengkap);
                                    editor.apply();
                                    Intent Login = new Intent(getApplicationContext(), MainMenuActivity.class);
                                    startActivity(Login);
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Data Belum Terdaftar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }
           }
        });
        //move activity
        btnLoginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("users","button working properly");
                Intent register = new Intent(Login.this, Register.class);
                startActivity(register);
            }
        });
    }
}
