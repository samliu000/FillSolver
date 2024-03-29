package com.example.fillsolver;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import androidx.gridlayout.widget.GridLayout;

public class FillSolver {

    // grid representing the game board
    private int[][] grid;

    // the number of columns and rows
    private int numRow;
    private int numCol;

    // the starting position of the gameboard
    private int startRow;
    private int startCol;

    // an array containing a sequence of points representing the steps of the solution
    private Vector<Point> directions;

    // a debug variable to keep track of how many iterations we have gone through
    private int visits;

    /**
     * Returns number of columns
     * @return number of columns
     */
    public int getNumCols() {
        return numCol;
    }

    /**
     * Returns the directions
     * @return vector of points
     */
    public Vector<Point> getDirections() {
        return directions;
    }

    /**
     * Constructs a grid of ones with the exception of the starting position, which is zero
     * 1: Unmarked path
     * 0: Marked path
     * Program will not consider paths that are 0 to be a possible move
     * @param row: number of rows in the grid
     * @param col: number of columns in the grid
     * @param startRow: row component of starting position
     * @param startCol: column compoenent of starting position
     */
    public FillSolver(int row, int col) {
        numRow = row;
        numCol = col;
        this.startRow = startRow;
        this.startCol = startCol;
        visits = 0;

        //construct grid
        grid = new int[row][col];

        //a list of points that represent the solution to the tracer map
        directions = new Vector<Point>();

        //fills map with 1's, indicating untraveled paths. Traveled paths will be marked with 0
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                grid[i][j] = 1;
            }
        }
    }

    /**
     * Method to set starting cell in grid to 0
     * @param startCol column we are starting with
     * @param startRow row we are starting with
     */
    public void updateStart(int startRow, int startCol) {
        //marks starting point
        grid[startRow][startCol] = 0;
    }
    /**
     * Used in each recursive step to determine if puzzle has been solved
     * @return: a boolean of whether the grid has been solved (completely filled with 0's)
     */
    public boolean gridSolved() {
        for(int i = 0; i < numRow; i++) {
            for(int j = 0; j < numCol; j++) {
                //if a block is 1, then the puzzle is not solved yet
                if(grid[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Lists all possible moves in cardinal directions of the current block
     * @param currentRow: current row
     * @param currentCol: current column
     * @return: a Queue of Points that list all possible moves from the current position
     */
    public Queue<Point> possibleMoves(int currentRow, int currentCol){
        //declares/initializes a queue of points
        Queue<Point> directionList = new LinkedList<Point>();
        for(int r = currentRow - 1; r < currentRow + 2 && r < numRow; r++){
            for(int c = currentCol - 1; c < currentCol + 2 && c < numCol; c++){
                //only considers the neighbors in cardinal directions
                if((Math.abs(currentRow - r) + Math.abs(currentCol - c)) == 1 && r >= 0 && c >= 0){
                    //checks if block in question is marked
                    if(grid[r][c] != 0) {
                        Point currentPoint = new Point(r ,c);
                        directionList.add(currentPoint);
                    }

                }
            }
        }
        return directionList;
    }

    public int getVisits() {
        return visits;
    }
    /**
     * Uses recursive backtracking to explore all possible paths
     * @param currentRow: current row
     * @param currentCol: current column
     * @return: true or false based on whether the puzzle is solved or not
     */
    public boolean fillSolve(int currentRow, int currentCol) {
        
        // increment our debug variable
        visits++;

        // base case: yay we have a solution!
        if(gridSolved()) {
            return true;
        }
        // recursive case: we still have work to do :(
        else {

            //gets possible moves for current block
            Queue<Point> possibleMoves = possibleMoves(currentRow, currentCol);
            int size = possibleMoves.size();

            // iterate through possible moves
            for(int i = 0; i < size; i++) {

                // find the next spot to go to
                Point nextSpot = possibleMoves.poll();
                int nextRow = nextSpot.getX();
                int nextCol = nextSpot.getY();

                //modify grid and list of directions
                grid[nextRow][nextCol] = 0;
                directions.add(nextSpot);
                //explore possible paths
                if(fillSolve(nextRow, nextCol)) {
                    return true;
                }

                //unmodify grid and list of directions
                grid[nextRow][nextCol] = 1;
                directions.remove(directions.size() - 1);

            }
        }
        return false;
    }

    /**
     * Gives user the option to mark spaces
     * @param rowR: selected row
     * @param colR: selected column
     */
    public void removeSpace(int rowR, int colR, GridLayout gameGrid, Context context) {
        Button cellButton = (Button) gameGrid.getChildAt((rowR * numCol) + colR);

        // if the cell is marked, reset it; otherwise, mark it as an obstacle
        if(grid[rowR][colR] == 0) {

            // set state
            grid[rowR][colR] = 1;

            //change color
            cellButton.setBackgroundResource(android.R.drawable.btn_default);
        } 
        else {

            // set state
            grid[rowR][colR] = 0;

            // change color
            Drawable d = context.getResources().getDrawable(R.drawable.button);
            cellButton.setBackground(d);
        }
    }
}
