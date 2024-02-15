package priorityqueues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 0;
    List<PriorityNode<T>> items;
    int size;
    HashMap<T, Integer> container;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        container = new HashMap<>();
        size = 0;
    }

    /**
     * Adds an item with the given priority value.
     *
     * @throws IllegalArgumentException if item is null or is already present in the PQ
     */
    @Override
    public void add(T item, double priority) {
        if (item == null || contains(item)) {
            throw new IllegalArgumentException();
        }
        PriorityNode<T> addNode = new PriorityNode<>(item, priority); // creates new priority node object
        container.put(addNode.getItem(), size); // adds node to container tracker
        items.add(size, addNode); // adds node to open space
        size++;
        int curr = size - 1;
        int parent = (curr - 1) / 2;
        while ((curr > 0) && (items.get(curr).getPriority() < items.get(parent).getPriority())) {
            // PriorityNode<T> parentNode = items.get(parent);
            // items.set(parent, addNode); // swap
            // items.set(curr, parentNode);
            swap(parent, curr);
            curr = parent; // sets cur to parent position
            parent = (curr - 1) / 2;
        }
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) { // a = parent or child, b = curr
        PriorityNode<T> temp1 = items.get(a);
        items.set(a, items.get(b));
        container.put(items.get(b).getItem(), a);
        items.set(b, temp1);
        container.put(temp1.getItem(), b);
    }

    @Override
    // O(logn) time
    public boolean contains(T item) {
        return container.containsKey(item);
    }

    /**
     * Returns the item with the least-valued priority.
     *
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T peekMin() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        PriorityNode<T> getNode = items.get(0);
        return getNode.getItem();
    }

    /**
     * Removes and returns the item with the least-valued priority.
     *
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T removeMin() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        PriorityNode<T> getNode = items.get(0);
        // last node goes to 0
        PriorityNode<T> lastNode = items.get(size - 1);
        items.set(0, lastNode); // sets root to lastNode
        container.put(items.get(size - 1).getItem(), 0);
        items.remove(size - 1);
        size--;
        container.remove(getNode.getItem()); // removes from hashmap
        if (size > 1) {
            int curr = 0; // tracks the current node position
            swapDown(curr);
        }

        return getNode.getItem();
        // return items.get(0).getItem();
    }

    public void swapDown(int currChildCount) {
        int pathIndex = holder(currChildCount);
        while (pathIndex < size) {
            if (items.get(currChildCount).getPriority() > items.get(pathIndex).getPriority()) {
                swap(pathIndex, currChildCount);
            } else {
                break;
            }
            currChildCount = pathIndex;
            pathIndex = holder(currChildCount);
        }
    }

    public int holder(int cur) { // chooses which index is the smallest, left & right as parameters
        if (((cur * 2) + 2) >= size) {
            return (cur * 2) + 1;
        }
        if (items.get((cur * 2) + 1).getPriority() < items.get((cur * 2) + 2).getPriority()) {
            return (cur * 2) + 1;
        } else {
            return (cur * 2) + 2;
        }
    }


    /**
     * Changes the priority of the given item.
     *
     * @throws NoSuchElementException if the item is not present in the PQ
     */
    @Override
    public void changePriority(T item, double priority) {
        // ODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }
}
