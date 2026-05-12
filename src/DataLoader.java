package src;

import java.util.*;

/**
 * DataLoader supplies generated task data for the baseline scheduler.
 * Creates reproducible sample tasks with deadlines.
 */
public class DataLoader {
    
    private static final String[] TASK_NAMES = {
        "Research Paper", "Code Review", "Unit Testing", "Documentation",
        "Team Meeting", "Client Presentation", "Database Design", "API Development",
        "Frontend UI", "Backend Logic", "Security Audit", "Performance Testing",
        "Deployment", "User Training", "Bug Fixing", "Feature Implementation"
    };

    private static final String[] PREFIXES = {"TASK", "PROJ", "DEV", "TEST"};

    /**
     * Generate N sample Task objects.
     * @param count number of tasks to generate
     * @return list of generated Task objects
     */
    public static List<Task> generateTasks(int count) {
        List<Task> tasks = new ArrayList<>();
        Random rand = new Random(42); // Fixed seed for reproducibility
        
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

    /**
     * Create a sample deadline value in YYYYMMDD format.
     */
    private static int generateDeadline(Random rand) {
        int year = 2025 + rand.nextInt(2);
        int month = rand.nextInt(12) + 1;
        int day = rand.nextInt(28) + 1;
        return year * 10000 + month * 100 + day;
    }

    /**
     * Load tasks into the application data list.
     */
    public static void loadIntoSystem(List<Task> tasks, List<Task> allTasks) {
        allTasks.clear();
        allTasks.addAll(tasks);
    }
}