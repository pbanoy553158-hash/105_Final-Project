package src.optimized;

import java.util.*;

/** Merge Sort implementation - O(n log n) for efficient sorting */
public class MergeSort {
    
    /** Sort by deadline using merge sort */
    public static List<Task> sortByDeadline(List<Task> tasks) {
        if (tasks == null || tasks.size() <= 1) return tasks;
        Task[] array = tasks.toArray(new Task[0]);
        mergeSortByDeadline(array, 0, array.length - 1);
        return new ArrayList<>(Arrays.asList(array));
    }
    
    private static void mergeSortByDeadline(Task[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortByDeadline(array, left, mid);
            mergeSortByDeadline(array, mid + 1, right);
            mergeByDeadline(array, left, mid, right);
        }
    }
    
    private static void mergeByDeadline(Task[] array, int left, int mid, int right) {
        int n1 = mid - left + 1, n2 = right - mid;
        Task[] leftArray = new Task[n1];
        Task[] rightArray = new Task[n2];
        for (int i = 0; i < n1; i++) leftArray[i] = array[left + i];
        for (int j = 0; j < n2; j++) rightArray[j] = array[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i].deadline <= rightArray[j].deadline) array[k] = leftArray[i++];
            else array[k] = rightArray[j++];
            k++;
        }
        while (i < n1) array[k++] = leftArray[i++];
        while (j < n2) array[k++] = rightArray[j++];
    }
    
    /** Sort by priority using merge sort */
    public static List<Task> sortByPriority(List<Task> tasks) {
        if (tasks == null || tasks.size() <= 1) return tasks;
        Task[] array = tasks.toArray(new Task[0]);
        mergeSortByPriority(array, 0, array.length - 1);
        return new ArrayList<>(Arrays.asList(array));
    }
    
    private static void mergeSortByPriority(Task[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortByPriority(array, left, mid);
            mergeSortByPriority(array, mid + 1, right);
            mergeByPriority(array, left, mid, right);
        }
    }
    
    private static void mergeByPriority(Task[] array, int left, int mid, int right) {
        int n1 = mid - left + 1, n2 = right - mid;
        Task[] leftArray = new Task[n1];
        Task[] rightArray = new Task[n2];
        for (int i = 0; i < n1; i++) leftArray[i] = array[left + i];
        for (int j = 0; j < n2; j++) rightArray[j] = array[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i].priority <= rightArray[j].priority) array[k] = leftArray[i++];
            else array[k] = rightArray[j++];
            k++;
        }
        while (i < n1) array[k++] = leftArray[i++];
        while (j < n2) array[k++] = rightArray[j++];
    }
}