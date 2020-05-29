package com.example.fillsolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    // gameGrid
    GridLayout gridView;

    // solver object
    FillSolver solve;

    // directions
    Vector<Point> directions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GameGrid
        gridView = (GridLayout)findViewById(R.id.GameGrid);

        // FillSolver Object
        solve = new FillSolver(8,6,0,0);

        // solve
        try {
            solve.fillSolve(0,0, gridView);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        directions = solve.getDirections();

        // logic for when user clicks on answer option
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {

                for(int i = 2; i < directions.size() + 2; i++) {

                    // get point location
                    Log.i("Where are we", "Current i: " + i);
                    Point currPoint = directions.get(i - 2);
                    int currRow = currPoint.getX();
                    int currCol = currPoint.getY();
                    int whereInGrid = (currRow * 6) + currCol;

                    // update text
                    Log.i("Where in Grid", "Grid Location: " + whereInGrid);
                    Button cellButton = (Button)gridView.getChildAt(whereInGrid);
                    cellButton.setText("" + i);

                }

            }

        });

    }


}
