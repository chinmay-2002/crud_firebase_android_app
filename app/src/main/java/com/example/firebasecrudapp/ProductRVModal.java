package com.example.firebasecrudapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductRVModal implements Parcelable {
    private String prodname;
    private String prodprice;
    private String suitedfor;
    private String productimg;
    private String prodlink;
    private String productDesc;



    public ProductRVModal(String prodname, String prodprice, String suitedfor, String productimg, String prodlink, String productDesc, String productId) {
        this.prodname = prodname;
        this.prodprice = prodprice;
        this.suitedfor = suitedfor;
        this.productimg = productimg;
        this.prodlink = prodlink;
        this.productDesc = productDesc;
        ProductId = productId;
    }


    protected ProductRVModal(Parcel in) {
        prodname = in.readString();
        prodprice = in.readString();
        suitedfor = in.readString();
        productimg = in.readString();
        prodlink = in.readString();
        productDesc = in.readString();
        ProductId = in.readString();
    }

    public static final Creator<ProductRVModal> CREATOR = new Creator<ProductRVModal>() {
        @Override
        public ProductRVModal createFromParcel(Parcel in) {
            return new ProductRVModal(in);
        }

        @Override
        public ProductRVModal[] newArray(int size) {
            return new ProductRVModal[size];
        }
    };

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    public String getProdprice() {
        return prodprice;
    }

    public void setProdprice(String prodprice) {
        this.prodprice = prodprice;
    }

    public String getSuitedfor() {
        return suitedfor;
    }

    public void setSuitedfor(String suitedfor) {
        this.suitedfor = suitedfor;
    }

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }

    public String getProdlink() {
        return prodlink;
    }

    public void setProdlink(String prodlink) {
        this.prodlink = prodlink;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    private String ProductId;

    public ProductRVModal(){

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(prodname);
        parcel.writeString(prodprice);
        parcel.writeString(suitedfor);
        parcel.writeString(productimg);
        parcel.writeString(prodlink);
        parcel.writeString(productDesc);
        parcel.writeString(ProductId);
    }
}
