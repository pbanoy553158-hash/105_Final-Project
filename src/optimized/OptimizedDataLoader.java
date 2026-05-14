package src.optimized;

import java.util.*;

/** Generates test data for optimized system with optional dependencies */
public class OptimizedDataLoader {
    
    private static final String[] TASK_NAMES = {
        "Research Paper", "Code Review", "Unit Testing", "Documentation",
        "Team Meeting", "Client Presentation", "Database Design", "API Development",
        "Frontend UI", "Backend Logic", "Security Audit", "Performance Testing",
        "Deployment", "User Training", "Bug Fixing", "Feature Implementation"
    };

    private static final String[] PREFIXES = {"TASK", "PROJ", "DEV", "TEST"};
    private static final Random rand = new Random(42);

    /** Generates N tasks with no dependencies */
    public static List<Task> generateTasks(int count) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String prefix = PREFIXES[i % PREFIXES.length];
            String id = String.format("%s%04d", prefix, i + 1);
            String name = TASK_NAMES[i % TASK_NAMES.length] + " " + (i + 1);
            int priority = rand.nextInt(5) + 1;
            int deadline = generateDeadline();
            tasks.add(new Task(id, name, priority, deadline));
        }
        return tasks;
    }

    /** Generates tasks with random dependencies (30% chance) */
    public static List<Task> generateTasksWithDependencies(int count, int dependencyPercent) {
        List<Task> tasks = generateTasks(count);
        Graph<String> dependencyGraph = new Graph<>();
        for (Task t : tasks) dependencyGraph.addVertex(t.id);
        
        for (int i = 1; i < count; i++) {
            if (rand.nextInt(100) < dependencyPercent) {
                int depIndex = rand.nextInt(i);
                String taskId = tasks.get(i).id;
                String depId = tasks.get(depIndex).id;
                if (dependencyGraph.addEdge(depId, taskId)) {
                    tasks.get(i).prerequisites.add(depId);
                    tasks.get(depIndex).dependencies.add(taskId);
                }
            }
        }
        return tasks;
    }

    private static int generateDeadline() {
        int year = 2025 + rand.nextInt(2);
        int month = rand.nextInt(12) + 1;
        int day = rand.nextInt(28) + 1;
        return year * 10000 + month * 100 + day;
    }
}