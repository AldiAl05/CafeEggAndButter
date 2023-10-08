package com.example.pemesanancafeeggandbutter.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.pemesanancafeeggandbutter.Adapter.itemAdapter;
import com.example.pemesanancafeeggandbutter.Adapter.produkAdapter;
import com.example.pemesanancafeeggandbutter.Entitas.DataClass;
import com.example.pemesanancafeeggandbutter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ManageProduk extends AppCompatActivity {

    ImageView btnBack,btnAddProduk;
    RecyclerView rvAProduk;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    List<DataClass> dataList;
    produkAdapter adapter;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_produk);

        btnBack = findViewById(R.id.imgBackmanagetoadmin);
        rvAProduk = findViewById(R.id.rvMProduk);
        btnAddProduk = findViewById(R.id.btnaddProduk);


        rvAProduk.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ManageProduk.this, 1);
        rvAProduk.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();
        adapter = new produkAdapter(dataList,this);
        rvAProduk.setAdapter(adapter);

        rvAProduk.setItemAnimator(new DefaultItemAnimator());

        databaseReference = FirebaseDatabase.getInstance().getReference("Produk");
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        btnAddProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddDataProduk.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin.class));
            }
        });

    }
}



