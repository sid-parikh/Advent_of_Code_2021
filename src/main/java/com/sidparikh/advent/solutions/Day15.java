package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.io.IOException;
import java.util.*;

public class Day15 extends Solution {
    public Day15() throws IOException {
        super(15);
    }

    public static class Node implements Comparable<Node> {
        String name;
        // The value of this point on the map, also the "distance"/cost from this node to all adjacent nodes
        int val;
        // The (shortest) distance from this node to the starting one
        int dist = Integer.MAX_VALUE;
        // All adjacent nodes
        List<Node> adjacentNodes = new ArrayList<>();
        // The shortest path from this node to the starting one
//        List<Node> shortestPath = new LinkedList<>();

        Node(int v, int i, int j) {
            val = v;
            name = String.format("(%d, %d)", i, j);
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.dist, o.dist);
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    public static void calculateShortestPathFromSource(Node source, int size, Node target) {
        source.dist = 0;
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Node> unvisited = new PriorityQueue<>();

        unvisited.add(source);

        while (visited.size() != size) {

            if (unvisited.isEmpty()) {
                return;
            }

            Node currentNode = unvisited.poll();

            if (visited.contains(currentNode)) {
                continue;
            }

            for (Node adjacentNode : currentNode.adjacentNodes) {
                if (!visited.contains(adjacentNode)) {
                    calcMinDist(adjacentNode, currentNode);
                    unvisited.offer(adjacentNode);
                }
            }
            visited.add(currentNode);

            if (currentNode.name.equals(target.name)) {
                return;
            }

        }
    }

    private static void calcMinDist(Node evaluationNode, Node sourceNode) {
        int sourceDist = sourceNode.dist;
        if (sourceDist + evaluationNode.val < evaluationNode.dist) {
            evaluationNode.dist = sourceDist + evaluationNode.val;
//            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.shortestPath);
//            shortestPath.add(sourceNode);
//            evaluationNode.shortestPath = shortestPath;
        }
    }

    public static int[][] expandFiveTimes(int[][] map) {
        final int LEN = map.length;

        int[][] newMap = new int[LEN * 5][LEN * 5];

        // Zero
        for (int i = 0; i < LEN; i++) {
            System.arraycopy(map[i], 0, newMap[i], 0, LEN);
        }

        // One
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                newMap[LEN + i][j] = increment(map[i][j], 1);
                newMap[i][LEN + j] = increment(map[i][j], 1);
            }
        }

        // Two
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                newMap[LEN * 2 + i][j] = increment(map[i][j], 2);
                newMap[i][LEN * 2 + j] = increment(map[i][j], 2);
                newMap[LEN + i][LEN + j] = increment(map[i][j], 2);
            }
        }

        // Three
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                newMap[LEN * 3 + i][j] = increment(map[i][j], 3);
                newMap[LEN * 2 + i][LEN + j] = increment(map[i][j], 3);
                newMap[LEN + i][LEN * 2 + j] = increment(map[i][j], 3);
                newMap[i][LEN * 3 + j] = increment(map[i][j], 3);
            }
        }

        // Four
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                newMap[LEN * 4 + i][j] = increment(map[i][j], 4);
                newMap[LEN * 3 + i][LEN + j] = increment(map[i][j], 4);
                newMap[LEN * 2 + i][LEN * 2 + j] = increment(map[i][j], 4);
                newMap[LEN + i][LEN * 3 + j] = increment(map[i][j], 4);
                newMap[i][LEN * 4 + j] = increment(map[i][j], 4);
            }
        }

        // Five
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                newMap[LEN * 4 + i][LEN + j] = increment(map[i][j], 5);
                newMap[LEN * 3 + i][LEN * 2 + j] = increment(map[i][j], 5);
                newMap[LEN * 2 + i][LEN * 3 + j] = increment(map[i][j], 5);
                newMap[LEN + i][LEN * 4 + j] = increment(map[i][j], 5);
            }
        }

        // Six
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                newMap[LEN * 4 + i][LEN * 2 + j] = increment(map[i][j], 6);
                newMap[LEN * 3 + i][LEN * 3 + j] = increment(map[i][j], 6);
                newMap[LEN * 2 + i][LEN * 4 + j] = increment(map[i][j], 6);
            }
        }

        // Seven
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                newMap[LEN * 4 + i][LEN * 3 + j] = increment(map[i][j], 7);
                newMap[LEN * 3 + i][LEN * 4 + j] = increment(map[i][j], 7);
            }
        }

        // Eight
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                newMap[LEN * 4 + i][LEN * 4 + j] = increment(map[i][j], 8);
            }
        }

        return newMap;
    }

    /**
     * Increments a number part two style
     *
     * @param start  starting number
     * @param amount amount to increase by
     * @return new number
     */
    public static int increment(int start, int amount) {
        while (amount > 0) {
            start++;
            amount--;
            if (start == 10) {
                start = 1;
            }
        }
        return start;
    }

    public static String solveMaze(int[][] map) {
        Node[][] nodeMap = new Node[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                nodeMap[i][j] = new Node(map[i][j], i, j);
            }
        }

        final int size = nodeMap.length * nodeMap[0].length;

        for (int i = 0; i < nodeMap.length; i++) {
            for (int j = 0; j < nodeMap[0].length; j++) {

                if (i > 0) {
                    nodeMap[i][j].adjacentNodes.add(nodeMap[i - 1][j]);
                }
                if (i + 1 < nodeMap.length) {
                    nodeMap[i][j].adjacentNodes.add(nodeMap[i + 1][j]);
                }
                if (j > 0) {
                    nodeMap[i][j].adjacentNodes.add(nodeMap[i][j - 1]);
                }
                if (j + 1 < nodeMap.length) {
                    nodeMap[i][j].adjacentNodes.add(nodeMap[i][j + 1]);
                }

            }
        }

        calculateShortestPathFromSource(nodeMap[0][0], size, nodeMap[nodeMap.length - 1][nodeMap[0].length - 1]);

        return String.valueOf(nodeMap[nodeMap.length - 1][nodeMap[0].length - 1].dist);
    }

    @Override
    public String partOne() {
        // Create map
        int[][] map = new int[input.size()][input.get(0).length()];
        // Parse input for each row and save
        for (int i = 0; i < input.size(); i++) {
            map[i] = Arrays.stream(input.get(i).split("")).mapToInt(Integer::parseInt).toArray();
        }

        return solveMaze(map);

    }

    @Override
    public String partTwo() {
        // Create map
        int[][] map = new int[input.size()][input.get(0).length()];
        // Parse input for each row and save
        for (int i = 0; i < input.size(); i++) {
            map[i] = Arrays.stream(input.get(i).split("")).mapToInt(Integer::parseInt).toArray();
        }

        map = expandFiveTimes(map);

        return solveMaze(map);
    }


}
