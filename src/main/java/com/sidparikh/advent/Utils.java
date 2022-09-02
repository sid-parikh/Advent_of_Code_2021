package com.sidparikh.advent;

import java.util.List;

public class Utils {

    public static void print2dArray(int[][] arr) {
        for (int[] row : arr) {
            for (int n : row) {
                System.out.print(n);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void print2dArray(long[][] arr) {
        for (long[] row : arr) {
            for (long n : row) {
                System.out.print(n);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void print2dArray(char[][] arr) {
        for (char[] row : arr) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void print2dArray(Object[][] arr) {
        for (Object[] row : arr) {
            for (Object o : row) {
                System.out.print(o);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printList(List<?> list, String separator) {
        for (Object o : list) {
            System.out.print(o);
            System.out.print(separator);
        }
    }

    public static void printList(List<?> list) {
        printList(list, "\n");
    }

    public record Loc(int x, int y) {}

}
