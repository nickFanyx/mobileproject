package com.example.mobileproject.Model;

public class OrderlistModel {



    public OrderlistModel() {
    }

    public OrderlistModel(String address, String totalpayment, String totalprice) {
        this.address = address;
        this.totalpayment = totalpayment;
        this.totalprice = totalprice;
    }

    String address, totalpayment, totalprice;

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
