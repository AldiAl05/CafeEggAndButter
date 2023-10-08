package com.example.pemesanancafeeggandbutter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailProduk extends AppCompatActivity {

    TextView detailTitle, detailDesc, detailHarga, quantity;
    ImageView detailImage, detailBack, addItem, removeItem;
    Button addCart;
    String key = "";
    String nama,nohp;
    String imageUrl;

    int totalQuantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        detailTitle = findViewById(R.id.detailTitle);
        detailDesc = findViewById(R.id.detailDesc);
        detailHarga = findViewById(R.id.detailHarga);
        detailImage = findViewById(R.id.detailImage);
        detailBack = findViewById(R.id.imgBackToMenu);

        addItem = findViewById(R.id.tambahquantity);
        removeItem = findViewById(R.id.kurangquantity);
        quantity = findViewById(R.id.quantity);

        addCart = findViewById(R.id.btnaddtocart);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailTitle.setText(bundle.getString("Title"));
            detailDesc.setText(bundle.getString("Description"));
            detailHarga.setText(bundle.getString("Harga"));
            key = bundle.getString("key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);

        }

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = totalQuantity; // Jumlah produk yang dipilih
                System.out.println(FirebaseDatabase.getInstance().getReference("Pesanan"));
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Pesanan");
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()){

                            System.out.println("btnpesan");

                            Intent intent = new Intent(DetailProduk.this, KonfirmasiPesanan.class);
                            intent.putExtra("Title", detailTitle.getText().toString());
                            intent.putExtra("Harga", detailHarga.getText().toString());
                            intent.putExtra("Quantity", quantity);
                            intent.putExtra("username", nama);
                            intent.putExtra("nohp",nohp);
                            intent.putExtra("Key", key);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });

        detailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity > 0) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

    }
}