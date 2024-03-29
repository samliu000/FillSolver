package com.example.fillsolver;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    // timer that displays the solution to the user. On each tick of the clock, it will reveal the next step in the solution
    CountDownTimer countDownTimer;

    // current context
    Context context;

    // setting start state
    boolean settingStart = false;

    // start state row
    int startRow = -1;

    // start col
    int startCol = -1;

    /**
     * Method for starting the solution display. On each tick of the timer, the next step of the solution will be revealed
     */
    private void startTimer() {
        countDownTimer.cancel();
        countDownTimer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializations
        gridView = (GridLayout)findViewById(R.id.GameGrid);
        context = this;
        ImageView reloadButton = (ImageView)findViewById(R.id.reload);
        reloadButton.setVisibility(View.INVISIBLE);

        // set the background of all backgrounds to the default color
        for(int cell = 0; cell < 48; cell++) {
            Button cellButton = (Button) gridView.getChildAt(cell);
            cellButton.setBackgroundResource(android.R.drawable.btn_default);
        }

        // FillSolver Object that will the puzzle solving logic
        solve = new FillSolver(8,6);

        // initilization of timer that tells the timer to reveal the next step of the solution on each tick of the timer
        countDownTimer = new CountDownTimer(4800, 100) {
            int i = 1;
            public void onTick(long millisUntilFinished) {
                if(i>directions.size()){
                    return;
                }
                Point currPoint = directions.get(i - 1);
                int currRow = currPoint.getX();
                int currCol = currPoint.getY();
                int whereInGrid = (currRow * 6) + currCol;

                Button cellButton = (Button)gridView.getChildAt(whereInGrid);
                cellButton.setText("" + i);
                Drawable d = getResources().getDrawable(R.drawable.final_background);
                cellButton.setBackground(d);
                i++;
            }

            public void onFinish() {
                i=1;
            }
        };


        // User wants to START!
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // starting position hasn't been set
                if(startRow < 0 || startCol < 0) {
                    Toast failureMessage = Toast.makeText(getApplicationContext(),
                            "Please Set Starting Position", Toast.LENGTH_SHORT);
                    failureMessage.setGravity(Gravity.BOTTOM, 0, 0);
                    failureMessage.show();
                    return;
                }

                // solve
                solve.updateStart(startRow, startCol);

                if(solve.fillSolve(startRow, startCol)) {

                } else {
                    Toast failureMessage = Toast.makeText(getApplicationContext(),
                            "No Solution", Toast.LENGTH_SHORT);
                    failureMessage.setGravity(Gravity.BOTTOM, 0, 0);
                    failureMessage.show();

                    return;
                }

                // if it works
                directions = solve.getDirections();

                for(int i = 1; i < directions.size() + 1; i++) {

                    // get point location
                    Point currPoint = directions.get(i - 1);
                    int currRow = currPoint.getX();
                    int currCol = currPoint.getY();
                    int whereInGrid = (currRow * 6) + currCol;

                    // update text
                    Button cellButton = (Button)gridView.getChildAt(whereInGrid);
                }
                startTimer();

                ImageView reloadButton = (ImageView)findViewById(R.id.reload);
                reloadButton.setVisibility(View.VISIBLE);

            }

        });

        // logic for replaying the solution
        findViewById(R.id.reload).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                countDownTimer.onFinish();

                // refresh all the cells back to their default state
                for(int cell = 0; cell < 48; cell++) {

                    // do not want to paint over the starting point
                    int currStart = (startRow * 6) + startCol;
                    if(cell == currStart) {
                        continue;
                    }

                    // paint over all buttons (other than start)
                    Button cellButton = (Button) gridView.getChildAt(cell);
                    cellButton.setBackgroundResource(android.R.drawable.btn_default);
                    cellButton.setText("");
                }

                // start the solution animation
                startTimer();
            }

        });


        // reset!
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                
                // set all cells back to default sstate 
                for(int cell = 0; cell < 48; cell++) {
                    Button cellButton = (Button) gridView.getChildAt(cell);
                    cellButton.setBackgroundResource(android.R.drawable.btn_default);
                    cellButton.setText("");
                }
                Drawable d = getResources().getDrawable(R.drawable.og_button);
                ((Button)findViewById(R.id.setStart)).setBackground(d);
                solve = new FillSolver(8,6);

                startRow = -1;
                startCol = -1;

                // reset timer
                countDownTimer.cancel();
                countDownTimer.onFinish();

                ImageView reloadButton = (ImageView)findViewById(R.id.reload);
                reloadButton.setVisibility(View.INVISIBLE);
            }


        });

        // user wants to set the starting position
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);

                    startRow = 0;
                    startCol = 0;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(0)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button)findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 1;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(1)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 2;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(2)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 3;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(3)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 4;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(4)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 0;
                    startCol = 5;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(5)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 1;
                    startCol = 0;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(6)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 1;
                    startCol = 1;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(7)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 1;
                    startCol = 2;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(8)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 1;
                    startCol = 3;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(9)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 1;
                    startCol = 4;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(10)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 1;
                    startCol = 5;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(11)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 2;
                    startCol = 0;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(12)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 2;
                    startCol = 1;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(13)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 2;
                    startCol = 2;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(14)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 2;
                    startCol = 3;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(15)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 2;
                    startCol = 4;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(16)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 2;
                    startCol = 5;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(17)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 3;
                    startCol = 0;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(18)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 3;
                    startCol = 1;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(19)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 3;
                    startCol = 2;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(20)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 3;
                    startCol = 3;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(21)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 3;
                    startCol = 4;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(22)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 3;
                    startCol = 5;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(23)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 4;
                    startCol = 0;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(24)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 4;
                    startCol = 1;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(25)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 4;
                    startCol = 2;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(26)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 4;
                    startCol = 3;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(27)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 4;
                    startCol = 4;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(28)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 4;
                    startCol = 5;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(29)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 5;
                    startCol = 0;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(30)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 5;
                    startCol = 1;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(31)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 5;
                    startCol = 2;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(32)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 5;
                    startCol = 3;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(33)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 5;
                    startCol = 4;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(34)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 5;
                    startCol = 5;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(35)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 6;
                    startCol = 0;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(36)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 6;
                    startCol = 1;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(37)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 6;
                    startCol = 2;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(38)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 6;
                    startCol = 3;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(39)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 6;
                    startCol = 4;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(40)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 6;
                    startCol = 5;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(41)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 7;
                    startCol = 0;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(42)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 7;
                    startCol = 1;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(43)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 7;
                    startCol = 2;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(44)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 7;
                    startCol = 3;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(45)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 7;
                    startCol = 4;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(46)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
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
                    if(startRow >=0 && startCol >= 0)
                        ((Button)gridView.getChildAt((startRow * 6) + startCol)).setBackgroundResource(android.R.drawable.btn_default);
                    startRow = 7;
                    startCol = 5;
                    settingStart = false;
                    Drawable d = getResources().getDrawable(R.drawable.set_start_color);
                    ((Button)gridView.getChildAt(47)).setBackground(d);
                    Drawable a = getResources().getDrawable(R.drawable.og_button);
                    ((Button) findViewById(R.id.setStart)).setBackground(a);
                }
                else    // we are not setting start
                    solve.removeSpace(7, 5, gridView, context);
            }
        });

    }


}
