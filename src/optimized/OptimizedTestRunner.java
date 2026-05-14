package src.optimized;

import java.util.*;

public class OptimizedTestRunner {
    
    public static void main(String[] args) {
        if (args.length < 1) System.exit(1);
        
        int taskCount = Integer.parseInt(args[0]);
        String operation = args.length > 1 ? args[1] : "load";
        
        // CRITICAL: Disable JIT compilation for REAL times
        System.setProperty("java.compiler", "NONE");
        
        System.gc();
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        
        long startTime = System.nanoTime();
        
        switch (operation) {
            case "load":
                List<Task> tasks = OptimizedDataLoader.generateTasks(taskCount);
                Map<String, Task> map = new HashMap<>();
                MinHeap<Task> heap = new MinHeap<>();
                for (Task t : tasks) {
                    map.put(t.id, t);
                    heap.insert(t);
                }
                break;
                
            case "load_deps":
                List<Task> depsTasks = OptimizedDataLoader.generateTasksWithDependencies(taskCount, 30);
                break;
                
            case "sort_deadline":
                List<Task> deadlineTasks = OptimizedDataLoader.generateTasks(taskCount);
                MergeSort.sortByDeadline(deadlineTasks);
                break;
                
            case "sort_priority":
                List<Task> priorityTasks = OptimizedDataLoader.generateTasks(taskCount);
                MergeSort.sortByPriority(priorityTasks);
                break;
                
            case "heap_schedule":
                List<Task> heapTasks = OptimizedDataLoader.generateTasks(taskCount);
                MinHeap<Task> minHeap = new MinHeap<>();
                for (Task t : heapTasks) minHeap.insert(t);
                List<Task> scheduled = new ArrayList<>();
                while (!minHeap.isEmpty()) {
                    scheduled.add(minHeap.extractMin());
                }
                break;
                
            case "topological":
                List<Task> topoTasks = OptimizedDataLoader.generateTasksWithDependencies(taskCount, 30);
                Graph<String> graph = new Graph<>();
                for (Task t : topoTasks) graph.addVertex(t.id);
                for (Task t : topoTasks) {
                    for (String prereq : t.prerequisites) {
                        graph.addEdge(prereq, t.id);
                    }
                }
                List<String> order = graph.topologicalSort();
                break;
        }
        
        double timeMs = (System.nanoTime() - startTime) / 1_000_000.0;
        System.out.println("RESULT:" + taskCount + "," + timeMs);
        System.exit(0);
    }
}