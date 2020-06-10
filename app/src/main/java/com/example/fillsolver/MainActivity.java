package com.example.fillsolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        // for testing purposes
//        findViewById(R.id.Testing).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(int cell = 0; cell < 48; cell++) {
//                    Button cellButton = (Button) gridView.getChildAt(cell);
//                    cellButton.setBackgroundColor(Color.parseColor("#ffff00"));
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        // START!
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // solve
                try {
                    solve.updateStart(startRow, startCol);

                    if(!solve.fillSolve(startRow, startCol, gridView)) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "No Solution", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();

                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // if it works
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

        // reset!
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for(int cell = 0; cell < 48; cell++) {
                    Button cellButton = (Button) gridView.getChildAt(cell);
                    cellButton.setBackgroundResource(android.R.drawable.btn_default);
                    cellButton.setText("");
                }

                solve = new FillSolver(8,6);

                startRow = 0;
                startCol = 0;
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
                    // reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 0;
                    settingStart = false;
                    ((Button)gridView.getChildAt(0)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else     // we are not setting start
                    solve.removeSpace(0, 0, gridView);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    // reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 1;
                    settingStart = false;
                    ((Button)gridView.getChildAt(1)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else     // we are not setting start
                    solve.removeSpace(0, 1, gridView);
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 2;
                    settingStart = false;
                    ((Button)gridView.getChildAt(2)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(0, 2, gridView);
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 3;
                    settingStart = false;
                    ((Button)gridView.getChildAt(3)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(0, 3, gridView);
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 4;
                    settingStart = false;
                    ((Button)gridView.getChildAt(4)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(0, 4, gridView);
            }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 5;
                    settingStart = false;
                    ((Button)gridView.getChildAt(5)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(0, 5, gridView);
            }
        });

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 1;
                    startCol = 0;
                    settingStart = false;
                    ((Button)gridView.getChildAt(6)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(1, 0, gridView);
            }
        });

        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 1;
                    startCol = 1;
                    settingStart = false;
                    ((Button)gridView.getChildAt(7)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(1, 1, gridView);
            }
        });

        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 1;
                    startCol = 2;
                    settingStart = false;
                    ((Button)gridView.getChildAt(8)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(1, 2, gridView);
            }
        });

        findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 1;
                    startCol = 3;
                    settingStart = false;
                    ((Button)gridView.getChildAt(9)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(1, 3, gridView);
            }
        });

        findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 1;
                    startCol = 4;
                    settingStart = false;
                    ((Button)gridView.getChildAt(10)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(1, 4, gridView);
            }
        });

        findViewById(R.id.button12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 1;
                    startCol = 5;
                    settingStart = false;
                    ((Button)gridView.getChildAt(11)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(1, 5, gridView);
            }
        });

        findViewById(R.id.button13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 2;
                    startCol = 0;
                    settingStart = false;
                    ((Button)gridView.getChildAt(12)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(2, 0, gridView);
            }
        });

        findViewById(R.id.button14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 2;
                    startCol = 1;
                    settingStart = false;
                    ((Button)gridView.getChildAt(13)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(2, 1, gridView);
            }
        });

        findViewById(R.id.button15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 2;
                    startCol = 2;
                    settingStart = false;
                    ((Button)gridView.getChildAt(14)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(2, 2, gridView);
            }
        });

        findViewById(R.id.button16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 2;
                    startCol = 3;
                    settingStart = false;
                    ((Button)gridView.getChildAt(15)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(2, 3, gridView);
            }
        });

        findViewById(R.id.button17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 2;
                    startCol = 4;
                    settingStart = false;
                    ((Button)gridView.getChildAt(16)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(2, 4, gridView);
            }
        });

        findViewById(R.id.button18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 2;
                    startCol = 5;
                    settingStart = false;
                    ((Button)gridView.getChildAt(17)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(2, 5, gridView);
            }
        });

        findViewById(R.id.button19).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 3;
                    startCol = 0;
                    settingStart = false;
                    ((Button)gridView.getChildAt(18)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(3, 0, gridView);
            }
        });

        findViewById(R.id.button20).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 3;
                    startCol = 1;
                    settingStart = false;
                    ((Button)gridView.getChildAt(19)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(3, 1, gridView);
            }
        });

        findViewById(R.id.button21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 3;
                    startCol = 2;
                    settingStart = false;
                    ((Button)gridView.getChildAt(20)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(3, 2, gridView);
            }
        });

        findViewById(R.id.button22).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 3;
                    startCol = 3;
                    settingStart = false;
                    ((Button)gridView.getChildAt(21)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(3, 3, gridView);
            }
        });

        findViewById(R.id.button23).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 3;
                    startCol = 4;
                    settingStart = false;
                    ((Button)gridView.getChildAt(22)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(3, 4, gridView);
            }
        });

        findViewById(R.id.button24).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 3;
                    startCol = 5;
                    settingStart = false;
                    ((Button)gridView.getChildAt(23)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(3, 5, gridView);
            }
        });

        findViewById(R.id.button25).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 4;
                    startCol = 0;
                    settingStart = false;
                    ((Button)gridView.getChildAt(24)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(4, 0, gridView);
            }
        });

        findViewById(R.id.button26).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 4;
                    startCol = 1;
                    settingStart = false;
                    ((Button)gridView.getChildAt(25)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(4, 1, gridView);
            }
        });

        findViewById(R.id.button27).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 4;
                    startCol = 2;
                    settingStart = false;
                    ((Button)gridView.getChildAt(26)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(4, 2, gridView);
            }
        });

        findViewById(R.id.button28).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 4;
                    startCol = 3;
                    settingStart = false;
                    ((Button)gridView.getChildAt(27)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(4, 3, gridView);
            }
        });

        findViewById(R.id.button29).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 4;
                    startCol = 4;
                    settingStart = false;
                    ((Button)gridView.getChildAt(28)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(4, 4, gridView);
            }
        });

        findViewById(R.id.button30).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 4;
                    startCol = 5;
                    settingStart = false;
                    ((Button)gridView.getChildAt(29)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(4, 5, gridView);
            }
        });

        findViewById(R.id.button31).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 5;
                    startCol = 0;
                    settingStart = false;
                    ((Button)gridView.getChildAt(30)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(5, 0, gridView);
            }
        });

        findViewById(R.id.button32).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 5;
                    startCol = 1;
                    settingStart = false;
                    ((Button)gridView.getChildAt(31)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(5, 1, gridView);
            }
        });

        findViewById(R.id.button33).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 5;
                    startCol = 2;
                    settingStart = false;
                    ((Button)gridView.getChildAt(32)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(5, 2, gridView);
            }
        });

        findViewById(R.id.button34).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 5;
                    startCol = 3;
                    settingStart = false;
                    ((Button)gridView.getChildAt(33)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(5, 3, gridView);
            }
        });

        findViewById(R.id.button35).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 5;
                    startCol = 4;
                    settingStart = false;
                    ((Button)gridView.getChildAt(34)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(5, 4, gridView);
            }
        });

        findViewById(R.id.button36).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 5;
                    startCol = 5;
                    settingStart = false;
                    ((Button)gridView.getChildAt(35)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(5, 5, gridView);
            }
        });

        findViewById(R.id.button37).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 6;
                    startCol = 0;
                    settingStart = false;
                    ((Button)gridView.getChildAt(36)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(6, 0, gridView);
            }
        });

        findViewById(R.id.button38).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 6;
                    startCol = 1;
                    settingStart = false;
                    ((Button)gridView.getChildAt(37)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(6, 1, gridView);
            }
        });

        findViewById(R.id.button39).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 6;
                    startCol = 2;
                    settingStart = false;
                    ((Button)gridView.getChildAt(38)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(6, 2, gridView);
            }
        });

        findViewById(R.id.button40).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 6;
                    startCol = 3;
                    settingStart = false;
                    ((Button)gridView.getChildAt(39)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(6, 3, gridView);
            }
        });

        findViewById(R.id.button41).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 6;
                    startCol = 4;
                    settingStart = false;
                    ((Button)gridView.getChildAt(40)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(6, 4, gridView);
            }
        });

        findViewById(R.id.button42).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 6;
                    startCol = 5;
                    settingStart = false;
                    ((Button)gridView.getChildAt(41)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(6, 5, gridView);
            }
        });

        findViewById(R.id.button43).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 7;
                    startCol = 0;
                    settingStart = false;
                    ((Button)gridView.getChildAt(42)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(7, 0, gridView);
            }
        });

        findViewById(R.id.button44).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 7;
                    startCol = 1;
                    settingStart = false;
                    ((Button)gridView.getChildAt(43)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(7, 1, gridView);
            }
        });

        findViewById(R.id.button45).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 7;
                    startCol = 2;
                    settingStart = false;
                    ((Button)gridView.getChildAt(44)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(7, 2, gridView);
            }
        });

        findViewById(R.id.button46).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 7;
                    startCol = 3;
                    settingStart = false;
                    ((Button)gridView.getChildAt(45)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(7, 3, gridView);
            }
        });

        findViewById(R.id.button47).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 7;
                    startCol = 4;
                    settingStart = false;
                    ((Button)gridView.getChildAt(46)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(7, 4, gridView);
            }
        });

        findViewById(R.id.button48).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 7;
                    startCol = 5;
                    settingStart = false;
                    ((Button)gridView.getChildAt(47)).setBackgroundColor(Color.parseColor("#78FF00"));
                    ((Button) findViewById(R.id.setStart)).setBackgroundColor(Color.parseColor("#d2cced"));
                }
                else    // we are not setting start
                    solve.removeSpace(7, 5, gridView);
            }
        });

    }


}
