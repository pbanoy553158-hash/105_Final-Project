package src.baseline;

import java.util.*;

public class AccurateTestRunner {
    
    public static void main(String[] args) {
        if (args.length < 1) System.exit(1);
        int taskCount = Integer.parseInt(args[0]);
        
        // FORCE JIT COMPILATION OFF - MULTIPLE METHODS
        System.setProperty("java.compiler", "NONE");
        
        // Force garbage collection and pause
        System.gc();
        try { Thread.sleep(200); } catch (InterruptedException e) {}
        
        // DO THE WORK MULTIPLE TIMES TO ENSURE COLD START
        // First run is discarded (warmup)
        for (int warmup = 0; warmup < 2; warmup++) {
            List<Task> warmupTasks = new ArrayList<>();
            List<Task> warmupNew = DataLoader.generateTasks(taskCount);
            DataLoader.loadIntoSystem(warmupNew, warmupTasks);
        }
        
        // Force GC again before actual measurement
        System.gc();
        try { Thread.sleep(200); } catch (InterruptedException e) {}
        
        // ACTUAL MEASUREMENT - THIS IS THE REAL TIME
        long startTime = System.nanoTime();
        
        List<Task> tasks = new ArrayList<>();
        List<Task> newTasks = DataLoader.generateTasks(taskCount);
        DataLoader.loadIntoSystem(newTasks, tasks);
        
        long endTime = System.nanoTime();
        double timeMs = (endTime - startTime) / 1_000_000.0;
        
        System.out.println("RESULT:" + taskCount + "," + timeMs);
        System.exit(0);
    }
}