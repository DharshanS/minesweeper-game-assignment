package com.gic.game;

import com.gic.game.model.Cell;
import com.gic.game.service.MinePlayBord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinePlayBoardTest {


    @BeforeEach
    void setup() {
        // Initial setup if required
        // MinePlayBord playBord = new MinePlayBord(3, 0);
    }


    @Test
    void testMinePlacement() {
        MinePlayBord playBord = new MinePlayBord(3, 0);
        playBord.getPlayGridCell(0, 0).placeMine();
        assertTrue(playBord.getPlayGridCell(0, 0).isMine());
    }

    @Test
    void testRevealCellMine() {
        MinePlayBord playBord = new MinePlayBord(3, 0);
        playBord.getPlayGridCell(0, 0).reveal();
        assertTrue(playBord.getPlayGridCell(0, 0).isRevealed());
    }


    @Test
    void testMinePlacementAndAdjacentCounts() {
        MinePlayBord playBord = new MinePlayBord(3, 2);
        assertEquals(2, playBord.getTotalMines());
    }


    @Test
    void testRevealCellAndNonMine() {
        MinePlayBord playBord = new MinePlayBord(3, 0);
        playBord.getPlayGridCell(1, 1).reveal();
        boolean hitMine = playBord.getPlayGridCell(1, 1).isMine();
        assertFalse(hitMine);
        assertTrue(playBord.getPlayGridCell(1, 1).isRevealed());
    }


    @Test
    void testMinePlacementCount() {
        MinePlayBord playBord = new MinePlayBord(3, 3);
        int mineCount = 0;
        for (Cell[] row : playBord.getPlayGrid()) {
            for (Cell cell : row) {
                if (cell.isMine()) mineCount++;
            }
        }
        ;
        assertEquals(3, mineCount);
    }

    @Test
    void testRevealCell() {
        MinePlayBord playBord = new MinePlayBord(3, 0);
        for (Cell[] row : playBord.getPlayGrid()) {
            for (Cell cell : row) {
                assertFalse(cell.isRevealed());
            }
        }
    }

    @Test
    void testRevealCountCheck() {
        MinePlayBord playBord = new MinePlayBord(3, 0);
        playBord.getPlayGridCell(1, 1).reveal();
        playBord.getPlayGridCell(1, 2).reveal();
        int count = 0;

        for (int r = 0; r < playBord.getSize(); r++) {
            for (int c = 0; c < playBord.getSize(); c++) {
                if (playBord.getPlayGridCell(r, c).isRevealed()) {
                    count++;
                }
            }
        }

        assertEquals(2, count);
    }


    @Test
    void testCalculateAdjacencyCountMines() {
        MinePlayBord playBord = new MinePlayBord(3, 0);
        playBord.getPlayGridCell(1, 1).placeMine();
        playBord.calculateAdjacency();
        assertEquals(1, playBord.getPlayGridCell(0, 1).getAdjacentMines());
        assertEquals(1, playBord.getPlayGridCell(1, 2).getAdjacentMines());
        assertEquals(1, playBord.getPlayGridCell(2, 1).getAdjacentMines());

    }


    @Test
    void testCalculateAdjacencyMinesZeroScenario() {
        MinePlayBord playBord = new MinePlayBord(3, 0);
        playBord.getPlayGridCell(0, 1).placeMine();
        playBord.calculateAdjacency();
        assertEquals(0, playBord.getPlayGridCell(2, 1).getAdjacentMines());
    }

    @Test
    void testCalculateAdjacencyCombinationScenario() {
        MinePlayBord playBord = new MinePlayBord(3, 0);
        playBord.getPlayGridCell(1, 1).placeMine();
        playBord.getPlayGridCell(2, 2).placeMine();
        playBord.calculateAdjacency();
        assertEquals(2, playBord.getPlayGridCell(2, 1).getAdjacentMines());
        assertEquals(1, playBord.getPlayGridCell(0, 0).getAdjacentMines());
    }


    @Test
    void testWinCondition() {
        MinePlayBord playBord = new MinePlayBord(3, 1);

        int row = 0;
        int col = 0;
        for (int r = 0; r < playBord.getSize(); r++) {
            for (int c = 0; c < playBord.getSize(); c++) {
                if (playBord.getPlayGridCell(r, c).isMine()) {
                    row = r;
                    col = c;
                }
            }
        }

        for (int r = 0; r < playBord.getSize(); r++) {
            for (int c = 0; c < playBord.getSize(); c++) {
                if (!(row == r && col == c)) {
                    playBord.getPlayGridCell(r, c).reveal();
                }
            }
        }
        assertTrue(playBord.allSafeCellsRevealed());

    }


    @Test
    void testLosingCondition() {
        MinePlayBord playBord = new MinePlayBord(3, 1);
        int row = 0;
        int col = 0;
        // find the mine placed position
        for (int r = 0; r < playBord.getSize(); r++) {
            for (int c = 0; c < playBord.getSize(); c++) {
                if (playBord.getPlayGridCell(r, c).isMine()) {
                    row = r;
                    col = c;
                }
            }
        }

        //hitting the mine place
        for (int r = 0; r < playBord.getSize(); r++) {
            for (int c = 0; c < playBord.getSize(); c++) {
                if (row == r && col == c) {
                    playBord.getPlayGridCell(r, c).reveal();
                }
            }
        }
        assertFalse(playBord.allSafeCellsRevealed());
    }

}
