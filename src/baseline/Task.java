package src.baseline;

import java.util.ArrayList;
import java.util.List;

/** Task model - stores id, name, priority, deadline */
public class Task {
    public String id;
    public String name;
    public int priority;      // 1=Critical, 5=Minimal
    public int deadline;      // YYYYMMDD format
    public List<String> dependencies;
    public boolean completed;

    public Task(String id, String name, int priority, int deadline) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        this.dependencies = new ArrayList<>();
        this.completed = false;
    }

    /** Converts YYYYMMDD to YYYY-MM-DD for display */
    public String getFormattedDeadline() {
        String d = String.valueOf(deadline);
        if (d.length() == 8) {
            return d.substring(0, 4) + "-" + d.substring(4, 6) + "-" + d.substring(6, 8);
        }
        return d;
    }

    /** Converts numeric priority to readable label */
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