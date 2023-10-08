package com.example.pemesanancafeeggandbutter.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pemesanancafeeggandbutter.Entitas.Pesanan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.MyViewHolder> {
    List<Pesanan> dataList;
    Context context;

    public historyAdapter(List<Pesanan> list, Context context) {
        Collections.reverse(list);
        dataList = list;
        this.context = context;
    }

    @Override
    public historyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(historyAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
