package com.example.firebasecrudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProductActivity extends AppCompatActivity {

    private TextInputEditText pname,pprice,psuited,pimglink,plink,pdiscription;
    private Button updateProduct,deleteProduct;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String ProductId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        pname = findViewById(R.id.idEdtProductName);
        pprice = findViewById(R.id.idEdtProductPrice);
        psuited = findViewById(R.id.idEdtProductSuitedFor);
        pimglink = findViewById(R.id.idEdtProductimgLink);
        plink = findViewById(R.id.idEdtProductimgLink);
        pdiscription = findViewById(R.id.idEdtProductDiscription);
        updateProduct = findViewById(R.id.updateCourseBtn);
        deleteProduct = findViewById(R.id.deleteCourseBtn);
        progressBar = findViewById(R.id.pbloading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses").child(ProductId);
    }
}