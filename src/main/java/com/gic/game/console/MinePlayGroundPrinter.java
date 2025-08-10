package com.gic.game.console;


import com.gic.game.model.Cell;
import com.gic.game.service.MinePlayBord;

public class MinePlayGroundPrinter {
    /*
    Display the grid to user with col with number and row with letters
     */
    public static void displayMinefield(MinePlayBord minefield, boolean revealAll) {
        int size = minefield.getSize();
        System.out.println();
        System.out.print("  ");
        for (int c = 1; c <= size; c++) {
            System.out.print(c + " ");
        }
        System.out.println();
        for (int r = 0; r < size; r++) {
            System.out.print((char) ('A' + r) + " ");
            for (int c = 0; c < size; c++) {
                Cell cell = minefield.getPlayGridCell(r, c);
                if (revealAll) {
                    if (cell.isMine()) {
                        System.out.print("* ");
                    } else {
                        System.out.print(cell.getAdjacentMines() + " ");
                    }
                } else {
                    if (!cell.isRevealed()) {
                        System.out.print("_ ");
                    } else if (cell.isMine()) {
                        System.out.print("* ");
                    } else {
                        System.out.print(cell.getAdjacentMines() + " ");
                    }
                }
            }
            System.out.println();
        }
    }

}

