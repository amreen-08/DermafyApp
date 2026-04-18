package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<ProductModel> {
    private DatabaseHelper db;

    public ProductAdapter(Context context, ArrayList<ProductModel> products) {
        super(context, 0, products);
        db = new DatabaseHelper(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductModel product = getItem(position);

        if (convertView == null) {
            // Using list_item to match your file structure
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.itemName);
        TextView price = convertView.findViewById(R.id.itemPrice);
        Button addBtn = convertView.findViewById(R.id.btnAddCart);

        if (product != null) {
            name.setText(product.getName());
            price.setText("₹" + product.getPrice());

            addBtn.setOnClickListener(v -> {
                // This will now work because we added the method to DatabaseHelper
                db.addToCart(product.getName(), product.getPrice());
                Toast.makeText(getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }
}