package com.example.fillsolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayout gridView = (GridLayout)findViewById(R.id.GameGrid);
        int[] testInput = new int[48];
        CellAdapter booksAdapter = new CellAdapter(this, testInput);
        for(int i = 0; i < 4; i++) {
            Button hi = (Button)gridView.getChildAt(i);

        }
    }
}
