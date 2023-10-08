package com.example.pemesanancafeeggandbutter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pemesanancafeeggandbutter.Admin.DetailEditProduk;
import com.example.pemesanancafeeggandbutter.Entitas.DataClass;
import com.example.pemesanancafeeggandbutter.R;

import java.util.List;

public class produkAdapter extends RecyclerView.Adapter<produkAdapter.MyViewHolder> {
    List<DataClass> dataList;
    Context context;

    public produkAdapter(List<DataClass> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public produkAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(produkAdapter.MyViewHolder holder, int position) {
        final DataClass item = dataList.get(position);
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recProduk);
        holder.recTitle.setText(dataList.get(position).getDataTitle());
        holder.recDesc.setText(dataList.get(position).getDataDesc());
        holder.recHarga.setText(dataList.get(position).getDataHarga());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailEditProduk.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Harga", dataList.get(holder.getAdapterPosition()).getDataHarga());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recProduk;
        TextView recTitle, recDesc, recHarga;
        CardView recCard;

        public MyViewHolder(View itemView) {
            super(itemView);

            recProduk = itemView.findViewById(R.id.listImage);
            recTitle = itemView.findViewById(R.id.listTitle);
            recDesc = itemView.findViewById(R.id.listDesc);
            recHarga = itemView.findViewById(R.id.listHarga);
            recCard = itemView.findViewById(R.id.recCard);
        }
    }
}

