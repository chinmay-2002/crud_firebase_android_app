package com.example.firebasecrudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddProductActivity extends AppCompatActivity {

    private TextInputEditText pname,pprice,psuited,pimglink,plink,pdiscription;
    private Button addProduct;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String ProductId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        pname = findViewById(R.id.idEdtProductName);
        pprice = findViewById(R.id.idEdtProductPrice);
        psuited = findViewById(R.id.idEdtProductSuitedFor);
        pimglink = findViewById(R.id.idEdtProductimgLink);
        plink = findViewById(R.id.idEdtProductimgLink);
        pdiscription = findViewById(R.id.idEdtProductDiscription);
        addProduct = findViewById(R.id.addCourseBtn);
        progressBar = findViewById(R.id.pbloading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String prodname = pname.getText().toString();
                String prodprice = pprice.getText().toString();
                String suitedfor = psuited.getText().toString();
                String productimg = pimglink.getText().toString();
                String prodlink = plink.getText().toString();
                String courseDesc = pdiscription.getText().toString();
                ProductId = prodname;

                ProductRVModal productRVModal = new ProductRVModal(prodname,prodprice,suitedfor,productimg,prodlink,courseDesc,ProductId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        databaseReference.child(ProductId).setValue(productRVModal);
                        Toast.makeText(AddProductActivity.this, "Hotel Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddProductActivity.this, MainActivity.class ));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddProductActivity.this, "Error is " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });



    }
}