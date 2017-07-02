package com.example.yuechengshi.finalprojectshoppingsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private ArrayList<Product> products;

    private String search;

    public static ArrayAdapter<Product> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //set up listView
        products = new ArrayList<>();

        ListView view = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_expandable_list_item_1, products);
        view.setAdapter(adapter);




        Intent intent = getIntent();
        search = intent.getStringExtra("search");


    }


    @Override
    public void onStart() {
        super.onStart();
        ProductData fetchProductData = new ProductData(getBaseContext(), products);
        fetchProductData.execute(search);

    }
}
