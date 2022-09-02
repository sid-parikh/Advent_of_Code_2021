package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day17 extends Solution {
    int totalY = 0;
    int totalX = 0;
    public Day17() throws IOException {
        super(17);
    }

    @Override
    public String partOne() {

        List<String[]> allPairs = input.stream().map((s) -> s.split("\\s+")).toList();
        List<String> pairsBetter = new ArrayList<>();
        for (String[] arr : allPairs) {
            Collections.addAll(pairsBetter, arr);
        }

        Collections.sort(pairsBetter);
        printList(pairsBetter);


        List<Integer> ref;
        for (int i = -1000; i < 1000; i++) {
            ref = allYPos(i, new ArrayList<Integer>(), 0);
            int j = ref.size() - 1;
            for (; j >= 0; j--) {
                if (ref.get(j) >= -260 && ref.get(j) <= -200) {
                    totalY++;
                    break;
                }
            }
        }
        return String.valueOf(totalY);
    }

    private List<Integer> allYPos(int vy, List<Integer> pos, int y) {
        y += vy;
        pos.add(y);
        vy--;
        if (y < -260) {
            return pos;
        }
        return allYPos(vy, pos, y);
    }

    private List<Integer> allXPos(int vx, List<Integer> pos, int x) {
        x += vx;
        pos.add(x);
        if (vx > 0) vx--;
        else if (vx < 0) vx++;
        if (x > 67) {
            return pos;
        }
        return allXPos(vx, pos, x);
    }

    private void printList(List<?> list) {
        for (Object o : list) {
            System.out.print(o + " ");
        }
        System.out.println("");
    }

    private int sumRange(int a, int b) {
        return (b - a + 1) * (b + a) / 2;
    }



    @Override
    public String partTwo() {
        System.out.println("\n\n");
        // target area: x=25..67, y=-260..-200
        String in = "target area: x=25..67, y=-260..-200";
        final int minX = Integer.parseInt(in.substring(15, in.indexOf('.', 15)));
        final int maxX = Integer.parseInt(in.substring(in.indexOf('.', 15) + 2, in.indexOf(',', 15)));
        final int spaceIndex = in.indexOf(' ', 15);
        final int minY = Integer.parseInt(in.substring(spaceIndex + 3, in.indexOf('.', spaceIndex)));
        final int maxY = Integer.parseInt(in.substring(in.indexOf('.', spaceIndex) + 2));

        final int maxVxi = maxX;
        final int minVxi = (int) Math.ceil((Math.sqrt(1 + 8 * minX) - 1) / 2);

        final int minVyi = minY;

        // Unsure if there exists a true max and how to calculate it
        final int maxVyi = 500;

        // Try every combination within our calculated reasonable range
        int successes = 0;
        for (int vxi = minVxi; vxi <= maxVxi; vxi++) {
            for (int vyi = minVyi; vyi <= maxVyi; vyi++) {

                int vx = vxi;
                int vy = vyi;
                int x = 0;
                int y = 0;

                // Go until you overshoot either dimension (y will always overshoot eventually)
                while (x <= maxX && y >= minY) {
                    // Step:
                    x += vx;
                    y += vy;

                    if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                        System.out.printf("%d,%d ", vxi, vyi);
                        successes++;
                        // Success: don't count this velocity combination again.
                        break;
                    }

                    if (vx > 0) vx--;
                    vy--;
                }
            }
        }
        return String.valueOf(successes);
    }
}
