package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * A quick-union-by-size data structure with path compression.
 *
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers;
    private final HashMap<T, Integer> ids; // change to a hashamp?
    // private final HashMap<Integer, Integer> sizes;
    // private int size;

    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    public UnionBySizeCompressingDisjointSets() {
        this.ids = new HashMap<>();
        // this.sizes = new HashMap<>();
        this.pointers = new ArrayList<>();
        // this.size = 0;
    }

    @Override
    public void makeSet(T item) {
        if (!ids.containsKey(item)) {
            int newIndex = ids.size();
            pointers.add(-1); // root , index = -1
            ids.put(item, newIndex);
            // sizes.put(newIndex, 1);
            // this.size++;
        }
    }

    @Override
    public int findSet(T item) {
        if (this.ids.containsKey(item) == false) {
            throw new IllegalArgumentException();
        }
        Integer index = ids.get(item);
        if (index == null) {
            throw new IllegalArgumentException(item + " is not in any set.");
        }
        // int root = findRoot(index);
        // Perform path compression
        return compressPath(index);
    }

    private int findRoot(int index) {
        if (pointers.get(index) != index) {
            pointers.set(index, findRoot(pointers.get(index)));
        }
        return pointers.get(index);
    }

    private int compressPath(int index) { // get to the very top, reset stuff,
        int parent = index;
        while (this.pointers.get(parent) >= 0) { // might be a problem (while curr index >= 0 (non-negative)) while we're not at root
            //int parent = pointers.get(index); //
            parent = this.pointers.get(parent); // traversing path
            System.out.println("whileLoop1");
            // pointers.set(index, root);
            // index = parent;
        }
        int newRoot = parent; // we have the new root we stopped iterating at
        int curr = index;
        while (this.pointers.get(curr) >= 0) {
            parent = pointers.get(curr);
            pointers.set(curr, newRoot);
            curr = parent;
            System.out.println("whileLoop2");
        }
        return newRoot;
    }

    @Override
    public boolean union(T item1, T item2) {
        int root1 = findSet(item1);
        int root2 = findSet(item2);

        if (root1 == root2) {
            return false; // They are already in the same set
        }

        // Union by size
        int size1 = this.pointers.get(root1) * -1;
        int size2 = this.pointers.get(root2) * -1;
        if (size1 < size2) {
            pointers.set(root1, root2);
            this.pointers.set(root2, (size1 + size2) * -1);
            // sizes.put(root2, sizes.get(root1) + sizes.get(root2));
        } else {
            pointers.set(root2, root1);
            this.pointers.set(root1, (size1 + size2) * -1);
            // sizes.put(root1, sizes.get(root1) + sizes.get(root2));
        }
        return true;
    }
}
