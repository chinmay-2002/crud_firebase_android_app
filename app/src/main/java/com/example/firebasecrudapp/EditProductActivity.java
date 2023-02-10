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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditProductActivity extends AppCompatActivity {

    private TextInputEditText pname,pprice,psuited,pimglink,plink,pdiscription;
    private Button updateProduct,deleteProduct;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String ProductId;
    private ProductRVModal productRVModal;

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
        productRVModal = getIntent().getParcelableExtra("course");
        if(productRVModal!=null){
            pname.setText(productRVModal.getProdname());
            pprice.setText(productRVModal.getProdprice());
            psuited.setText(productRVModal.getSuitedfor());
            pimglink.setText(productRVModal.getProductimg());
            plink.setText(productRVModal.getProdlink());
            pdiscription.setText(productRVModal.getProductDesc());
            ProductId = productRVModal.getProductId();
        }
        databaseReference = firebaseDatabase.getReference("Courses").child(ProductId);

        updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String prodname = pname.getText().toString();
                String prodprice = pprice.getText().toString();
                String suitedfor = psuited.getText().toString();
                String productimg = pimglink.getText().toString();
                String prodlink = plink.getText().toString();
                String courseDesc = pdiscription.getText().toString();


                Map<String, Object> map = new HashMap<>();
                map.put("prodname", prodname);
                map.put("prodprice", prodprice);
                map.put("suitedfor", suitedfor);
                map.put("productimg", productimg);
                map.put("prodlink", prodlink);
                map.put("productDesc", courseDesc);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditProductActivity.this, "Product Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditProductActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditProductActivity.this, "Failed to update product", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();
            }
        });

    }
    private void deleteProduct(){
        databaseReference.removeValue();
        Toast.makeText(this, "Product Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditProductActivity.this, MainActivity.class));
    }
}