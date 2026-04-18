package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper dbHelper;
    private ArrayList<ProductModel> productList;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        dbHelper = new DatabaseHelper(this);
        productList = new ArrayList<>();

        // Fix for "Cannot find symbol" errors
        listView = findViewById(R.id.productListView);
        TextView tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnGoToCart = findViewById(R.id.btnGoToCart);

        // Navigation Logic
        btnBack.setOnClickListener(v -> finish());

        btnGoToCart.setOnClickListener(v -> {
            Intent intent = new Intent(ProductListActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Load specific category data
        String selectedCategory = getIntent().getStringExtra("CATEGORY");
        if (selectedCategory != null) {
            tvCategoryTitle.setText(selectedCategory);
            loadProducts(selectedCategory);
        }

        adapter = new ProductAdapter(this, productList);
        listView.setAdapter(adapter);
    }

    private void loadProducts(String category) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Assuming Column 1 is Name and Column 4 is Price in 'products' table
        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE category = ?", new String[]{category});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                productList.add(new ProductModel(cursor.getString(1), cursor.getDouble(4)));
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}