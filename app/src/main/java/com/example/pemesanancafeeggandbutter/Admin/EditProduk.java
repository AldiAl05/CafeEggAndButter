package com.example.pemesanancafeeggandbutter.Admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pemesanancafeeggandbutter.Entitas.DataClass;
import com.example.pemesanancafeeggandbutter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

public class EditProduk extends AppCompatActivity {
    ImageView updateImage;
    Button saveButton,btnbacktoedit;
    EditText updateDesc,updateTitle,updateHarga;
    String title,desc,harga;
    String imageUrl;
    String key,oldImageURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_produk);

        updateImage = findViewById(R.id.uploadImage);
        updateDesc = findViewById(R.id.etAEddescproduk);
        updateTitle = findViewById(R.id.etEdnamaproduk);
        updateHarga = findViewById(R.id.etEdhargaproduk);
        saveButton = findViewById(R.id.btnsaveedproduk);
        btnbacktoedit = findViewById(R.id.btnbackmpe);

        ActivityResultLauncher<Intent>activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode()==Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            updateImage.setImageURI(uri);
                        }else{
                            Toast.makeText(EditProduk.this, "gk ada image yang dipilih", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
            Glide.with(EditProduk.this).load(bundle.getString("Image")).into(updateImage);
            updateTitle.setText(bundle.getString("Title"));
            updateDesc.setText(bundle.getString("Description"));
            updateHarga.setText(bundle.getString("Harga"));
            key = bundle.getString("Key");
            oldImageURL = bundle.getString("Image");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Produk").child(key);
        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateproduk = new Intent(Intent.ACTION_PICK);
                updateproduk.setType("image/*");
                activityResultLauncher.launch(updateproduk);
            }
        });
        btnbacktoedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProduk.this,ManageProduk.class);
                startActivity(intent);
                saveData();

            }
        });

    }

    private void saveData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Produk Images")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProduk.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        System.out.println(uri+"uri test");

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri>uriTask =taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl =   urlImage.toString();
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
            dialog.dismiss();
            }
        });
    }

    private void updateData() {
        title = updateTitle.getText().toString().trim();
        desc = updateDesc.getText().toString().trim();
        harga = updateHarga.getText().toString();

        DataClass dataClass = new DataClass(title,desc,harga,imageUrl);
        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                    reference.delete();
                    Toast.makeText(EditProduk.this, "Update", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(EditProduk.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
