package com.gic.game.model;

 public class Cell {
    private boolean mine;
    private boolean revealed;
    private int adjacentMines;

    public Cell() {
        this.mine = false;
        this.revealed = false;
        this.adjacentMines = 0;
    }

    public boolean isMine() { return mine; }
    public boolean isRevealed() { return revealed; }
    public int getAdjacentMines() { return adjacentMines; }

    public void placeMine() { this.mine = true; }
    public void incrementAdjacentMines() { this.adjacentMines++; }
    public void reveal() { this.revealed = true; }

}
