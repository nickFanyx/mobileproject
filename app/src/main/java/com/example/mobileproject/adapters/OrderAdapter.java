package com.example.mobileproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mobileproject.Model.OrderModel;
import com.example.mobileproject.Model.OrderlistModel;
import com.example.mobileproject.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    List<OrderlistModel> list;

    public OrderAdapter(Context context, List<OrderlistModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        OrderlistModel orderlistModel = list.get(position);
        holder.address.setText(orderlistModel.getAddress());
        holder.totalpayment.setText(orderlistModel.getTotalpayment());
        holder.totalprice.setText(orderlistModel.getTotalprice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView address, totalpayment, totalprice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.order_address);
            totalpayment = itemView.findViewById(R.id.order_totalpayment);
            totalprice = itemView.findViewById(R.id.order_totalprice);
        }
    }


}
