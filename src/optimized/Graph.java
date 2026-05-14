package src.optimized;

import java.util.*;

/** Directed Acyclic Graph (DAG) for task dependencies - Topological Sort O(V+E) */
public class Graph<T> {
    private Map<T, List<T>> adjacencyList;
    private Map<T, Integer> inDegree;
    private Set<T> vertices;
    
    public Graph() {
        adjacencyList = new HashMap<>();
        inDegree = new HashMap<>();
        vertices = new HashSet<>();
    }
    
    public void addVertex(T vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            adjacencyList.put(vertex, new ArrayList<>());
            inDegree.put(vertex, 0);
        }
    }
    
    /** Adds edge: source must be completed before destination */
    public boolean addEdge(T source, T destination) {
        if (!vertices.contains(source)) addVertex(source);
        if (!vertices.contains(destination)) addVertex(destination);
        if (wouldCreateCycle(source, destination)) return false;
        adjacencyList.get(source).add(destination);
        inDegree.put(destination, inDegree.get(destination) + 1);
        return true;
    }
    
    private boolean wouldCreateCycle(T source, T destination) {
        return canReach(destination, source);
    }
    
    /** BFS to check reachability */
    private boolean canReach(T start, T target) {
        if (start.equals(target)) return true;
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            T current = queue.poll();
            if (visited.contains(current)) continue;
            visited.add(current);
            for (T neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                if (neighbor.equals(target)) return true;
                if (!visited.contains(neighbor)) queue.add(neighbor);
            }
        }
        return false;
    }
    
    /** Cycle detection using Kahn's algorithm - O(V+E) */
    public boolean hasCycle() {
        Map<T, Integer> tempInDegree = new HashMap<>(inDegree);
        Queue<T> queue = new LinkedList<>();
        for (T vertex : vertices) {
            if (tempInDegree.getOrDefault(vertex, 0) == 0) queue.add(vertex);
        }
        int processedCount = 0;
        while (!queue.isEmpty()) {
            T current = queue.poll();
            processedCount++;
            for (T neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                tempInDegree.put(neighbor, tempInDegree.get(neighbor) - 1);
                if (tempInDegree.get(neighbor) == 0) queue.add(neighbor);
            }
        }
        return processedCount != vertices.size();
    }
    
    /** Topological Sort using Kahn's algorithm - O(V+E) */
    public List<T> topologicalSort() {
        List<T> result = new ArrayList<>();
        Map<T, Integer> tempInDegree = new HashMap<>(inDegree);
        Queue<T> queue = new LinkedList<>();
        for (T vertex : vertices) {
            if (tempInDegree.getOrDefault(vertex, 0) == 0) queue.add(vertex);
        }
        while (!queue.isEmpty()) {
            T current = queue.poll();
            result.add(current);
            for (T neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                tempInDegree.put(neighbor, tempInDegree.get(neighbor) - 1);
                if (tempInDegree.get(neighbor) == 0) queue.add(neighbor);
            }
        }
        return result;
    }
    
    public Set<T> getVertices() { return new HashSet<>(vertices); }
    public List<T> getDependents(T vertex) {
        return new ArrayList<>(adjacencyList.getOrDefault(vertex, new ArrayList<>()));
    }
    public void clear() { adjacencyList.clear(); inDegree.clear(); vertices.clear(); }
    public int size() { return vertices.size(); }
}