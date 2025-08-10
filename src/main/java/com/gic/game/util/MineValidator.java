package com.gic.game.util;

public class MineValidator {

    public static boolean isValidCharForSquareToReveal(char rowChar, int size) {
        return rowChar < 'A' || rowChar >= ('A' + size);
    }

    public static boolean isValidInputSquareToReveal(String input) {
        return input.length() < 2 || input.length() > 3;
    }

    public static boolean inBounds(int r, int c,int size) {
        return r >= 0 && c >= 0 && r < size && c < size;
    }
}
