/* Sid Parikh. Created on December 4, 2021, for Advent of Code */
package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Day 4: Giant Squid
 * <p>
 * We're gonna play bingo with a very scary giant squid. <br>
 * Star One: Find the board that's going to win first. Calculate its winning score. <br>
 * Star Two: A much more intelligent strategy: let the Squid win. Find the board that is guaranteed to lose.
 * Calculate its winning score.
 */
public class Day04 extends Solution {

    /**
     * Identifies a square of a bingo board as being crossed off.
     */
    private static final int MARK = -1;

    /**
     * Keeps track of all of the bingo boards, each one represented as a 2D integer array.
     */
    private ArrayList<int[][]> boards;

    /**
     * Keeps track of the numbers that are called in the order they are called.
     * Initialize to null.
     */
    private int[] nums = null;

    public Day04() {
        super(4);
    }

    /**
     * Calculates the final score of a bingo board, defined as the sum of the uncalled numbers times the last number to
     * be called (ie the number that made this board win).
     *
     * @param board   the bingo board to check
     * @param lastNum the last number to be called
     * @return the final score of the given {@code int[][] board}
     */
    public static int getFinalScore(int[][] board, int lastNum) {
        int sum = 0;
        for (int[] row : board) {
            for (int n : row) {
                if (n != MARK) sum += n;
            }
        }
        return lastNum * sum;
    }

    /**
     * Checks if a bingo board is a winner, as defined by having a full row or a full column of {@link #MARK}s.
     *
     * @param board the bingo board to check
     * @return true if the given {@code int[][] board} has a full row of column of {@link #MARK}s, false otherwise
     */
    public static boolean isBoardWinner(int[][] board) {
        int i;
        int j;

        // Check rows 1 through 5
        for (i = 0; i < 5; i++) {
            // Check each cell as long as they are marked
            j = 0;
            while (j < 5 && board[i][j] == MARK) {
                j++;
            }
            // If we made it to the end, it's a perfect row.
            if (j == 5) {
                return true;
            }
        }

        // Check columns 1 through 5
        for (j = 0; j < 5; j++) {
            // Check each cell as long as they are marked
            i = 0;
            while (i < 5 && board[i][j] == MARK) {
                i++;
            }
            // If we made it to the end, it's a perfect column.
            if (i == 5) {
                return true;
            }
        }

        return false;
    }

    /**
     * Parses the input and sets up {@link #nums} and {@link #boards}.
     */
    private void createBingoBoards() {
        // nums shouldn't and can't be recreated
        if (nums == null) {
            nums = Arrays.stream(input.remove(0).split(",")).filter((s) -> !s.isEmpty()).mapToInt(Integer::parseInt).toArray();
        }

        // Parse input to initialize boards. This should happen before part one and part two, to reset the marking.
        boards = new ArrayList<>();
        int i = 0;
        for (String s : input) {
            // An empty row is a gap between boards
            if (s.isEmpty()) {
                // Reset row counter
                i = 0;
                // Create a new board
                boards.add(new int[5][5]);
            } else {
                // Update the last board for normal rows
                boards.get(boards.size() - 1)[i] = Arrays.stream(s.split(" "))
                        .filter((a) -> !a.isEmpty())
                        .mapToInt(Integer::parseInt)
                        .toArray();
                i++;
            }
        }
    }

    /**
     * Find the board that will win first.
     *
     * @return the final score of the first winner (8136)
     */
    @Override
    public String partOne() {
        // Setup
        createBingoBoards();

        // For each number that is called
        for (int n : nums) {
            // Check each board
            for (int[][] board : boards) {
                // Check each square of the bingo board
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (board[i][j] == n) {
                            // If it's the called number, mark the square as called
                            board[i][j] = MARK;
                        }
                    }
                }
                // This board might have won! Check if it did, and if so, return its final score.
                if (isBoardWinner(board)) {
                    return String.valueOf(getFinalScore(board, n));
                }
            }
        }

        // Default condition; means something went wrong
        return null;
    }

    /**
     * Finds the board that will win last.
     *
     * @return the final score of the losing board when it finally wins (12738)
     */
    @Override
    public String partTwo() {
        // Setup
        createBingoBoards();

        // For each number that is called
        for (int n : nums) {
            // For each bingo board in a special way
            Iterator<int[][]> iterator = boards.iterator();
            while (iterator.hasNext()) {
                final int[][] board = iterator.next();
                // Check each square of the bingo board
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (board[i][j] == n) {
                            // If it's the called number, mark the square as called
                            board[i][j] = MARK;
                        }
                    }
                }

                // This board might have won! Check if it did.
                if (isBoardWinner(board)) {
                    // If it's the last board to win, then its our loser and therefore our winner
                    // Return its score
                    if (boards.size() == 1) {
                        return String.valueOf(getFinalScore(board, n));
                    }
                    // If its just a regular winner, kick it out. We only want real losers here.
                    iterator.remove();
                }
            }
        }

        // Default condition; means something went wrong
        return null;
    }
}
