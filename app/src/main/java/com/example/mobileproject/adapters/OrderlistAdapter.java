package com.example.mobileproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileproject.Model.OrderlistActivity;
import com.example.mobileproject.R;

import java.util.List;

public class OrderlistAdapter extends RecyclerView.Adapter<OrderlistAdapter.ViewHolder> {

    Context context;
    List<OrderlistActivity> list;

    public OrderlistAdapter(Context context, List<OrderlistActivity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist,parent));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position));
        holder.productName.setText(list.get(position).getProductName());
        holder.orderQuantity.setText(list.get(position).getOrderQuantity());
        holder.orderDate.setText(list.get(position).getOrderDate());
        holder.price.setText(list.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName,orderQuantity,orderDate,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.orderlist_productname);
            orderQuantity = itemView.findViewById(R.id.orderlist_quantity);
            orderDate = itemView.findViewById(R.id.orderlist_date);
            price = itemView.findViewById(R.id.orderlist_date);
        }
    }
}
