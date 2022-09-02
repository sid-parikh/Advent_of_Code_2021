package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.io.IOException;

public class Day13 extends Solution {

    public Day13() throws IOException {
        super(13);
    }

    @Override
    public String partOne() {
        boolean[][] dots = new boolean[1500][1500];
        int i = 0;
        int maxX = 0;
        int maxY = 0;
        for (; i < input.size(); i++) {
            String line = input.get(i);
            if (line.isEmpty()) {
                break;
            }
            String[] coords = line.split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            dots[x][y] = true;
        }
        boolean[][] newArr = new boolean[maxX + 1][maxY + 1];
        for (int r = 0; r <= maxX; r++) {
            System.arraycopy(dots[r], 0, newArr[r], 0, maxY + 1);
        }
        dots = newArr;
        String firstFold = input.get(i + 1);
        firstFold = firstFold.substring(11);
        boolean isX = firstFold.charAt(0) == 'x';
        int val = Integer.parseInt(firstFold.substring(2));

        dots = foldX(dots, val);

        int count = 0;
        for (i = 0; i < dots.length; i++) {
            for (int j = 0; j < dots[0].length; j++) {
                count += dots[i][j] ? 1 : 0;
            }
        }

        return String.valueOf(count);
    }

    private static boolean[][] foldX(boolean[][] dots, int val) {
        final int L = dots[0].length;
        boolean[][] answer;
        if (val > dots.length / 2 - 1) {
            answer = new boolean[val][L];
            for (int i = 1; val + i < dots.length; i++) {
                for (int j = 0; j < L; j++) {
                    answer[val - i][j] = dots[val - i][j] || dots[val + i][j];
                }
            }
            for (int i = 0; i <= (2 * val) - dots.length; i++) {
                System.arraycopy(dots[i], 0, answer[i], 0, L);
            }
        } else {
            answer = new boolean[dots.length - val - 1][L];
            final int offset = dots.length - (2 * val) - 1;
            for (int i = 1; val + i < dots.length; i++) {
                for (int j = 0; j < L; j++) {
                    answer[val - i + offset][j] = dots[val - i][j] || dots[val + i][j];
                }
            }
        }
        return answer;
    }


    private static boolean[][] foldY(boolean[][] dots, int val) {
        final int L = dots.length;
        boolean[][] answer;
        if (val > dots[0].length / 2 - 1) {
            answer = new boolean[L][val];

            for (int i = 0; i < L; i++) {
                for (int j = 1; val + j < dots[0].length; j++) {
                    answer[i][val - j] = dots[i][val - j] || dots[i][val + j];
                }
            }

            for (int i = 0; i < L; i++) {
                if ((2 * val) - dots[0].length + 1 >= 0)
                    System.arraycopy(dots[i], 0, answer[i], 0, (2 * val) - dots[0].length + 1);
            }
        } else {
            answer = new boolean[L][dots[0].length - val];

            final int offset = dots[0].length - (2 * val) - 1;

            for (int i = 0; i < L; i++) {
                for (int j = 1; j + val < dots[0].length; j++) {
                    answer[i][val - j + offset] = dots[i][val - j] || dots[i][val + j];
                }
            }

        }
        return answer;
    }

    @Override
    public String partTwo() {
        boolean[][] dots = new boolean[1500][1500];
        int i = 0;
        int maxX = 0;
        int maxY = 0;
        for (; i < input.size(); i++) {
            String line = input.get(i);
            if (line.isEmpty()) {
                break;
            }
            String[] coords = line.split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            dots[x][y] = true;
        }
        boolean[][] newArr = new boolean[maxX + 1][maxY + 1];
        for (int r = 0; r <= maxX; r++) {
            System.arraycopy(dots[r], 0, newArr[r], 0, maxY + 1);
        }
        dots = newArr;
        for (i = i + 1; i < input.size(); i++) {
            String intstructions = input.get(i);
            intstructions = intstructions.substring(11);
            boolean isX = intstructions.charAt(0) == 'x';
            int val = Integer.parseInt(intstructions.substring(2));

            if (isX) {
                dots = foldX(dots, val);
            } else {
                dots = foldY(dots, val);
            }

        }


        int count = 0;
//        for (i = 0; i <= maxX; i++) {
//            for (int j = 0; j <= maxY; j++) {
//                count += dots[i][j] ? 1 : 0;
//            }
//        }

        StringBuilder sb = new StringBuilder("\n");
        for (i = 0; i < dots[0].length; i++) {
            for (boolean[] dot : dots) {
                sb.append(dot[i] ? '#' : '.');
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
