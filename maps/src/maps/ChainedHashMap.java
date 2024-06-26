package maps;

import java.util.Iterator;
import java.util.Map;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    // ODO: define reasonable default values for each of the following three fields
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = .75; //1 this is a fraction
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 10; // 100 index size
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 10; // 100 arrayMap size
    double resizingLoadFactorThreshold;
    int initialChainCount;
    int initialChainCapacity;
    double elements; // might change to hashet element
    AbstractIterableMap<K, V> holder; // store key - value pairings for rehash
    boolean input; // input = checks whether I should add new stuff to holder


    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;

    // You're encouraged to add extra fields (and helper methods) though!

    /**
     * Constructs a new ChainedHashMap with default resizing load factor threshold,
     * default initial chain count, and default initial chain capacity.
     */
    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    /**
     * Constructs a new ChainedHashMap with the given parameters.
     *
     * @param resizingLoadFactorThreshold the load factor threshold for resizing. When the load factor
     *                                    exceeds this value, the hash table resizes. Must be > 0.
     * @param initialChainCount the initial number of chains for your hash table. Must be > 0.
     * @param chainInitialCapacity the initial capacity of each ArrayMap chain created by the map.
     *                             Must be > 0.
     */
    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        this.resizingLoadFactorThreshold = resizingLoadFactorThreshold;
        this.initialChainCapacity = chainInitialCapacity;
        this.chains = createArrayOfChains(initialChainCount);
        this.holder = createChain(chainInitialCapacity);
        // ODO: replace this with your code
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    @Override
    public V get(Object key) {
        V value = null;
        for (int i = 0; i < chains.length; i++) {
            if (chains[i] != null) {
                AbstractIterableMap<K, V> arrContains = chains[i]; // sets array map to something i can work with
                Iterator<Entry<K, V>> iterator = arrContains.iterator(); // iterator maker
                K keyCheck;
                while (iterator.hasNext()) { // checks through chain
                    keyCheck = iterator.next().getKey();
                    if (keyCheck.equals(key)) {
                        value = arrContains.get(keyCheck); //might need to check if this works
                    }
                }
            }
        }
        return value;
        // ODO: replace this with your code
    }

    @Override
    public V put(K key, V value) {
        input = true;
        // check loading factor = check, if greater = resize (must double chains) and rehash everything
        if ((elements / chains.length) >= DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD) {
            input = false; // no need to put any new things in holder arraymap
            // make a copy of all elements in array map
            // resize (need to check and see if I need to resize when it is less than load factor and not enough space)
            AbstractIterableMap<K, V>[] fresh = createArrayOfChains(chains.length * 2);
            chains = fresh; // set chains to this new empty, fresh array
            Iterator<Entry<K, V>> iteratorH = holder.iterator();
            V reload = null; // variable to hold the value of putt
            while (iteratorH.hasNext()) { // might need to look at current chain field
                K keyH = iteratorH.next().getKey();
                V valueH = iteratorH.next().getValue();
                reload = putter(keyH, valueH);
            }
            holder.clear(); // clears the array after I put everything in
            return reload; // i want it to keep iterating and adding from holder to new hash-chained map
        }
        return putter(key, value);
    }

    public V putter(K key, V value) {
        V oldValue = null;
        int hashCode;
        if (key == null) { // haschode for null keys
            hashCode = 0;
        } else {
            hashCode = key.hashCode();
        }
        int index = Math.abs(hashCode) % chains.length; // creates index
        if (chains[index] != null) { // there is already an array map (chain) in that index
            AbstractIterableMap<K, V> arrFound = chains[index]; // sets array map to something i can work with
            Iterator<Entry<K, V>> iterator = arrFound.iterator(); // iterator maker
            while (iterator.hasNext()) { // might need to look at current chain field
                oldValue = iterator.next().getValue();
            }
            chains[index].put(key, value); // puts another value in the chain
            if (input) {
                holder.put(key, value); // add key-value pair to holder array
            }
            elements++; // increment key-value pairings
            return oldValue; // may or may not use this
        } else { // no array map index, create one
            AbstractIterableMap<K, V> arr = createChain(DEFAULT_INITIAL_CHAIN_CAPACITY);
            arr.put(key, value); // puts in the key and value in it array
            if (input) {
                holder.put(key, value); // add key-value pair to holder array
            }
            chains[index] = arr; // sets chain[index] = new chain
            elements++;
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        // ODO: replace this with your code
        V value = null;
        for (int i = 0; i < chains.length; i++) {
            if (chains[i] != null) {
                AbstractIterableMap<K, V> arrContains = chains[i]; // sets array map to something i can work with
                Iterator<Entry<K, V>> iterator = arrContains.iterator(); // iterator maker
                K keyCheck;
                while (iterator.hasNext()) { // checks through chain
                    keyCheck = iterator.next().getKey();
                    if (keyCheck.equals(key)) {
                        value = arrContains.get(keyCheck); //might need to check if this works
                        arrContains.remove(keyCheck);
                    }
                }
            }
        }
        return value;
    }

    @Override
    public void clear() {
        for (int i = 0; i < chains.length; i++) {
            chains[i] = null;
        }
        // ODO: replace this with your code
    }

    @Override
    public boolean containsKey(Object key) {
        boolean found = false;
        for (int i = 0; i < chains.length; i++) {
            if (chains[i] != null) {
                AbstractIterableMap<K, V> arrContains = chains[i]; // sets array map to something i can work with
                Iterator<Entry<K, V>> iterator = arrContains.iterator(); // iterator maker
                K keyCheck;
                while (iterator.hasNext()) { // checks through chain
                    keyCheck = iterator.next().getKey();
                    if (keyCheck.equals(key)) {
                        found = true;
                    }
                }
            }
        }
        return found;
        // go through the chains; if the index is available go into that index and iterate through the chain
        // ODO: replace this with your code
    }

    @Override
    public int size() {
        return chains.length;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains);
    }

    // ODO: after you implement the iterator, remove this toString implementation
    // Doing so will give you a better string representation for assertion errors the debugger.
    @Override
    public String toString() {
        return super.toString();
    }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        // You may add more fields and constructor parameters
        private int index; // tracks the chain count array
        private boolean inChain;
        private int upperLimit;

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            this.index = -1;
            this.inChain = false;
            this.upperLimit = 1;
        }

        @Override
        public boolean hasNext() {
            if (index + 1 >= chains.length) {
                return false;
            }
            // need one to go in chaining
            return false;
        }

        // go through the chain


        @Override
        public Map.Entry<K, V> next() {
            if (!inChain) {
                index++;
            }
            if (chains[index] != null) {
                AbstractIterableMap<K, V> arrContains = chains[index]; // need to count size
                Iterator<Entry<K, V>> iterator = arrContains.iterator();
                int size = arrContains.size();
                int indexInChain;
                for (indexInChain = 0; indexInChain < size; indexInChain++) { // goes through the arraymap chain
                    if (indexInChain + 1 == upperLimit) { // takes each value
                        upperLimit++;
                        return iterator.next();
                    } else {
                        iterator.next();
                    }
                }
            }
            return null;
        }
    }
}
