package com.example.mobileproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileproject.Model.CartModel;
import com.example.mobileproject.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdapterCheckout extends RecyclerView.Adapter<AdapterCheckout.CheckoutViewHolder>{
    private Context context;
    private ArrayList<CartModel> cartDetailsModel;
    private static final String TAG = "MyActivity";
    private FirebaseFirestore db;

    public AdapterCheckout(Context context, ArrayList<CartModel> cartDetailsModel) {
        this.context = context;
        this.cartDetailsModel = cartDetailsModel;
    }

    @Override
    public int getItemCount() {
        if (cartDetailsModel != null) {
            return cartDetailsModel.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public AdapterCheckout.CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_checkout, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        holder.tvProductName.setText(cartDetailsModel.get(position).getProductName());
        Glide.with(context).load(cartDetailsModel.get(position).getImageurl()).into(holder.ivProduct);
        holder.tvPrice.setText("RM " + String.format("%.2f", cartDetailsModel.get(position).getPrice()));
        holder.tvQuantity.setText(cartDetailsModel.get(position).getOrderQty()+ " x");
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivProduct;
        private TextView  tvQuantity, tvProductName, tvPrice;
         public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
             ivProduct = itemView.findViewById(R.id.ivProduct);
             tvProductName = itemView.findViewById(R.id.tvProductName);
             tvQuantity = itemView.findViewById(R.id.tvQty);
             tvPrice = itemView.findViewById(R.id.tvPrice);
        }

    }
}
