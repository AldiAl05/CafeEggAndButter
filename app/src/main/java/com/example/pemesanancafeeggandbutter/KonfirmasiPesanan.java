package com.example.pemesanancafeeggandbutter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pemesanancafeeggandbutter.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class KonfirmasiPesanan extends AppCompatActivity {
    EditText inputAlamat, konNama, konHp;
    TextView konProduk, konHarga, konJumlah, konTotal, konNamaPesanans ;
    Button btnkirimpesanan, btnbatalpesan;
    DatabaseReference database;
    ValueEventListener eventListener;
    Session session;

    String title, harga, key, konNamaPesanan, username;
    int jumlah, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pesanan);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = preferences.getString("username", null);
        String namaLengkap = preferences.getString("namaLengkap",null);
        System.out.println("sessions"+username);

        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cafeeggandbutter-default-rtdb.asia-southeast1.firebasedatabase.app/");

        inputAlamat = findViewById(R.id.inputAlamat);
        konNama = findViewById(R.id.konNamaLengkap);
        konNamaPesanans = findViewById(R.id.konNamaPemesan);
        konHp = findViewById(R.id.konnohp);
        konProduk = findViewById(R.id.konfirmasiTitle);
        konHarga = findViewById(R.id.konfirmasiHarga);
        konJumlah = findViewById(R.id.konjumlah);
        konTotal = findViewById(R.id.konfirmasiTotalHarga);
        btnbatalpesan = findViewById(R.id.batalpesan);
        btnkirimpesanan = findViewById(R.id.KonfirmasiPesanan);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            konNamaPesanan = bundle.getString("username");
            title = bundle.getString("Title");
            harga = bundle.getString("Harga");
            jumlah = bundle.getInt("Quantity");
            quantity = bundle.getInt("Quantity");
            key = bundle.getString("key");
        }
        konNamaPesanans.setText(namaLengkap);
        konProduk.setText(title);
        konHarga.setText(harga);
        konJumlah.setText(String.valueOf(jumlah));
        konTotal.setText(String.valueOf(quantity));
        int totalHargaProduk = Integer.parseInt(harga) * jumlah;
        konTotal.setText(String.valueOf(totalHargaProduk));


        DatabaseReference pesananRef = FirebaseDatabase.getInstance().getReference("Pesanan").child(konNama.getText().toString());
        pesananRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long totalharga = dataSnapshot.child("totalharga").getValue(Long.class);
                    if (totalharga != null) {
                        String totalhargaStr = String.valueOf(totalharga);
                        konTotal.setText(totalhargaStr); // Tampilkan total harga
                    } else {
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnkirimpesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = konNama.getText().toString();
                String alamat = inputAlamat.getText().toString();
                String nohp = konHp.getText().toString();

                database = FirebaseDatabase.getInstance().getReference("Pesanan").child(nama);
                HashMap<String, Object> pesananMap = new HashMap<>();
                pesananMap.put("nama", namaLengkap);
                pesananMap.put("nohp", nohp);
                pesananMap.put("alamat", alamat);
                pesananMap.put("produk", title);
                pesananMap.put("harga", harga);
                pesananMap.put("jumlah", jumlah);
                pesananMap.put("quantity", quantity);

                int totalharga = Integer.parseInt(harga) * quantity; //menghitung total harga
                pesananMap.put("totalharga", totalharga);



                if (nama.isEmpty() || nohp.isEmpty() || alamat.isEmpty()){
                    Toast.makeText(KonfirmasiPesanan.this, "Harap isi data yang kosong!", Toast.LENGTH_SHORT).show();
                }else{
                    database.setValue(pesananMap);
                    Toast.makeText(KonfirmasiPesanan.this, "Pesanan berhasil dikirim!", Toast.LENGTH_LONG).show();
                    finish();

                }

            }
        });

        btnbatalpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}