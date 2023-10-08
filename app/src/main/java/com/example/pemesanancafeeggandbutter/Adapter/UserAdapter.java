package com.example.pemesanancafeeggandbutter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pemesanancafeeggandbutter.Admin.EditDataUser;
import com.example.pemesanancafeeggandbutter.Entitas.User;
import com.example.pemesanancafeeggandbutter.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    //save data dari entitas
    List<User> eList;
    Context context;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");

    public UserAdapter(List<User> eList, Context context){
        this.eList = eList;
        this.context = context;
    }
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.MyViewHolder holder, int position) {
        final User item = eList.get(position);
        holder.tvUsername.setText("username : "+item.getUsername());
        holder.tvNama.setText("Nama  : "+item.getFullname());
        holder.tvEmail.setText("Email : "+item.getEmail());
        holder.tvNohp.setText("No Hp : "+item.getNohp());
        holder.tvPassword.setText("Password : "+item.getPassword());

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child(item.getUsername()).setValue(null);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = item.getUsername();
                String fullname = item.getFullname();
                String email = item.getEmail();
                String nohp = item.getNohp();
                String password = item.getPassword();

                Intent edituser = new Intent(context, EditDataUser.class);
                //nambahin data
                edituser.putExtra("username",username);
                edituser.putExtra("nama lengkap",fullname);
                edituser.putExtra("email",email);
                edituser.putExtra("nohp",nohp);
                edituser.putExtra("password",password);


                context.startActivity(edituser);


            }
        });
    }

    @Override
    public int getItemCount() {
        return eList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUsername, tvNama, tvEmail, tvNohp,tvPassword;
        private Button btnEdit, btnHapus;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvNama = itemView.findViewById(R.id.tvFullname);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvNohp = itemView.findViewById(R.id.tvPhone);
            tvPassword = itemView.findViewById(R.id.tvPassword);

            btnEdit = itemView.findViewById(R.id.btnEdituser);
            btnHapus = itemView.findViewById(R.id.btnHapususer);
        }
    }
}
