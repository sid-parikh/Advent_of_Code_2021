package com.sidparikh.advent.solutions;

import com.sidparikh.advent.Solution;

import java.util.*;

public class Day15 extends Solution {
    public Day15() {
        super(15);
    }

    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.dist = 0;
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Node> unvisited = new PriorityQueue<>();

        unvisited.add(source);

        while (unvisited.size() != 0) {
            Node currentNode = unvisited.poll();
            for (Node adjacentNode : currentNode.adjacentNodes) {
                if (!visited.contains(adjacentNode)) {
                    calcMinDist(adjacentNode, currentNode);
                    unvisited.offer(adjacentNode);
                }
            }
            visited.add(currentNode);
        }
        return graph;
    }

    private static void calcMinDist(Node evaluationNode, Node sourceNode) {
        int sourceDist = sourceNode.dist;
        if (sourceDist + evaluationNode.val < evaluationNode.dist) {
            evaluationNode.dist = sourceDist + evaluationNode.val;
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.shortestPath);
            shortestPath.add(sourceNode);
            evaluationNode.shortestPath = shortestPath;
        }
    }

    @Override
    public String partOne() {
        // Create map
        int[][] map = new int[input.size()][input.get(0).length()];
        // Parse input for each row and save
        for (int i = 0; i < input.size(); i++) {
            map[i] = Arrays.stream(input.get(i).split("")).mapToInt(Integer::parseInt).toArray();
        }

        Node[][] nodeMap = new Node[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                nodeMap[i][j] = new Node(map[i][j]);
            }
        }

        Graph graph = new Graph();

        for (int i = 0; i < nodeMap.length; i++) {
            for (int j = 0; j < nodeMap[0].length; j++) {

                graph.addNode(nodeMap[i][j]);

                for (int r = Math.max(0, i - 1); r < Math.min(i + 1, nodeMap.length - 1); r++) {
                    for (int c = Math.max(0, j - 1); c < Math.min(j + 1, nodeMap[0].length - 1); c++) {
                        nodeMap[i][j].adjacentNodes.add(nodeMap[r][c]);
                    }
                }

            }
        }

        calculateShortestPathFromSource(graph, nodeMap[0][0]);

        return String.valueOf(nodeMap[nodeMap.length - 1][nodeMap[0].length - 1].shortestPath);


        //return String.valueOf(findPath(map, 0, 0) - map[0][0]);
    }

    @Override
    public String partTwo() {
        return null;
    }

    public static class Node implements Comparable<Node> {
        // The value of this point on the map, also the "distance"/cost from this node to all adjacent nodes
        int val;
        // The (shortest) distance from this node to the starting one
        int dist = Integer.MAX_VALUE;
        // All adjacent nodes
        List<Node> adjacentNodes = new ArrayList<>();
        // The shortest path from this node to the starting one
        List<Node> shortestPath = new LinkedList<>();

        Node(int v) {
            val = v;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.dist, o.dist);
        }
    }

//    public static int findPath(int[][] map, int i, int j) {
//        if (i == map.length - 1) {
//            if (j == map.length - 1) {
//                return map[i][j];
//            }
//            return map[i][j] + findPath(map, i, j + 1);
//        }
//        if (j == map.length - 1) {
//            return map[i][j] + findPath(map, i + 1, j);
//        }
//
//        return map[i][j] + Math.min(findPath(map, i + 1, j), findPath(map, i, j + 1));
//    }

    public static class Graph {
        Set<Node> nodes = new HashSet<>();

        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }
    }
}
