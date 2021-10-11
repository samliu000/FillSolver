package com.example.fillsolver;

/**
 * Point class to represent an individual cell in a grid
 */
public class Point {

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
