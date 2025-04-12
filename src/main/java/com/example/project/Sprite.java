package com.example.project;

public class Sprite {
    private int x, y;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // ------Getters & Setters------
    public int getX() {
        return x; 
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }
    //------------------------------

    // Returns coordinates of sprite
    // Returns in (x,y) format
    public String getCoords() {
        return "(" + x + "," + y + ")";
    }

    // Returns row and column of the sprite
    // Returns in [row][col] format
    public String getRowCol(int size) {
        return "[" + (size - y - 1) + "][" + x + "]";
    }

    // leave this empty
    public void move(String direction) {

    }

    // leave this empty
    public void interact() {

    }
}
