package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
//import java.util.*;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 100;
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;
    int sizeMap;

    // You may add extra fields or helper methods though!

    /**
     * Constructs a new ArrayMap with default initial capacity.
     */
    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ArrayMap with the given initial capacity (i.e., the initial
     * size of the internal array).
     *
     * @param initialCapacity the initial capacity of the ArrayMap. Must be > 0.
     */
    public ArrayMap(int initialCapacity) {
        //SimpleEntry<K, V>[] entry;
        this.entries = this.createArrayOfEntries(initialCapacity);
        this.sizeMap = 0;
        // for (SimpleEntry<K, V> oneEntry : entries) {
        //     System.out.println(oneEntry);
        // }
        //this.entries = entries;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {
        V value = null;
        for (int i = 0; i < sizeMap; i++) {
            if (entries[i] != null && entries[i].getKey().equals(key)) {
                value = entries[i].getValue();
            }
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        V oldValue = null;
        boolean update = true;
        SimpleEntry<K, V> newValue = new SimpleEntry<K, V>(key, value);
        if (sizeMap >= entries.length) {
            SimpleEntry<K, V>[] copy = createArrayOfEntries(sizeMap * 2);
            for (int i = 0; i < sizeMap; i++) {
                copy[i] = entries[i];
            }
            entries = copy;
        }
        for (int i = 0; i < sizeMap; i++) {
            if (entries[i] != null && entries[i].getKey().equals(key)) {
                oldValue = entries[i].getValue();
                entries[i].setValue(value);
                update = false;
            }
        }
        if (update) {
            entries[sizeMap] = newValue;
            sizeMap++;
        }
        return oldValue;
    }

    @Override
    public V remove(Object key) { // return mapping for key if it present
        // V removeValue = null; // returns remove value
        // if (sizeMap == 1) {
        //     entries[0] = null;
        // } else if (sizeMap != 0) {
        for (int i = 0; i < sizeMap; i++) { // make sure to check for last entry, could be < or equal to
            if (entries[i] != null && entries[i].getKey().equals(key)) {
                V removeValue = entries[i].getValue();
                entries[i] = entries[sizeMap - 1];
                entries[sizeMap - 1] = null;
                sizeMap--;
                return removeValue; // stops once found
            }
        }
        //}
        return null;
    }

    @Override
    public void clear() {
        for (int i = 0; i <= sizeMap; i++) {
            entries[i] = null;
        }
        sizeMap = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean found = false;
        for (int i = 0; i < sizeMap; i++) { // i = 0, 1
            if (entries[i] != null && entries[i].getKey().equals(key)) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public int size() {
        return sizeMap;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: You may or may not need to change this method, depending on whether you
        // add any parameters to the ArrayMapIterator constructor.
        return new ArrayMapIterator<>(this.entries, sizeMap);
    }

    // TODO: after you implement the iterator, remove this toString implementation
    // Doing so will give you a better string representation for assertion errors the debugger.
    @Override
    public String toString() {
        return super.toString();
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        // You may add more fields and constructor parameters
        private int index;
        private int sizeMap2;

        public ArrayMapIterator(SimpleEntry<K, V>[] entries, int sizeMap) {
            this.entries = entries;
            this.sizeMap2 = sizeMap;
            this.index = -1;
        }

        @Override
        public boolean hasNext() {
            // if (index >= sizeMap2) {
            //     return false;
            // } else {
            //     return true;
            // }
            // if (entries[index + 1] != null) {
            //     return false;
            // } else {
            //     return true;
            // }
            return entries[index + 1] != null;
            //throw new UnsupportedOperationException("Not implemented yet.");
        }

        @Override
        public Map.Entry<K, V> next() {
            index++;
            if (index >= sizeMap2) { //index >= sizeMap2
                throw new NoSuchElementException();
            }
            //index++;
            return entries[index];
            // TODO: replace this with your code
            //throw new UnsupportedOperationException("Not implemented yet.");
        }
    }
}
