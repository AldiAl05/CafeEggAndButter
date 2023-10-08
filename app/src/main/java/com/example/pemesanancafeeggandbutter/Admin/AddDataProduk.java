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

import com.example.pemesanancafeeggandbutter.Entitas.DataClass;
import com.example.pemesanancafeeggandbutter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;


public class AddDataProduk extends AppCompatActivity {

    ImageView uploadImgProduk;
    Button btnsaveDataProduk,btnbacktoedit;
    EditText uploadNamaProduk, uploadDesProduk, uploadHargaProduk;
    String imageURL;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_produk);

        uploadImgProduk = findViewById(R.id.uploadImage);
        uploadNamaProduk = findViewById(R.id.etAddnamaproduk);
        uploadDesProduk = findViewById(R.id.etAdddescproduk);
        uploadHargaProduk = findViewById(R.id.etAddhargaproduk);
        btnbacktoedit = findViewById(R.id.btnbackmp);

        btnsaveDataProduk = findViewById(R.id.btnsaveproduk);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImgProduk.setImageURI(uri);
                        } else {
                            Toast.makeText(getApplicationContext(), "Tidak Ada Gambar yang Dipilih", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        btnbacktoedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        uploadImgProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoSelected = new Intent(Intent.ACTION_PICK);
                photoSelected.setType("image/*");
                activityResultLauncher.launch(photoSelected);
            }
        });

        btnsaveDataProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAddDataProduk();
            }
        });
    }
        public void saveAddDataProduk(){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Produk Images")
                    .child(uri.getLastPathSegment());

            //dialog progres loading
            AlertDialog.Builder builder = new AlertDialog.Builder(AddDataProduk.this);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageURL = urlImage.toString();
                    uploadAddDataProduk();
                    dialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    dialog.dismiss();
                }
            });
        }
        public void uploadAddDataProduk(){
            String title = uploadNamaProduk.getText().toString();
            String desc = uploadDesProduk.getText().toString();
            String harga = uploadHargaProduk.getText().toString();

            DataClass dataClass = new DataClass(title, desc, harga, imageURL);

//            String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            FirebaseDatabase.getInstance().getReference("Produk").child(title).
                    setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
}