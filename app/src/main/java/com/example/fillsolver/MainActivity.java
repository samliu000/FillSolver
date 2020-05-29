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

    // setting start state
    boolean settingStart = false;

    // start state row
    int startRow = 0;

    // start col
    int startCol = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GameGrid
        gridView = (GridLayout)findViewById(R.id.GameGrid);

        for(int cell = 0; cell < 48; cell++) {
            Button cellButton = (Button) gridView.getChildAt(cell);
            cellButton.setBackgroundResource(android.R.drawable.btn_default);
        }

        // FillSolver Object
        solve = new FillSolver(8,6);

        // START!
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // solve
                try {
                    solve.updateStart(startRow, startCol);
                    solve.fillSolve(startRow, startCol, gridView);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                directions = solve.getDirections();

                for(int i = 1; i < directions.size() + 1; i++) {

                    // get point location
                    Log.i("Where are we", "Current i: " + i);
                    Point currPoint = directions.get(i - 1);
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

        // user wants to set start
        findViewById(R.id.setStart).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                settingStart = true;
                ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#ffff00"));
            }
        });

        // all 48 buttons oh boy :(
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 0;
                    settingStart = false;
                    ((Button)gridView.getChildAt(0)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else
                    solve.removeSpace(0, 0, gridView);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 1;
                    settingStart = false;
                    ((Button)gridView.getChildAt(1)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else
                    solve.removeSpace(0, 1, gridView);
            }
        });

    }


}
