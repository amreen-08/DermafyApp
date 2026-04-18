package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // 1. Initialize the individual buttons that exist in the XML
        Button btnBack = findViewById(R.id.btnBack);
        Button btnCleansers = findViewById(R.id.btnCleansers);
        Button btnMoisturizers = findViewById(R.id.btnMoisturizers);
        Button btnSerums = findViewById(R.id.btnSerums);
        Button btnSunscreen = findViewById(R.id.btnSunscreen); // Find the new button

        // 2. Set click listener for the Back button to go to the selection screen
        btnBack.setOnClickListener(v -> finish());

        // 3. Set click listeners for the category buttons
        // Cleansers Click
        btnCleansers.setOnClickListener(v -> navigateToProducts("Cleansers"));

        // Moisturizers Click
        btnMoisturizers.setOnClickListener(v -> navigateToProducts("Moisturizers"));

        // Serums Click
        btnSerums.setOnClickListener(v -> navigateToProducts("Serums"));

        // Sunscreen Click (New)
        btnSunscreen.setOnClickListener(v -> navigateToProducts("Sunscreen"));
    }

    /**
     * Common helper method to navigate to the ProductListActivity and pass the selected category.
     * This ensures the next activity knows which items to load from the database.
     */
    private void navigateToProducts(String categoryName) {
        Intent intent = new Intent(CategoryActivity.this, ProductListActivity.class);
        intent.putExtra("CATEGORY", categoryName);
        startActivity(intent);
        Toast.makeText(this, "Selected: " + categoryName, Toast.LENGTH_SHORT).show();
    }
}