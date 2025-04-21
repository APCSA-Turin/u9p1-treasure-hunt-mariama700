package com.example.project;
import java.util.Scanner;

public class Game {
    // all instance varibales
    private Grid board;
    private Player hero;
    private Enemy[] villains;
    private Treasure[] loot;
    private Trophy goal;
    private int boardSize;

    // this method initializes the game
    public Game(int initialSize) {
        this.boardSize = initialSize;
        initialize();
        play();
    }

    // this method clears the screen
    public static void clearScreen() { //do not modify
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix-based (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // main loop for the game to run
    public void play() {
        Scanner scanner = new Scanner(System.in); // allow user to enter input
        boolean playAgain = true;

        // this allows the game to repeat
        while (playAgain == true) {
            // clear screen given by Ms.Turin
            try {
                Thread.sleep(100); // wait for 1/10 of second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clearScreen(); // clear the screen
            board.display(); // display grid

            // display the player's stats
            System.out.println("Treasure collected: " + hero.getTreasureCount());
            System.out.println("Lives remaining: " + hero.getLives());
            String input = scanner.nextLine().toLowerCase();

            // handles play movement
            if (input.equals("w") || input.equals("a") || input.equals("s") || input.equals("d")) {
                Sprite nextCell = null; // placeholder for nothing assigned yet

                if (hero.isValid(boardSize, input)) {
                    int posX = hero.getX();
                    int posY = hero.getY();

                    // update X and Y based on what key the user picks
                    if (input.equals("w")) {
                        posY++;
                    } else if (input.equals("s")) {
                        posY--;
                    } else if (input.equals("a")) {
                        posX--;
                    } else if (input.equals("d")) {
                        posX++;
                    }

                    nextCell = board.getGrid()[boardSize - 1 - posY][posX];

                // if there is an object in a cell that is moved to, interact with it
                hero.interact(boardSize, input, loot.length, nextCell);

                // cant touch the trophy until all treasurer is collected!
                if (!(nextCell instanceof Trophy) || hero.getTreasureCount() == loot.length) {
                        hero.move(input); // finally moved the player
                    }
                }

                // updates player position
                board.placeSprite(hero, input);
            }

            // now checks if the player won or lost
            if (hero.getLives() == 0 || hero.getWin()) {
                if (hero.getWin()) {
                    board.win();
                } else {
                    board.gameover();
                }

                System.out.println("Try again? y/n");
                playAgain = scanner.nextLine().toLowerCase().equals("y");

                if (playAgain) {
                    initialize(); // reset the board
                }
            }
        }
    }

    // set up a new game
    public void initialize() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please pick a level:");
        System.out.print("\n1. Easy, say 1");
        System.out.print("\n2. Medium, say 2");
        System.out.println("\n3. Hard, say 3");
        int level = scan.nextInt();
        scan.nextLine();

        if (level == 1) {
            boardSize = 5;
            villains = new Enemy[3];
            loot = new Treasure[2];
        } else if (level == 2) {
            boardSize = 10;
            villains = new Enemy[5];
            loot = new Treasure[3];
        } else if (level == 3) {
            boardSize = 15;
            villains = new Enemy[20];
            loot = new Treasure[5];
        }

        board = new Grid(boardSize);
        hero = new Player(0, 0);
        board.placeSprite(hero);

        goal = new Trophy(boardSize - 1, boardSize - 1);
        board.placeSprite(goal);

        // randomly place enemies
        for (int idx = 0; idx < villains.length; idx++) {
            boolean isPlaced = false;

            // keep generating random coordinates until we find an empty tile
            while (!isPlaced) {
                int xCoord = (int) (Math.random() * boardSize);
                int yCoord = (int) (Math.random() * boardSize);
                Sprite currentTile = board.getGrid()[boardSize - 1 - yCoord][xCoord];

                if (currentTile instanceof Dot) {
                    Enemy enemy = new Enemy(xCoord, yCoord);
                    board.placeSprite(enemy);
                    villains[idx] = enemy;
                    isPlaced = true;
                }
            }
        }

        // randomly place treasures
        for (int idx = 0; idx < loot.length; idx++) {
            boolean isPlaced = false;

            // keep generating random coordinates until we find an empty tile
            while (!isPlaced) {
                int xCoord = (int) (Math.random() * boardSize);
                int yCoord = (int) (Math.random() * boardSize);
                Sprite currentTile = board.getGrid()[boardSize - 1 - yCoord][xCoord];

                if (currentTile instanceof Dot) {
                    Treasure treasure = new Treasure(xCoord, yCoord);
                    board.placeSprite(treasure);
                    loot[idx] = treasure;
                    isPlaced = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        new Game(5);
    }
}
