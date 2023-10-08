package com.example.pemesanancafeeggandbutter.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pemesanancafeeggandbutter.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailEditProduk extends AppCompatActivity {
    TextView detailDescp, detailTitlep, detailHargap;
    ImageView detailImagep,btnbacktomanageedit;
    FloatingActionButton deleteButtonp, editButtonp;
    String key = "";
    String imageUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_edit_produk);

        detailDescp = findViewById(R.id.detailDescp);
        detailImagep = findViewById(R.id.detailImagep);
        detailTitlep = findViewById(R.id.detailTitlep);
        deleteButtonp = findViewById(R.id.deleteButton);
        editButtonp = findViewById(R.id.editButton);
        detailHargap = findViewById(R.id.detailHargap);
        btnbacktomanageedit = findViewById(R.id.imgBackTomanagemenu);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailDescp.setText(bundle.getString("Description"));
            detailTitlep.setText(bundle.getString("Title"));
            detailHargap.setText(bundle.getString("Harga"));
            key = bundle.getString("Key");
            System.out.println("delete"+key+"Title"+detailTitlep);
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImagep);
        }

        deleteButtonp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Produk");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        key = bundle.getString("Key");
                        reference.child(key).removeValue();
                        Toast.makeText(DetailEditProduk.this, "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ManageProduk.class));
                        finish();
                    }
                });
            }
        });

        editButtonp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailEditProduk.this, EditProduk.class)
                        .putExtra("Title", detailTitlep.getText().toString())
                        .putExtra("Description", detailDescp.getText().toString())
                        .putExtra("Harga", detailHargap.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });

        btnbacktomanageedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}