package com.example.firebasecrudapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductRVAdapter extends RecyclerView.Adapter<ProductRVAdapter.ViewHolder> {

    private ArrayList<ProductRVModal> productRVModalsArrayList;
    private Context context;
    private ProductClickInterface productClickInterface;

    public ProductRVAdapter(ArrayList<ProductRVModal> productRVModalsArrayList, Context context, ProductClickInterface productClickInterface) {
        this.productRVModalsArrayList = productRVModalsArrayList;
        this.context = context;
        this.productClickInterface = productClickInterface;
    }

    int lastPos = -1;



    @NonNull
    @Override
    public ProductRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRVAdapter.ViewHolder holder, int position) {
        ProductRVModal productRVModal = productRVModalsArrayList.get(position);
        holder.productNameTV.setText(productRVModal.getProdname());
        holder.productPriceTV.setText("RS." + productRVModal.getProdprice());
        Picasso.get().load(productRVModal.getProductimg()).into(holder.productIV);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productClickInterface.onProductClick(position);
            }
        });

    }

    private void setAnimation(View itemView, int position){
        if(position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }


    @Override
    public int getItemCount() {
        return productRVModalsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView productNameTV, productPriceTV;
        private ImageView productIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTV = itemView.findViewById(R.id.idTVProductName);
            productPriceTV = itemView.findViewById(R.id.idTVPrice);
            productIV = itemView.findViewById(R.id.idTVProductimg);

        }
    }
    public interface ProductClickInterface{
        void onProductClick(int pos);
    }
}
