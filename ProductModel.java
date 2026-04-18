package com.example.project;

public class ProductModel {
    private String name;
    private double price;

    public ProductModel(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
}