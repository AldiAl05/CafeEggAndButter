package com.example.pemesanancafeeggandbutter.LoginDanReqister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pemesanancafeeggandbutter.Entitas.User;
import com.example.pemesanancafeeggandbutter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    private EditText etUsername,etFullname, etEmail, etPhone, etPassword, etValpassword;
    private Button btnRegister;
    private TextView btnLogin;

//    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etRegUsername);
        etFullname = findViewById(R.id.etRegFullName);
        etEmail = findViewById(R.id.etRegEmail);
        etPhone = findViewById(R.id.etRegPhone);
        etPassword = findViewById(R.id.etRegPassword);
        etValpassword = findViewById(R.id.etValPassword);

        btnRegister = findViewById(R.id.btnReg);
        btnLogin = findViewById(R.id.tvRegistToLogin);

        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cafeeggandbutter-default-rtdb.asia-southeast1.firebasedatabase.app/");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String fullname = etFullname.getText().toString();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString().trim();
                String valpassword = etValpassword.getText().toString();



                //konfirmasi from
                if (username.isEmpty()||fullname.isEmpty()||email.isEmpty()||phone.isEmpty()||password.isEmpty()||valpassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong!!", Toast.LENGTH_SHORT).show();

                } else if (!password.equals(valpassword)) {
                    Toast.makeText(getApplicationContext(), "Password tidak sesuai", Toast.LENGTH_SHORT).show();

                } else {

                    //validasi data
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)){
                                Toast.makeText(getApplicationContext(), "Username telah digunakan", Toast.LENGTH_SHORT).show();

                            }else{
                                database = FirebaseDatabase.getInstance().getReference("users");
                                //send data
                                database.child(username).child("username").setValue(username);
                                database.child(username).child("nama lengkap").setValue(fullname);
                                database.child(username).child("email").setValue(email);
                                database.child(username).child("nohp").setValue(phone);
                                database.child(username).child("password").setValue(password);

                                Toast.makeText(Register.this,"Register Berhasil", Toast.LENGTH_SHORT).show();
                                finish();

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Login = new Intent(Register.this, Login.class);
                startActivity(Login);

            }
        });

    }
}