package com.example.fillsolver;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.GridView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView = (GridView)findViewById(R.id.GameGrid);
        int[] testInput = new int[48];
        CellAdapter booksAdapter = new CellAdapter(this, testInput);
        gridView.setAdapter(booksAdapter);
    }
}
