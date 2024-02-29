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
    private final Map<T, Integer> ids;
    private final Map<Integer, Integer> sizes;

    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    public UnionBySizeCompressingDisjointSets() {
        ids = new HashMap<>();
        sizes = new HashMap<>();
        pointers = new ArrayList<>();
        // ODO: replace this with your code
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void makeSet(T item) {
        // ODO: replace this with your code
        if (!ids.containsKey(item)) {
            int newIndex = pointers.size();
            pointers.add(newIndex);
            ids.put(item, newIndex);
            sizes.put(newIndex, 1);
        }
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public int findSet(T item) {
        Integer index = ids.get(item);
        if (index == null) {
            throw new IllegalArgumentException(item + " is not in any set.");
        }
        return findRoot(index);
        // ODO: replace this with your code
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    private int findRoot(int index) {
        if (pointers.get(index) != index) {
            pointers.set(index, findRoot(pointers.get(index))); // Path compression
        }
        return pointers.get(index);
    }

    @Override
    public boolean union(T item1, T item2) {
        int root1 = findSet(item1);
        int root2 = findSet(item2);

        if (root1 == root2) {
            return false; // They are already in the same set
        }

        // Union by size
        if (sizes.get(root1) < sizes.get(root2)) {
            pointers.set(root1, root2);
            sizes.put(root2, sizes.get(root1) + sizes.get(root2));
        } else {
            pointers.set(root2, root1);
            sizes.put(root1, sizes.get(root1) + sizes.get(root2));
        }

        return true;
    }
    // ODO: replace this with your code
    // throw new UnsupportedOperationException("Not implemented yet.");
}
