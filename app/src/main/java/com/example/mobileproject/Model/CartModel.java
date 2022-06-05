package com.example.mobileproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CartModel  implements Parcelable {
    private String itemId;
    private String productId;
    private String productName;
    private String userId;
    private int orderQty;
    private float price;
    private String imageurl;

    public CartModel(String productId, String productName, String userId, int orderQty, float price, String imageurl) {
        this.productId = productId;
        this.productName = productName;
        this.userId = userId;
        this.orderQty = orderQty;
        this.price = price;
        this.imageurl = imageurl;
    }
    public CartModel(String itemId,String productId, String productName, String userId, int orderQty, float price, String imageurl) {
        this.itemId=itemId;
        this.productId = productId;
        this.productName = productName;
        this.userId = userId;
        this.orderQty = orderQty;
        this.price = price;
        this.imageurl = imageurl;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemId);
        dest.writeString(this.productId);
        dest.writeString(this.productName);
        dest.writeString(this.userId);
        dest.writeInt(this.orderQty);
        dest.writeFloat(this.price);
        dest.writeString(this.imageurl);
    }
    protected CartModel(Parcel in) {
        this.itemId=in.readString();
        this.productId = in.readString();
        this.productName = in.readString();
        this.userId = in.readString();
        this.orderQty = in.readInt();
        this.price = in.readFloat();
        this.imageurl = in.readString();
    }
    public static final Parcelable.Creator<CartModel> CREATOR = new Parcelable.Creator<CartModel>() {
        @Override
        public CartModel createFromParcel(Parcel source) {
            return new CartModel(source);
        }

        @Override
        public CartModel[] newArray(int size) {
            return new CartModel[size];
        }
    };

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public CartModel() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }



}
