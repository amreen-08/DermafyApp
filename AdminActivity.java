package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        DatabaseHelper db = new DatabaseHelper(this);
        EditText etName = findViewById(R.id.pn), etQty = findViewById(R.id.pq), etPrice = findViewById(R.id.pp);
        Spinner sp = findViewById(R.id.pc);

        // SHARED CATEGORY LIST
        String[] categories = {"Cleansers", "Moisturizers", "Serums", "Sunscreen"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        sp.setAdapter(adapter);

        findViewById(R.id.add).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String cat = sp.getSelectedItem().toString(); // Gets "Sunscreen" etc.
            String qStr = etQty.getText().toString().trim();
            String pStr = etPrice.getText().toString().trim();

            if (!name.isEmpty() && !qStr.isEmpty() && !pStr.isEmpty()) {
                db.addProduct(name, cat, Integer.parseInt(qStr), Double.parseDouble(pStr));
                Toast.makeText(this, "Product Saved in " + cat, Toast.LENGTH_SHORT).show();
                etName.setText(""); etQty.setText(""); etPrice.setText("");
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.logout).setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });
    }
}