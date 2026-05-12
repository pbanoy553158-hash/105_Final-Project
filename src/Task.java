package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Task class for the baseline scheduling system.
 * Models a single work item with basic properties.
 */
public class Task {
    /** Unique task identifier */
    public String id;
    /** Display name of the task */
    public String name;
    /** Priority level: 1=Critical, 5=Minimal */
    public int priority;
    /** Deadline in YYYYMMDD format */
    public int deadline;
    /** List of task dependencies */
    public List<String> dependencies;
    /** Completion state */
    public boolean completed;

    /**
     * Create a new task instance.
     */
    public Task(String id, String name, int priority, int deadline) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        this.dependencies = new ArrayList<>();
        this.completed = false;
    }

    /**
     * Return a short text summary of the task.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s | Priority: %d | Due: %d", id, name, priority, deadline);
    }

    /**
     * Convert integer deadline to YYYY-MM-DD format.
     */
    public String getFormattedDeadline() {
        String d = String.valueOf(deadline);
        if (d.length() == 8) {
            return d.substring(0, 4) + "-" + d.substring(4, 6) + "-" + d.substring(6, 8);
        }
        return d;
    }

    /**
     * Return user-friendly label for priority value.
     */
    public String getPriorityLabel() {
        switch (priority) {
            case 1: return "Critical";
            case 2: return "High";
            case 3: return "Medium";
            case 4: return "Low";
            case 5: return "Minimal";
            default: return "P" + priority;
        }
    }
}