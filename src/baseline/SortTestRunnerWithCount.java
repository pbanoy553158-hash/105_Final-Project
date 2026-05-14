package src.baseline;

import java.util.*;

/** Runs BUBBLE SORT O(n²) in fresh JVM with JIT disabled - REAL TIME */
public class SortTestRunnerWithCount {
    
    public static void main(String[] args) {
        if (args.length < 2) System.exit(1);
        
        String sortBy = args[0];
        int taskCount = Integer.parseInt(args[1]);
        
        // Disable JIT - this gives REAL O(n²) execution time
        System.setProperty("java.compiler", "NONE");
        
        List<Task> tasks = new ArrayList<>();
        List<Task> newTasks = DataLoader.generateTasks(taskCount);
        DataLoader.loadIntoSystem(newTasks, tasks);
        
        List<Task> toSort = new ArrayList<>(tasks);
        
        System.gc();
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        
        long startTime = System.nanoTime();
        int n = toSort.size();
        int comparisons = 0;
        int swaps = 0;
        
        // BUBBLE SORT O(n²) - Real unoptimized execution
        if (sortBy.equals("deadline")) {
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    comparisons++;
                    if (toSort.get(j).deadline > toSort.get(j + 1).deadline) {
                        Task temp = toSort.get(j);
                        toSort.set(j, toSort.get(j + 1));
                        toSort.set(j + 1, temp);
                        swaps++;
                    }
                }
            }
        } else {
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    comparisons++;
                    if (toSort.get(j).priority > toSort.get(j + 1).priority) {
                        Task temp = toSort.get(j);
                        toSort.set(j, toSort.get(j + 1));
                        toSort.set(j + 1, temp);
                        swaps++;
                    }
                }
            }
        }
        
        double sortTime = (System.nanoTime() - startTime) / 1_000_000.0;
        System.out.println("RESULT:" + sortTime + "," + comparisons + "," + swaps);
        System.exit(0);
    }
}