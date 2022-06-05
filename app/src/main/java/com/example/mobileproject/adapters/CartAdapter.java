package com.example.mobileproject.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileproject.Model.CartModel;
import com.example.mobileproject.R;
import com.example.mobileproject.ShoppingCart;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemViewHolder> {

    private Context context;
    private OnListItemClick onListItemClick;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ArrayList<CartModel> cartModels;
    private static final String TAG = "MyActivity";
    private FirebaseFirestore db;
    private int tempIndex;
    private CartModel temp;
    private ArrayList<CheckBox> cbArray;


    public CartAdapter(Context context, ArrayList<CartModel> cartModels, OnListItemClick onListItemClick, ShimmerFrameLayout shimmerFrameLayout) {
        this.context = context;
        this.cartModels = cartModels;
        this.onListItemClick = onListItemClick;
        this.shimmerFrameLayout = shimmerFrameLayout;

        this.cbArray = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvProductName.setText(cartModels.get(position).getProductName());
        Glide.with(context).load(cartModels.get(position).getImageurl()).into(holder.ivProduct);
        holder.tvPrice.setText("RM " + String.format("%.2f", cartModels.get(position).getPrice()));
        holder.tvQuantity.setText("" + cartModels.get(position).getOrderQty());

        if (cbArray.size() < getItemCount()) {
            cbArray.add(holder.cbSelect);
        }

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = cartModels.get(position).getOrderQty() - 1;
                if (qty == 0) {
                    tempIndex=position;
                    temp = cartModels.get(position);
                    deleteAlert(position);
                } else {
                    cartModels.get(position).setOrderQty(qty);
                    holder.tvQuantity.setText("" + cartModels.get(position).getOrderQty());
                    if (holder.cbSelect.isChecked()) {
                        onListItemClick.onMinusSelected(cartModels.get(position).getOrderQty(), cartModels.get(position).getPrice(), cartModels.get(position));
                    }


                }
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempIndex=position;
                temp = cartModels.get(position);
                deleteAlert(position);
            }
        });

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = cartModels.get(position).getOrderQty() + 1;
                cartModels.get(position).setOrderQty(qty);
                holder.tvQuantity.setText("" + cartModels.get(position).getOrderQty());
                if (holder.cbSelect.isChecked()) {
                    onListItemClick.onAddSelected(cartModels.get(position).getOrderQty(), cartModels.get(position).getPrice(), cartModels.get(position));
                }


            }
        });

        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cbSelect.isChecked()) {

                    onListItemClick.onCheckClick(cartModels.get(position).getOrderQty(), cartModels.get(position).getPrice(), cartModels.get(position));

                } else {
                    onListItemClick.onDeselectClick(cartModels.get(position).getOrderQty(), cartModels.get(position).getPrice(), cartModels.get(position));

                }
            }
        });


    }


    @NonNull
    @Override
    public CartAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_cart, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (cartModels != null) {
            return cartModels.size();
        } else {
            return 0;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProduct;
        private Button deleteBtn;
        private CheckBox cbSelect;
        private TextView addBtn, minusBtn, tvQuantity, tvProductName, tvPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvProductName = itemView.findViewById(R.id.tvprodName);
            deleteBtn = itemView.findViewById(R.id.btnDelete);
            addBtn = itemView.findViewById(R.id.plus);
            minusBtn = itemView.findViewById(R.id.minus);
            tvQuantity = itemView.findViewById(R.id.tvQty);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            cbSelect = itemView.findViewById(R.id.cbSelect);
            //btnCancel = itemView.findViewById(R.id.btnCancel);

        }
    }

    public void deleteAlert(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to remove this from cart?");
        builder.setTitle("Delete !");
        builder.setCancelable(false);
        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {


                                if (cbArray.get(tempIndex).isChecked()) {
                                    Log.i(TAG, "Error: item count" + getItemCount());

                                    if (tempIndex + 1 != getItemCount()) {
                                        //check if the cb for the next index is check or not
                                        if (!cbArray.get(tempIndex + 1).isChecked()) {
                                            cbArray.get(tempIndex).setChecked(false);
                                        }
                                    }
                                    onListItemClick.onRemoveItem(cartModels.get(position).getOrderQty(), cartModels.get(position).getPrice(), cartModels.get(position));
                                }

                                db = FirebaseFirestore.getInstance();
                                final DocumentReference ref = db.collection("Cart").document(cartModels.get(position).getItemId());

                                /*ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Removed Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed to remove", Toast.LENGTH_SHORT).show();
                                    }
                                });*/
                                cartModels.remove(tempIndex);
                                if (getItemCount() == 0) {
                                    notifyDataSetChanged();
                                }else{
                                    notifyItemRemoved(tempIndex);
                                    notifyDataSetChanged();
                                }





                            }
                        });
        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }


    public interface OnListItemClick {
        void onItemClick(int position, DocumentSnapshot documentSnapshot);

        void onCheckClick(int qty, float price, CartModel cartModel);

        void onDeselectClick(int qty, float price, CartModel cartModel);

        void onAddSelected(int qty, float price, CartModel cartModel);

        void onMinusSelected(int qty, float price, CartModel cartModel);

        void onRemoveItem(int qty, float price, CartModel cartModel);

        void resetTotalPrice(float tempPrice);

    }

}
