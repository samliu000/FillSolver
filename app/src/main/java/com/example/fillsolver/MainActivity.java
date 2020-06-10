package com.example.fillsolver;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Vector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

    // gameGrid
    GridLayout gridView;

    // solver object
    FillSolver solve;

    // directions
    Vector<Point> directions;

    // current context
    Context context;

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

        // initialize context
        context = this;

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
                solve.updateStart(startRow, startCol);

                if(solve.fillSolve(startRow, startCol)) {

                } else {
                    Toast failureMessage = Toast.makeText(getApplicationContext(),
                            "No Solution", Toast.LENGTH_SHORT);
                    failureMessage.setGravity(Gravity.BOTTOM, 0, 0);
                    failureMessage.show();


                }

                directions = solve.getDirections();

                for(int i = 1; i < directions.size() + 1; i++) {

                    // get point location
                    Point currPoint = directions.get(i - 1);
                    int currRow = currPoint.getX();
                    int currCol = currPoint.getY();
                    int whereInGrid = (currRow * 6) + currCol;

                    // update text
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
                ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                solve = new FillSolver(8,6);

                startRow = 0;
                startCol = 0;
            }


        });

        // user wants to set start hi
        findViewById(R.id.setStart).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                settingStart = true;
                Drawable d = getResources().getDrawable(R.drawable.start_button);
                ((Button) findViewById(R.id.setStart)).setBackground(d);
            }
        });

        // all 48 buttons oh boy :(
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 0;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(0)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(0, 0, gridView, context);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingStart == true) {
                    //reset previous start
                    ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 1;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(1)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(0, 1, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(2)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(0, 2, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(3)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(0, 3, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(4)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(0, 4, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(5)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(0, 5, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(6)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(1, 0, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(7)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(1, 1, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(8)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(1, 2, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(9)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(1, 3, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(10)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(1, 4, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(11)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(1, 5, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(12)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(2, 0, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(13)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(2, 1, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(14)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(2, 2, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(15)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(2, 3, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(16)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(2, 4, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(17)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(2, 5, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(18)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(3, 0, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(19)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(3, 1, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(20)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(3, 2, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(21)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(3, 3, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(22)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(3, 4, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(23)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(3, 5, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(24)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(4, 0, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(25)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(4, 1, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(26)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(4, 2, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(27)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(4, 3, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(28)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(4, 4, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(29)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(4, 5, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(30)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(5, 0, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(31)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(5, 1, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(32)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(5, 2, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(33)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(5, 3, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(34)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(5, 4, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(35)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(5, 5, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(36)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(6, 0, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(37)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(6, 1, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(38)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(6, 2, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(39)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(6, 3, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(40)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(6, 4, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(41)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(6, 5, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(42)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(7, 0, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(43)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(7, 1, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(44)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(7, 2, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(45)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(7, 3, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(46)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(7, 4, gridView, context);
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
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(47)).setBackground(d);
                    ((Button)findViewById(R.id.setStart)).setBackgroundResource(R.color.startBlue);
                }
                else    // we are not setting start
                    solve.removeSpace(7, 5, gridView, context);
            }
        });


    }


}
