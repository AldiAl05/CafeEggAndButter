package com.example.pemesanancafeeggandbutter.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.pemesanancafeeggandbutter.Adapter.UserAdapter;
import com.example.pemesanancafeeggandbutter.Entitas.User;
import com.example.pemesanancafeeggandbutter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageUser extends AppCompatActivity {
    private ImageView btnBack;
    private RecyclerView rvUsers;

    private DatabaseReference database;
    private UserAdapter useradapter;
    private ArrayList<User> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        btnBack = findViewById(R.id.imgBackmanagetoadmin);
        rvUsers = findViewById(R.id.rvMusers);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Admin.class));
            }
        });

        database = FirebaseDatabase.getInstance().getReference("users");
        rvUsers.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        rvUsers.setLayoutManager(layoutManager);
        rvUsers.setItemAnimator(new DefaultItemAnimator());

        tampilDataUser();
    }
    //memanggil perulangan data yang ada di dbs user
    private void tampilDataUser() {

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                arraylist = new ArrayList<>();

                for(DataSnapshot item : snapshot.getChildren()){

                    User user = new User();
                    user.setUsername(item.child("username").getValue(String.class));
                    user.setFullname(item.child("nama lengkap").getValue(String.class));
                    user.setEmail(item.child("email").getValue(String.class));
                    user.setNohp(item.child("nohp").getValue(String.class));
                    user.setPassword(item.child("password").getValue(String.class));
                    arraylist.add(user);

                }
                useradapter = new UserAdapter(arraylist, ManageUser.this);
                rvUsers.setAdapter(useradapter);
                useradapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}