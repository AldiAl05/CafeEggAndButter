package com.example.pemesanancafeeggandbutter.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pemesanancafeeggandbutter.Adapter.pesananAdapter;
import com.example.pemesanancafeeggandbutter.Entitas.Pesanan;
import com.example.pemesanancafeeggandbutter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagePesanan extends AppCompatActivity {
    private ImageView btnBack;
    private RecyclerView rvPesanan;
    private DatabaseReference database;
    private ValueEventListener eventListener;
    private ArrayList<Pesanan> arraylist;
    private pesananAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_pesanan);

        btnBack = findViewById(R.id.imgBackmanageptoadmin);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin.class));
            }
        });

        rvPesanan = findViewById(R.id.rvLPesanan);
        rvPesanan.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ManagePesanan.this, 1);
        rvPesanan.setLayoutManager(gridLayoutManager);

        arraylist = new ArrayList<>();
        adapter = new pesananAdapter(arraylist, this);

        rvPesanan.setAdapter(adapter);
        rvPesanan.setItemAnimator(new DefaultItemAnimator());

        database = FirebaseDatabase.getInstance().getReference("Pesanan");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                arraylist.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (item.child("nama").getValue() == null) {

                    } else {
                        Pesanan pesanan = new Pesanan();
                        pesanan.setNama(item.child("nama").getValue(String.class));
                        pesanan.setProduk(item.child("produk").getValue(String.class));
                        pesanan.setHarga(item.child("harga").getValue(String.class));
                        pesanan.setJumlah(item.child("jumlah").getValue(Long.class));
                        pesanan.setTotalharga(item.child("totalharga").getValue(Long.class));
                        pesanan.setNohp(item.child("nohp").getValue(String.class));
                        pesanan.setAlamat(item.child("alamat").getValue(String.class));

                        arraylist.add(pesanan);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                Log.d("FirebaseData", "Failed to retrieve Pesanan: " + error.getMessage());
            }
        };
        database.addValueEventListener(eventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eventListener != null && database != null) {
            database.removeEventListener(eventListener);
        }
    }
}
