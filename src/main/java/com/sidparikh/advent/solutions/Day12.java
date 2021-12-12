package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day12 extends Solution {
    public Day12() {
        super(12);
    }

    /**
     * Recursively finds all paths from a given starting cave to the cave with the name "end." Part One version where each cave can
     * only be visited once.
     *
     * @param start the cave to start searching from
     * @param pathSoFar the caves that have been visited on this path already
     * @return the number of legal paths from {@code start} to the {@link Cave} with the name "end"
     */
    private static int findPathsToEnd(Cave start, List<Cave> pathSoFar) {
        List<Cave> path = new ArrayList<>(List.copyOf(pathSoFar));

        if (start.getName().equals("end")) {
            return 1;
        }

        if (!start.isLarge() && pathSoFar.contains(start)) {
            return 0;
        }

        path.add(start);
        int a = 0;
        for (Cave c : start.getNeighbors()) {
            a += findPathsToEnd(c, path);
        }

        return a;
    }
    /**
     * Recursively finds all paths from a given starting cave to the cave with the name "end." Part Two version
     * where once a path you may visit a small cave twice.
     *
     * @param start the cave to start searching from
     * @param pathSoFar the caves that have been visited on this path already
     * @return the number of legal paths from {@code start} to the {@link Cave} with the name "end"
     */
    private static int findPathsToEndTwo(Cave start, List<Cave> pathSoFar, boolean burned) {
        List<Cave> path = new ArrayList<>(List.copyOf(pathSoFar));

        if (start.getName().equals("end")) {
            return 1;
        }

        int a = 0;

        if (!start.isLarge() && pathSoFar.contains(start)) {
            if (start.getName().equals("start") || burned) {
                return 0;
            } else {
                // Let it go, but once.
                for (Cave c : start.getNeighbors()) {
                    a += findPathsToEndTwo(c, path, true);
                }
                return a;
            }

        }

        path.add(start);
        for (Cave c : start.getNeighbors()) {
            a += findPathsToEndTwo(c, path, burned);
        }

        return a;
    }

    private boolean isStringUppercase(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isUpperCase(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String partOne() {
        // Even though I've set it up like a linked list type thing there aren't a lot of caves so lets track em individually too
        HashMap<String, Cave> caveMap = new HashMap<>();

        for (String line : input) {
            String[] split = line.split("-");


            Cave a = caveMap.getOrDefault(split[0], new Cave(split[0], isStringUppercase(split[0])));
            Cave b = caveMap.getOrDefault(split[1], new Cave(split[1], isStringUppercase(split[1])));

            a.addNeighbor(b);
            b.addNeighbor(a);

            caveMap.putIfAbsent(a.getName(), a);
            caveMap.putIfAbsent(b.getName(), b);
        }

        Cave start = caveMap.get("start");

        ArrayList<Cave> startingPath = new ArrayList<>();
        return String.valueOf(findPathsToEnd(start, startingPath));

    }

    @Override
    public String partTwo() {
        // Even though I've set it up like a linked list type thing there aren't a lot of caves so lets track em individually too
        HashMap<String, Cave> caveMap = new HashMap<>();

        for (String line : input) {
            String[] split = line.split("-");

            Cave a = caveMap.getOrDefault(split[0], new Cave(split[0], isStringUppercase(split[0])));
            Cave b = caveMap.getOrDefault(split[1], new Cave(split[1], isStringUppercase(split[1])));

            a.addNeighbor(b);
            b.addNeighbor(a);

            caveMap.putIfAbsent(a.getName(), a);
            caveMap.putIfAbsent(b.getName(), b);
        }

        Cave start = caveMap.get("start");

        ArrayList<Cave> startingPath = new ArrayList<>();
        return String.valueOf(findPathsToEndTwo(start, startingPath, false));
    }

    private static class Cave {
        private final String name;
        private final boolean isLarge;
        private final ArrayList<Cave> neighbors;

        public Cave(String name, boolean isLarge) {
            this.name = name;
            this.isLarge = isLarge;

            neighbors = new ArrayList<>();
        }

        public void addNeighbor(Cave cave) {
            neighbors.add(cave);
        }

        public String getName() {
            return name;
        }

        public boolean isLarge() {
            return isLarge;
        }

        public ArrayList<Cave> getNeighbors() {
            return neighbors;
        }

    }
}
