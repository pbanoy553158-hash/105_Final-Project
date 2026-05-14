package src.optimized;

import java.util.ArrayList;
import java.util.List;

/** Enhanced Task model with prerequisites and urgency scoring */
public class Task implements Comparable<Task> {
    public String id;
    public String name;
    public int priority;           // 1=Critical, 5=Minimal
    public int deadline;           // YYYYMMDD format
    public List<String> dependencies;   // Tasks that depend on this
    public List<String> prerequisites;  // Tasks this depends on
    public boolean completed;
    public int urgencyScore;

    public Task(String id, String name, int priority, int deadline) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        this.dependencies = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
        this.completed = false;
        this.urgencyScore = calculateUrgencyScore();
    }

    /** Calculates urgency based on priority (60%) and deadline (40%) */
    public int calculateUrgencyScore() {
        int priorityWeight = (6 - priority) * 10;
        int year = deadline / 10000;
        int month = (deadline / 100) % 100;
        int day = deadline % 100;
        int daysUntilDeadline = (year - 2025) * 365 + (month - 1) * 30 + day;
        int deadlineWeight = Math.max(0, 100 - daysUntilDeadline);
        return priorityWeight + deadlineWeight;
    }

    /** For MinHeap - compares by priority first, then deadline */
    @Override
    public int compareTo(Task other) {
        if (this.priority != other.priority) {
            return Integer.compare(this.priority, other.priority);
        }
        return Integer.compare(this.deadline, other.deadline);
    }

    public String getFormattedDeadline() {
        String d = String.valueOf(deadline);
        if (d.length() == 8) {
            return d.substring(0, 4) + "-" + d.substring(4, 6) + "-" + d.substring(6, 8);
        }
        return d;
    }

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

    public String getUrgencyLabel() {
        if (urgencyScore >= 80) return "Urgent";
        if (urgencyScore >= 60) return "High";
        if (urgencyScore >= 40) return "Medium";
        if (urgencyScore >= 20) return "Low";
        return "Minimal";
    }
}