package com.example.fillsolver;

import java.util.Scanner;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Queue;
import java.lang.Math;

public class FillSolver {
    private int[][] grid;
    private int numRow;
    private int numCol;
    private int startRow;
    private int startCol;
    private Vector<Point> directions;

    /**
     * Point Class which represents a cell on the grid
     */
    private static class Point{
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
        public String toString() {
            return "(" + x + "," + y + ")";
        }
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
    public FillSolver(int row, int col, int startRow, int startCol) {
        numRow = row;
        numCol = col;
        this.startRow = startRow;
        this.startCol = startCol;
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

    /**
     * Uses recursive backtracking to explore all possible paths
     * @param currentRow: current row
     * @param currentCol: current column
     * @return: true or false based on whether the puzzle is solved or not
     */
    public boolean solveTracer(int currentRow, int currentCol) {
        //base case
        if(gridSolved()) {
            return true;
        }
        //recursive case
        else {
            //gets possible moves for current block
            Queue<Point> possibleMoves = possibleMoves(currentRow, currentCol);
            int size = possibleMoves.size();
            for(int i = 0; i < size; i++) {
                Point nextSpot = possibleMoves.poll();
                int nextRow = nextSpot.getX();
                int nextCol = nextSpot.getY();
                //modify grid and list of directions
                grid[nextRow][nextCol] = 0;
                directions.add(nextSpot);
                //explore possible paths
                if(solveTracer(nextRow, nextCol)) {
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
     * Converts a vector of points into a vector of strings indicating directional movement
     * @param pointsList: vector to convert
     * @param startRow: starting row
     * @param startCol: starting column
     * @return: a vector of strings indicating directional movement
     */
    public static Vector<String> pointToDirectionConverter(Vector<Point> pointsList, int startRow, int startCol){
        Vector<String> returnList = new Vector<String>();
        //keeps track of the current block
        Point current = new Point(startRow, startCol);
        for(int i = 0; i < pointsList.size(); i++) {
            Point nextPosition = pointsList.elementAt(i);
            //change in row
            if(nextPosition.getX() > current.getX()) {
                returnList.add("Down");
            }
            //change in row
            else if(nextPosition.getX() < current.getX()) {
                returnList.add("Up");
            }
            //change in column
            else if(nextPosition.getY() > current.getY()) {
                returnList.add("Right");
            }
            //change in column
            else {
                returnList.add("Left");
            }
            //makes the next position the current position
            current = nextPosition;
        }
        return returnList;
    }

    /**
     * Prints out the contents of a 2D array
     * @param grid: 2D array you want to print
     * @param startRow: starting row
     * @param startCol: starting column
     */
    public static void printSteps(int[][] grid) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                //formatting (spacing off if number of steps in directions > 99 steps)
                if(grid[i][j] < 10) {
                    System.out.print(" ");
                }
                System.out.printf("%s", grid[i][j]+ " ");
            }
            System.out.println();
        }
    }

    /**
     * Prints arrows
     * @param grid: the grid to print out
     */
    public static void printArrows(String[][] grid) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == null) {
                    System.out.printf("%s",  "0 ");
                } else {
                    System.out.printf("%s", grid[i][j]+ " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Uses a list of directions to fill a 2D array with arrows representing directional movement
     * @param listDirections: a vector of strings representing directional movement
     * @return: a 2D array of arrows
     */
    public String[][] convertToArrows(Vector<String> listDirections) {
        String[][] gridArrows = new String[numRow][numCol];
        gridArrows[startRow][startCol] = "X";
        int currentRow = startRow;
        int currentCol = startCol;
        for(int i = 0; i < listDirections.size(); i++) {
            if(listDirections.elementAt(i).equals("Right")) {
                gridArrows[currentRow][currentCol + 1] = "→";
                currentCol++;
            } else if(listDirections.elementAt(i).equals("Left")) {
                gridArrows[currentRow][currentCol - 1] = "←";
                currentCol--;
            } else if(listDirections.elementAt(i).equals("Up")) {
                gridArrows[currentRow - 1][currentCol] = "↑";
                currentRow--;
            } else {
                gridArrows[currentRow + 1][currentCol] = "↓";
                currentRow++;
            }
        }
        return gridArrows;
    }
    /**
     * Gives user the option to mark spaces
     * @param rowR: selected row
     * @param colR: selected column
     */
    public void removeSpace(int rowR, int colR) {
        grid[rowR][colR] = 0;
    }
}
