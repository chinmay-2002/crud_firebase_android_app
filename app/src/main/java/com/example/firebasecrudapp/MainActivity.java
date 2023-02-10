package com.example.firebasecrudapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements  ProductRVAdapter.ProductClickInterface{


    private RecyclerView productRV;
    private ProgressBar progressBar;
    private FloatingActionButton add;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<ProductRVModal> productRVModalsArrayList ;
    private RelativeLayout bottomSheetRl;
    private ProductRVAdapter productRVAdapter;
    private FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productRV = findViewById(R.id.idrvproducts);
        progressBar = findViewById(R.id.pbloading);
        add = findViewById(R.id.floatAdd);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mauth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");
        productRVModalsArrayList = new ArrayList<>();
//        TODO: Solve this below line error
//        bottomSheetRl = findViewById(R.id.idRLBSheet);
        productRVAdapter = new ProductRVAdapter(productRVModalsArrayList,this, this);

        productRV.setLayoutManager(new LinearLayoutManager(this));
        productRV.setAdapter(productRVAdapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddProductActivity.class));

            }
        });
        getAllProducts();

    }

    private void getAllProducts(){
        productRVModalsArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                progressBar.setVisibility(View.GONE);
                productRVModalsArrayList.add(snapshot.getValue(ProductRVModal.class));
                productRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                progressBar.setVisibility(View.GONE);
                productRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                productRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                progressBar.setVisibility(View.GONE);
                productRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onProductClick(int pos) {
        displayBottomSheet(productRVModalsArrayList.get(pos));
    }

    private  void displayBottomSheet(ProductRVModal productRVModal){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dailougue,bottomSheetRl);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView prodnameTv = layout.findViewById(R.id.productName);
        TextView prodDescTv = layout.findViewById(R.id.description);
        TextView prodSuitedFor = layout.findViewById(R.id.suited);
        TextView prodprice = layout.findViewById(R.id.price);
        ImageView prodIV = layout.findViewById(R.id.prodimg);
        Button edtBtn = layout.findViewById(R.id.editCoursebtn);
        Button viewBtn = layout.findViewById(R.id.viewDetailsBtn);

        prodnameTv.setText(productRVModal.getProdname());
        prodDescTv.setText(productRVModal.getProductDesc());
        prodSuitedFor.setText(productRVModal.getSuitedfor());
        prodprice.setText(productRVModal.getProdprice());
        Picasso.get().load(productRVModal.getProductimg()).into(prodIV);

        edtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditProductActivity.class);
                i.putExtra("course", productRVModal);
                startActivity(i);
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(productRVModal.getProdlink()));
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logOut:
                Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show();
                mauth.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return false;

            default:
                return super.onOptionsItemSelected(item);

        }
//        return super.onOptionsItemSelected(item);
    }
}