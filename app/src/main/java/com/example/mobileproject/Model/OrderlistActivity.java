package com.example.mobileproject.Model;

public class OrderlistActivity {

    String productName;
    int orderQuantity;
    String orderDate;
    int price;

    public OrderlistActivity() {
    }

    public OrderlistActivity(String productName, int orderQuantity, String orderDate, int price, String img_url) {
        this.productName = productName;
        this.orderQuantity = orderQuantity;
        this.orderDate = orderDate;
        this.price = price;

    }

    public String getProductName() {
        return productName;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getPrice() {
        return price;
    }

}
