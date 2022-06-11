package com.example.mobileproject.Model;

public class OrderlistModel {

    String address, totalpayment, totalprice,orderdate;


    public OrderlistModel() {
    }

    public OrderlistModel(String address, String totalpayment, String totalprice, String orderdate) {
        this.address = address;
        this.totalpayment = totalpayment;
        this.totalprice = totalprice;
        this.orderdate =orderdate;

    }


    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getAddress() {
        return address;
    }

    public String getTotalpayment() {
        return totalpayment;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTotalpayment(String totalpayment) {
        this.totalpayment = totalpayment;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }
}
