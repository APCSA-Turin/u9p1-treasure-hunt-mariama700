package com.example.project;

// DO NOT DELETE ANY METHODS BELOW
public class Grid {
    private Sprite[][] board;
    private int gridSize;

    public Grid(int gridSize) {
        this.gridSize = gridSize;
        board = new Sprite[gridSize][gridSize];

        // fill the board with Dot objects to start
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                board[row][col] = new Dot(row, col);
            }
        }
    }

    // --- Getter methods ---
    public Sprite[][] getGrid() {
        return board;
    }

    public int getSize() {
        return gridSize;
    }

    public Sprite getSprite(int row, int col) {
        return board[row][col];
    }

    // places a sprite directly onto the board at its (x, y) coords
    public void placeSprite(Sprite sprite) {
        board[gridSize - 1 - sprite.getY()][sprite.getX()] = sprite;
    }

    // places a sprite and replaces the old spot with a Dot
    public void placeSprite(Sprite sprite, String direction) {
        Dot oldPosition = new Dot(0, 0);
        placeSprite(sprite); // place at new position

        // figure out where the sprite came *from* and put a Dot there
        int oldX = sprite.getX();
        int oldY = sprite.getY();

        if (direction.equals("w")) oldY--;
        else if (direction.equals("s")) oldY++;
        if (direction.equals("a")) oldX++;
        else if (direction.equals("d")) oldX--;

        oldPosition.setX(oldX);
        oldPosition.setY(oldY);
        placeSprite(oldPosition);
    }

    // prints the current board state to the terminal
    public void display() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Sprite current = board[row][col];
                if (current instanceof Dot) {
                    System.out.print("⬜");
                } else if (current instanceof Player) {
                    System.out.print("🦄");
                } else if (current instanceof Enemy) {
                    System.out.print("🦂");
                } else if (current instanceof Treasure && !(current instanceof Trophy)) {
                    System.out.print("🌈");
                } else if (current instanceof Trophy) {
                    System.out.print("🏆");
                }
            }
            System.out.println();
        }
    }

    // show board full of scorpions when player loses
    public void gameover() {
        StringBuilder output = new StringBuilder();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Sprite current = board[row][col];
                if (current instanceof Dot) {
                    output.append("🦂");
                } else if (current instanceof Player) {
                    output.append("🦄");
                }
            }
            output.append("\n");
        }
        System.out.println(output);
    }

    // show board full of rainbows when player wins
    public void win() {
        StringBuilder output = new StringBuilder();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Sprite current = board[row][col];
                if (current instanceof Dot) {
                    output.append("🌈");
                } else if (current instanceof Player) {
                    output.append("🦄");
                }
            }
            output.append("\n");
        }
        System.out.println(output);
    }
}
