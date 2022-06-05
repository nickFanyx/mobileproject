package com.example.mobileproject.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;

public class OrderModel {
    private String OrderId;
    private String userId;
    private @ServerTimestamp
    Date orderDate;
    private float totalPrice;
    private float totalPayment;
    private String address;
    private ArrayList<CartModel> cartModels;

    public OrderModel(String orderId, String userId, float totalPrice, float totalPayment,String address, ArrayList<CartModel> cartModels) {
        OrderId = orderId;
        this.userId = userId;
        this.orderDate = null;
        this.totalPrice = totalPrice;
        this.totalPayment = totalPayment;
        this.address=address;
        this.cartModels = cartModels;
    }

    public OrderModel() {
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(float totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<CartModel> getCartModels() {
        return cartModels;
    }

    public void setCartModels(ArrayList<CartModel> cartModels) {
        this.cartModels = cartModels;
    }
}
