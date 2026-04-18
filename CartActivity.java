package com.example.project;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    DatabaseHelper db;
    ArrayList<ProductModel> cartList;
    CartAdapter adapter;
    TextView tvTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = new DatabaseHelper(this);
        cartList = new ArrayList<>();
        ListView lv = findViewById(R.id.cartListView);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);

        loadCartData();

        // Pass 'this' so the adapter can talk back to the activity
        adapter = new CartAdapter(this, cartList);
        lv.setAdapter(adapter);

        findViewById(R.id.btnBackToCategory).setOnClickListener(v -> finish());

        findViewById(R.id.buy).setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                showSuccessDialog();
            }
        });
    }

    public void loadCartData() {
        cartList.clear();
        double total = 0;
        Cursor c = db.getCartItems();
        while (c.moveToNext()) {
            double price = c.getDouble(2);
            cartList.add(new ProductModel(c.getString(1), price));
            total += price;
        }
        c.close();
        tvTotalAmount.setText(String.format("₹%.2f", total));
    }

    // This is the method the Adapter will call
    public void refreshTotal() {
        double total = 0;
        for (ProductModel item : cartList) {
            total += item.getPrice();
        }
        tvTotalAmount.setText(String.format("₹%.2f", total));
    }

    private void showSuccessDialog() {
        db.clearCart();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        Button btnOk = dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        dialog.show();
    }
}