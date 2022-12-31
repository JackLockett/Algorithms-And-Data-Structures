package com.company;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private final List<String> vertices;
    private final List<List<Edge>> adjacencyList;

    public Main(List<String> vertices) {
        this.vertices = vertices;
        this.adjacencyList = new LinkedList<>();
        for (int i = 0; i < vertices.size(); i++) {
            this.adjacencyList.add(new LinkedList<>());
        }
    }

    public void addEdge(String src, String dest, int weight) {
        this.adjacencyList.get(getVertexIndex(src)).add(new Edge(src, dest, weight));
    }

    private int getVertexIndex(String vertex) {
        return vertices.indexOf(vertex);
    }

    public List<List<String>> getAllPaths(String src, String dest) {
        List<List<String>> paths = new LinkedList<>();
        getAllPathsUtil(src, dest, new LinkedList<>(), paths);
        return paths;
    }

    private void getAllPathsUtil(String src, String dest, List<String> path, List<List<String>> paths) {
        path.add(src);
        if (src.equals(dest)) {
            paths.add(new LinkedList<>(path));
        } else {
            for (Edge edge : adjacencyList.get(getVertexIndex(src))) {
                getAllPathsUtil(edge.dest, dest, path, paths);
            }
        }
        path.remove(path.size() - 1);
    }

    public int getPathCost(List<String> path) {
        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String src = path.get(i);
            String dest = path.get(i + 1);
            for (Edge edge : adjacencyList.get(getVertexIndex(src))) {
                if (edge.dest.equals(dest)) {
                    cost += edge.weight;
                    break;
                }
            }
        }
        return cost;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> vertices = List.of("New York", "Miami", "Dallas", "Chicago", "Denver", "San Diego", "San Francisco", "LA");
        Main graph = new Main(vertices);

        graph.addEdge("New York", "Miami", 90);
        graph.addEdge("New York", "Dallas", 125);
        graph.addEdge("New York", "Chicago", 75);
        graph.addEdge("New York", "Denver", 100);
        graph.addEdge("Miami", "Dallas", 50);
        graph.addEdge("Dallas", "San Diego", 90);
        graph.addEdge("Dallas", "LA", 80);
        graph.addEdge("Chicago", "Denver", 20);
        graph.addEdge("Chicago", "San Francisco", 25);
        graph.addEdge("Denver", "San Francisco", 75);
        graph.addEdge("Denver", "LA", 100);
        graph.addEdge("San Diego", "LA", 45);
        graph.addEdge("San Francisco", "LA", 45);

        System.out.print("Enter a city to display the cities directly accessible from that city: ");
        String cityName = scanner.nextLine();

        List<String> destinations = new LinkedList<>();
        for (Edge edge : graph.adjacencyList.get(vertices.indexOf(cityName)))
            destinations.add(edge.dest);

        System.out.println("\nCities directly accessible from " + cityName + ":");

        for (String dest : destinations)
            System.out.println(dest);

        System.out.print("\nEnter the source city: ");
        String src = scanner.nextLine();
        System.out.print("Enter the destination city: ");
        String dest = scanner.nextLine();
        System.out.print("\n");
        List<List<String>> paths = graph.getAllPaths(src, dest);
        paths.sort(Comparator.comparingInt(graph::getPathCost));

        for (List<String> path : paths)
            System.out.println(path + ": " + "- Cost $" + graph.getPathCost(path));
    }
}