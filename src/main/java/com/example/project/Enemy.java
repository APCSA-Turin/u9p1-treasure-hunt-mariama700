package com.example.project;

// Enemy sprite on the board
public class Enemy extends Sprite {
    public Enemy(int x, int y) {
        super(x, y);
    }

    @Override
    public String getCoords() {
        return "Enemy:" + super.getCoords();
    }

    @Override
    public String getRowCol(int size) {
        return "Enemy:" + super.getRowCol(size);
    }
}
