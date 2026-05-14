package src.baseline;

import java.io.*;

public class RunBaselineTests {
    
    public static void main(String[] args) {
        int[] sizes = {100, 500, 1000};
        int runs = 5;
        
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("BASELINE SYSTEM - REAL PERFORMANCE MEASUREMENT");
        System.out.println("Each test runs in a SEPARATE JVM with JIT disabled");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        // Test LOAD & INSERT
        System.out.println("--- LOAD & INSERT TESTS ---");
        for (int size : sizes) {
            double total = 0;
            for (int run = 1; run <= runs; run++) {
                double time = runSingleLoadTest(size);
                total += time;
                System.out.printf("  %d tasks - Run %d: %.4f ms%n", size, run, time);
            }
            double avg = total / runs;
            System.out.printf("  %d tasks - AVERAGE: %.4f ms%n%n", size, avg);
        }
        
        // Test BUBBLE SORT
        System.out.println("--- BUBBLE SORT (DEADLINE) TESTS ---");
        for (int size : sizes) {
            double total = 0;
            for (int run = 1; run <= runs; run++) {
                double time = runSingleSortTest(size);
                total += time;
                System.out.printf("  %d tasks - Run %d: %.4f ms%n", size, run, time);
            }
            double avg = total / runs;
            System.out.printf("  %d tasks - AVERAGE: %.4f ms%n%n", size, avg);
        }
    }
    
    private static double runSingleLoadTest(int taskCount) {
        try {
            String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            
            // -Xint = interpreted mode (NO JIT)
            // -Xms16m = small initial heap
            ProcessBuilder pb = new ProcessBuilder(
                javaBin, "-Xint", "-Xms16m", "-Xmx64m", "-cp", classpath,
                "src.baseline.AccurateTestRunner", String.valueOf(taskCount)
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            double result = 0;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("RESULT:")) {
                    String[] parts = line.substring(7).split(",");
                    result = Double.parseDouble(parts[1]);
                }
            }
            process.waitFor();
            return result;
        } catch (Exception e) {
            return -1;
        }
    }
    
    private static double runSingleSortTest(int taskCount) {
        try {
            String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            
            // -Xint = interpreted mode (NO JIT) - gives REAL O(n²) time
            ProcessBuilder pb = new ProcessBuilder(
                javaBin, "-Xint", "-Xms16m", "-Xmx64m", "-cp", classpath,
                "src.baseline.SortTestRunnerWithCount", "deadline", String.valueOf(taskCount)
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            double result = 0;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("RESULT:")) {
                    String[] parts = line.substring(7).split(",");
                    result = Double.parseDouble(parts[0]);
                }
            }
            process.waitFor();
            return result;
        } catch (Exception e) {
            return -1;
        }
    }
}