package src.baseline;

import java.util.*;

/** Generates sample task data with fixed seed (42) for reproducibility */
public class DataLoader {
    
    private static final String[] TASK_NAMES = {
        "Research Paper", "Code Review", "Unit Testing", "Documentation",
        "Team Meeting", "Client Presentation", "Database Design", "API Development",
        "Frontend UI", "Backend Logic", "Security Audit", "Performance Testing",
        "Deployment", "User Training", "Bug Fixing", "Feature Implementation"
    };

    private static final String[] PREFIXES = {"TASK", "PROJ", "DEV", "TEST"};

    /** Creates N tasks with random priorities/deadlines - O(n) */
    public static List<Task> generateTasks(int count) {
        List<Task> tasks = new ArrayList<>(count);
        Random rand = new Random(42);  // Fixed seed = reproducible results
        
        for (int i = 0; i < count; i++) {
            String prefix = PREFIXES[i % PREFIXES.length];
            String id = String.format("%s%04d", prefix, i + 1);
            String name = TASK_NAMES[i % TASK_NAMES.length] + " " + (i + 1);
            int priority = rand.nextInt(5) + 1;
            int deadline = generateDeadline(rand);
            tasks.add(new Task(id, name, priority, deadline));
        }
        return tasks;
    }

    /** Creates random deadline in 2025-2026 range */
    private static int generateDeadline(Random rand) {
        int year = 2025 + rand.nextInt(2);
        int month = rand.nextInt(12) + 1;
        int day = rand.nextInt(28) + 1;
        return year * 10000 + month * 100 + day;
    }

    /** Loads tasks into ArrayList - clear() + addAll() = O(n) */
    public static void loadIntoSystem(List<Task> newTasks, List<Task> existingTasks) {
        existingTasks.clear();
        existingTasks.addAll(newTasks);
    }
}