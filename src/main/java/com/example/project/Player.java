package com.example.project;

// Player is a sprite
public class Player extends Sprite {
    private int treasureCount;
    private int lives;
    private boolean won; // If the player has reached the trophy, this turns true

    // Constructor
    public Player(int x, int y) {
        super(x, y);
        lives = 2;
        treasureCount = 0;
        won = false;
    }

    //------Getters------
    public int getTreasureCount() {
        return treasureCount;
    }

    public int getLives() {
        return lives;
    }

    public boolean getWin() {
        return won;
    }
    //-------------------

    // These 2 methods are overriden to say Player instead of Sprite
    @Override
    public String getCoords() {
        return "Player:" + super.getCoords();
    }

    @Override
    public String getRowCol(int size) {
        return "Player:" + super.getRowCol(size);
    }


    // Moves player 1 place based on what direction they choose
    @Override
    public void move(String direction) {
        if (direction.equals("w")) {
            setY(getY() + 1); // up
        }
        else if (direction.equals("s")) {
            setY(getY() - 1); // down
        }
        else if (direction.equals("a")) {
            setX(getX() - 1); // left
        }
        else if (direction.equals("d")) {
            setX(getX() + 1); // right
        }
    }

    // This method determines what to do based on if the player runs into another object
    public void interact(int gridSize, String direction, int totalTreasures, Object obj) {
        if (obj instanceof Enemy) { // Player loses a life if they touch an enemy
            lives--;
        } else if (obj instanceof Trophy && treasureCount >= totalTreasures) { // If the player reaches the trophy and find all the treasures, they win
            won = true;
        } else if (obj instanceof Treasure && !(obj instanceof Trophy)) { // User touches a treasure
            treasureCount++;
        }
    }

    // Checks if the user can move in the direction they picked / if they move in a place outside the grid
    public boolean isValid(int gridSize, String direction) {
        int nextX = getX();
        int nextY = getY();

        // Finds out where your next location is
        if (direction.equals("w")) { 
            nextY++;
        }
        else if (direction.equals("s")) {
            nextY--;
        }
        else if (direction.equals("a")) {
            nextX--;
        }
        else if (direction.equals("d")) {
            nextX++;
        }

        // Makes sure you are still in the grid based on the direction picked
        return (nextX >= 0) && (nextX < gridSize) && (nextY >= 0) && (nextY < gridSize);
    }
}
