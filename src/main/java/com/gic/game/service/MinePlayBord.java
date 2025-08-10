package com.gic.game.service;


import com.gic.game.model.Cell;
import com.gic.game.util.MineValidator;

import java.util.Random;

import static com.gic.game.constants.GameConstants.*;


public class MinePlayBord {
    private final int size;

    public int getSize() {
        return size;
    }

    public int getTotalMines() {
        return totalMines;
    }

    private final int totalMines;
    private final Cell[][] playGrid;

    public MinePlayBord(int size, int totalMines) {
        this.size = size;
        this.totalMines = totalMines;
        this.playGrid = new Cell[size][size];
        for (int rowCell = 0; rowCell < size; rowCell++) {
            for (int colCell = 0; colCell < size; colCell++) {
                playGrid[rowCell][colCell] = new Cell();
            }
        }
        placeMinesInGrid();
        calculateAdjacency();

    }

    /**
     * Get the mines form user and place the mine in random position of the grids
     */
    public void placeMinesInGrid() {
        Random randMinePos = new Random();
        int placedCount = 0;
        while (placedCount < totalMines) {
            int row = randMinePos.nextInt(size);
            int col = randMinePos.nextInt(size);
            if (!playGrid[row][col].isMine()) {
                playGrid[row][col].placeMine();
                placedCount++;
            }
        }
    }

    /**
     * Calculates and updates the number of adjacent mines for each non-mine cell
     * in the Minesweeper grid.

     * This method iterates over all cells in the game grid. For each cell that is not a mine,
     * it checks its surrounding cells (up to 8 possible adjacent positions: top, bottom,
     * left, right, and the four diagonals) to count how many contain mines.

     * The method uses {@code SURROUNDING_CELLS_ROW} and {@code SURROUNDING_CELLS_COL}
     * arrays to determine the relative positions of all possible adjacent cells, and
     * {@link MineValidator#inBounds(int, int, int)} to ensure positions are within the grid boundaries.

     * For example:
     * [A][B][C]
     * [D][X][E]
     * [F][G][H]
     * The cell marked 'X' has 8 adjacent positions (A–H). Edge and corner cells
     * will have fewer adjacent cells due to boundary limits.

     * After counting, the number of adjacent mines is stored in the cell so that
     * it can be displayed during gameplay.
     */
    public void calculateAdjacency() {

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (playGrid[row][col].isMine())
                    continue;
                int count = 0;
                for (int pos = 0; pos < SURROUNDING_CELLS; pos++) {
                    int rowPosition = row + SURROUNDING_CELLS_ROW[pos];
                    int colPosition = col + SURROUNDING_CELLS_COL[pos];
                    if (MineValidator.inBounds(rowPosition, colPosition, size) && playGrid[rowPosition][colPosition].isMine())
                        count++;
                }
                for (int index = 0; index < count; index++) {
                    playGrid[row][col].incrementAdjacentMines();
                }
            }
        }
    }

    /**
     * Reveals the cell at the given row and column in the Minesweeper grid.

     * If the cell is out of bounds or already revealed, no further action is taken.
     * If the cell contains a mine, the method returns {@code false} to indicate the game is lost.
     * If the cell has zero adjacent mines, this method recursively reveals all surrounding cells
     * (flood fill behavior).

     *
     * @param row the row index of the cell to reveal
     * @param col the column index of the cell to reveal
     * @return {@code true} if the revealed cell is safe (no mine), {@code false} if a mine is revealed
     */
    public boolean revealCell(int row, int col) {
        if (!MineValidator.inBounds(row, col, size) || playGrid[row][col].isRevealed())
            return true;

        playGrid[row][col].reveal();
        if (playGrid[row][col].isMine())
            return false;

        if (playGrid[row][col].getAdjacentMines() == 0) {
            for (int i = 0; i < 8; i++) {
                revealCell(row + SURROUNDING_CELLS_ROW[i], col + SURROUNDING_CELLS_COL[i]);
            }
        }
        return true;
    }

    /**
     * Checks if all safe (non-mine) cells on the board have been revealed.

     This method counts how many safe cells have been revealed so far and compares
     * it to the total number of safe cells (total cells minus total mines).
     * If the count matches, it means the player has revealed all safe cells,
     * which typically signifies a win condition in Minesweeper.

     * @return true if all safe cells are revealed, false otherwise.
     */
    public boolean allSafeCellsRevealed() {
        int revealedCount = 0;
        int totalSafe = size * size - totalMines;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (!playGrid[row][col].isMine() && playGrid[row][col].isRevealed()) {
                    revealedCount++;
                }
            }
        }
        return revealedCount == totalSafe;
    }

    public Cell[][] getPlayGrid() {
        return playGrid;
    }

    public Cell getPlayGridCell(int row, int col) {
        return playGrid[row][col];
    }


}
