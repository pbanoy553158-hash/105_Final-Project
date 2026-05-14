package src.optimized;

import java.util.*;

/** Custom MinHeap for priority queue - O(log n) insert and extract */
public class MinHeap<T extends Comparable<T>> {
    private ArrayList<T> heap;
    private int size;
    
    public MinHeap() {
        heap = new ArrayList<>();
        size = 0;
    }
    
    /** Insert element - O(log n) */
    public void insert(T element) {
        heap.add(element);
        size++;
        bubbleUp(size - 1);
    }
    
    /** Extract minimum (highest priority) - O(log n) */
    public T extractMin() {
        if (size == 0) return null;
        T min = heap.get(0);
        T last = heap.get(size - 1);
        heap.set(0, last);
        heap.remove(size - 1);
        size--;
        if (size > 0) bubbleDown(0);
        return min;
    }
    
    public T peekMin() { return size > 0 ? heap.get(0) : null; }
    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
    public void clear() { heap.clear(); size = 0; }
    
    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap.get(parent).compareTo(heap.get(index)) <= 0) break;
            swap(parent, index);
            index = parent;
        }
    }
    
    private void bubbleDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;
            if (left < size && heap.get(left).compareTo(heap.get(smallest)) < 0) smallest = left;
            if (right < size && heap.get(right).compareTo(heap.get(smallest)) < 0) smallest = right;
            if (smallest == index) break;
            swap(index, smallest);
            index = smallest;
        }
    }
    
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}