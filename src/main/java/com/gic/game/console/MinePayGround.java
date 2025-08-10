package com.gic.game.console;


import com.gic.game.model.Cell;
import com.gic.game.service.MinePlayBord;
import com.gic.game.util.MineValidator;

import java.util.Scanner;

public class MinePayGround {

    private final Scanner scanner;
    private MinePlayBord minePlayBoard;

    public MinePayGround() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to Minesweeper!");
        while (true) {
            int size = promptGridSize();
            int maxMines = (int) Math.floor(size * size * 0.35);
            int mines = promptMinesCount(maxMines);

            minePlayBoard = new MinePlayBord(size, mines);

            System.out.print("Here is your minefield:");
            MinePlayGroundPrinter.displayMinefield(minePlayBoard, false);

            boolean gameOver = false;
            while (!gameOver) {
                int[] cords = promptMove();
                int row = cords[0];
                int col = cords[1];

                boolean mineHit = minePlayBoard.revealCell(row, col);
                Cell revealedCell = minePlayBoard.getPlayGridCell(row, col);

                if (mineHit) {
                    System.out.println("Oh no, you detonated a mine! Game over.");
                    MinePlayGroundPrinter.displayMinefield(minePlayBoard, true);
                    gameOver = true;
                } else {
                    System.out.printf("This square contains %d adjacent mine%s.%n",
                            revealedCell.getAdjacentMines(),
                            revealedCell.getAdjacentMines() == 1 ? "" : "s");
                    System.out.println("Here is your updated minefield:");
                    MinePlayGroundPrinter.displayMinefield(minePlayBoard, false);

                    if (minePlayBoard.allSafeCellsRevealed()) {
                        System.out.println("Congratulations, you have won the game!");
                        gameOver = true;
                    }
                }
            }
            System.out.println("\n Press any key to play again ! OR Type 'exit' exit from the game");
            scanner.nextLine();
            String line = scanner.nextLine();
            if (line.trim().equalsIgnoreCase("exit")) {
                break;

            }
        }
        System.out.println("Thanks for playing Minesweeper!");
    }

    /**
     * get grid size and validate the user inputs
     */
    private int promptGridSize() {
        int gridSize = 0;
        while (true) {
            System.out.println("Enter the size of the grid (e.g. 4 for a 4x4 grid):");
            try {
                String in = scanner.nextLine();
                gridSize = Integer.parseInt(in);
                if (gridSize < 2 || gridSize > 26) {
                    System.out.println("Please enter a size between 2 and 26.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please try again.");
            }
        }
        return gridSize;
    }

    /**
     * get the mine count and validate maximum %
     */
    private int promptMinesCount(int maxMines) {
        int mines = 0;
        while (true) {
            System.out.println("Enter the number of mines to place on the grid (maximum is 35% of the total squares):");

            try {
                String in = scanner.nextLine();
                mines = Integer.parseInt(in);
                if (mines < 1 || mines > maxMines) {
                    System.out.printf("Please enter a number between 1 and %d.%n", maxMines);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please try again.");
            }
        }
        return mines;
    }

    /**
     * Prompts user for a move like A1 and validate and returns row, col.
     */
    private int[] promptMove() {
        int size = minePlayBoard.getSize();
        while (true) {
            System.out.println("Select a square to reveal (e.g. A1): ");
            String input = scanner.next().trim().toUpperCase();

            if (MineValidator.isValidInputSquareToReveal(input)) {
                System.out.println("Invalid input format. Please use format like A1.");
                continue;
            }

            char rowChar = input.charAt(0);
            if (MineValidator.isValidCharForSquareToReveal(rowChar, size)) {
                System.out.println("Invalid row. Please enter a letter between A and " + (char) ('A' + size - 1));
                continue;
            }

            String colStr = input.substring(1);
            int col;
            try {
                col = Integer.parseInt(colStr);
                if (col < 1 || col > size) {
                    System.out.println("Invalid column. Please enter a number between 1 and " + size);
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid column number. Please try again.");
                continue;
            }

            int row = rowChar - 'A';
            col = col - 1;

            Cell cell = minePlayBoard.getPlayGridCell(row, col);
            if (cell.isRevealed()) {
                System.out.println("This square is already revealed. Please pick another one.");
                continue;
            }
            return new int[]{row, col};
        }
    }


}

