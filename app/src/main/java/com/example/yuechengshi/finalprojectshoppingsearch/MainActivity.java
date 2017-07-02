package com.example.yuechengshi.finalprojectshoppingsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void search(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        String searchName = editText.getText().toString();
        Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
        intent.putExtra("search",searchName);
        startActivity(intent);
    }
}
