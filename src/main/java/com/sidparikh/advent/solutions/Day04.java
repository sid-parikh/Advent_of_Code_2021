package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Day04 extends Solution {

    private static final int MARK = -1;


    public Day04() {
        super(4);
    }

    @Override
    public String partOne() {
//        final List<String> input = List.copyOf(input);
//        int[] nums = Arrays.stream(input.remove(0).split(",")).filter((s) -> !s.isEmpty()).mapToInt(Integer::parseInt).toArray();
//        ArrayList<int[][]> boards = new ArrayList<int[][]>();
//        int i = -1;
//        int j = 0;
//        for (String s : input) {
//            if (s.isEmpty()) {
//                j = 0;
//                i++;
//                boards.add(new int[5][5]);
//            } else {
//                boards.get(i)[j] = Arrays.stream(s.split(" ")).filter((a) -> !a.isEmpty()).mapToInt(Integer::parseInt).toArray();
//                j++;
//            }
//        }
//
//        for (int n : nums) {
//
//            for (int[][] board : boards) {
//
//                for (i = 0; i < 5; i++) {
//                    for (j = 0; j < 5; j++) {
//                        if (board[i][j] == n) {
//                            board[i][j] = MARK;
//                        }
//                    }
//                }
//
//                if (isBoardWinner(board)) {
//                    return String.valueOf(getFinalScore(board, n));
//                }
//
//            }
//
//        }


        return null;
    }

    public static int getFinalScore(int[][] board, int lastNum) {
        int sum = 0;
        for (int[] row : board) {
            for (int n : row) {
                if (n != MARK) sum += n;
            }
        }
        return lastNum * sum;
    }

    public static boolean isBoardWinner(int[][] board) {
        int i = 0;
        int j = 0;
        // Row 1
        while (j < 5 && board[i][j] == MARK) j++;
        if (j == 5) return true;
        // Row 2
        i = 1;
        j = 0;
        while (j < 5 && board[i][j] == MARK) j++;
        if (j == 5) return true;
        // Row 3
        i = 2;
        j = 0;
        while (j < 5 && board[i][j] == MARK) j++;
        if (j == 5) return true;
        // Row 4
        i = 3;
        j = 0;
        while (j < 5 && board[i][j] == MARK) j++;
        if (j == 5) return true;
        // Row 5
        i = 4;
        j = 0;
        while (j < 5 && board[i][j] == MARK) j++;
        if (j == 5) return true;
        // Col 1
        i = 0;
        j = 0;
        while (i < 5 && board[i][j] == MARK) i++;
        if (i == 5) return true;
        // Col 2
        i = 0;
        j = 1;
        while (i < 5 && board[i][j] == MARK) i++;
        if (i == 5) return true;
        // Col 3
        i = 0;
        j = 2;
        while (i < 5 && board[i][j] == MARK) i++;
        if (i == 5) return true;
        // Col 4
        i = 0;
        j = 3;
        while (i < 5 && board[i][j] == MARK) i++;
        if (i == 5) return true;
        // Col 5
        i = 0;
        j = 4;
        while (i < 5 && board[i][j] == MARK) i++;
        if (i == 5) return true;
//        // Diagonal 1
//        i = 0;
//        j = 0;
//        while (i < 5 && board[i][j] == MARK) {i++; j++;}
//        if (i == 5) return true;
//        // Diagonal 2
//        i = 0;
//        j = 4;
//        while (i < 5 && board[i][j] == MARK) {i++; j--;}
//        if (i == 5) return true;

        return false;
    }

    @Override
    public String partTwo() {

        int[] nums = Arrays.stream(input.remove(0).split(",")).filter((s) -> !s.isEmpty()).mapToInt(Integer::parseInt).toArray();
        ArrayList<int[][]> boards = new ArrayList<int[][]>();
        int i = -1;
        int j = 0;
        for (String s : input) {
            if (s.isEmpty()) {
                j = 0;
                i++;
                boards.add(new int[5][5]);
            } else {
                boards.get(i)[j] = Arrays.stream(s.split(" ")).filter((a) -> !a.isEmpty()).mapToInt(Integer::parseInt).toArray();
                j++;
            }
        }

        for (int n : nums) {

            Iterator<int[][]> iterator = boards.iterator();
            while (iterator.hasNext()) {
                final int[][] board = iterator.next();
                for (i = 0; i < 5; i++) {
                    for (j = 0; j < 5; j++) {
                        if (board[i][j] == n) {
                            board[i][j] = MARK;
                        }
                    }
                }

                if (isBoardWinner(board)) {
                    if (boards.size() == 1) {
                        return String.valueOf(getFinalScore(board, n));
                    }
                    iterator.remove();
                }

            }

        }


        return null;
    }
}
