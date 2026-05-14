package src.baseline;

import java.util.*;

/** Runs LINEAR SEARCH O(n) in fresh JVM with JIT disabled */
public class SearchTestRunnerWithCount {
    
    public static void main(String[] args) {
        if (args.length < 2) System.exit(1);
        
        String searchId = args[0];
        int taskCount = Integer.parseInt(args[1]);
        
        // Disable JIT for real time
        System.setProperty("java.compiler", "NONE");
        
        List<Task> tasks = new ArrayList<>();
        List<Task> newTasks = DataLoader.generateTasks(taskCount);
        DataLoader.loadIntoSystem(newTasks, tasks);
        
        System.gc();
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        
        long startTime = System.nanoTime();
        boolean found = false;
        int comparisons = 0;
        
        // LINEAR SEARCH O(n) - scans sequentially
        for (Task t : tasks) {
            comparisons++;
            if (t.id.equalsIgnoreCase(searchId)) {
                found = true;
                break;
            }
        }
        
        double searchTime = (System.nanoTime() - startTime) / 1_000_000.0;
        System.out.println("RESULT:" + searchTime + "," + comparisons + "," + found);
        System.exit(0);
    }
}