package com.example.pemesanancafeeggandbutter.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pemesanancafeeggandbutter.Entitas.Pesanan;
import com.example.pemesanancafeeggandbutter.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

public class pesananAdapter extends RecyclerView.Adapter<pesananAdapter.MyViewHolder> {
    List<Pesanan> dataList;

    Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Pesanan");

    public pesananAdapter(List<Pesanan> list, Context context) {
        Collections.reverse(list);
        dataList = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pesanan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Pesanan item = dataList.get(position);
        if (item.getNama() == null){
            holder.itemView.setVisibility(View.GONE);
        }else {
            holder.nama.setText(item.getNama());
            holder.noHp.setText("No HP : " + item.getNohp());
            holder.alamat.setText("Alamat : " + item.getAlamat());
            holder.produk.setText("Produk : " + item.getProduk());
            holder.harga.setText("Harga : Rp " + item.getHarga());
            holder.jumlah.setText("Jumlah Pesanan : " + String.valueOf(item.getJumlah()));
            holder.totalHarga.setText(String.valueOf("Total Harga : Rp " + item.getTotalharga()));
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    database.child(item.getNama()).setValue(null);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, produk, harga, jumlah, totalHarga, alamat, noHp;
        Button btnDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvPnama);
            produk = itemView.findViewById(R.id.tvPproduk);
            harga = itemView.findViewById(R.id.tvPharga);
            jumlah = itemView.findViewById(R.id.tvPjumlah);
            totalHarga = itemView.findViewById(R.id.tvPtotal);
            noHp = itemView.findViewById(R.id.tvPNoHp);
            alamat = itemView.findViewById(R.id.tvPalamat);

            btnDelete = itemView.findViewById(R.id.btnHapusproduk);
        }
    }
}
