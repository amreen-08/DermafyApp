package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<ProductModel> cartList;

    public CartAdapter(Context context, ArrayList<ProductModel> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public int getCount() { return cartList.size(); }

    @Override
    public Object getItem(int i) { return cartList.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cart_item, viewGroup, false);
        }

        TextView name = view.findViewById(R.id.cartItemName);
        TextView price = view.findViewById(R.id.cartItemPrice);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        ProductModel product = cartList.get(position);
        name.setText(product.getName());
        price.setText("₹" + product.getPrice());

        btnDelete.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(context);

            // 1. Remove from Database
            db.deleteCartItem(product.getName());

            // 2. Remove from the local list
            cartList.remove(position);

            // 3. Update the ListView UI
            notifyDataSetChanged();

            // 4. Update the Total on the Activity screen
            if (context instanceof CartActivity) {
                ((CartActivity) context).refreshTotal();
            }

            Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}