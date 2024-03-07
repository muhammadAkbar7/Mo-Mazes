package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        ExtrinsicMinPQ<V> minPQ = createMinPQ();
        Map<V, E> edgeTo = new HashMap<>(); // Shortest Paths Tree
        Map<V, Double> distTo = new HashMap<>(); // Map to store distances from the start vertex to each vertex
        Set<V> known = new HashSet<>(); // Set to keep track of vertices whose shortest paths are already determined

        // like min heap, distance as priority, find shortest path, update priority distance 1 and 2
        // initilaize
        distTo.put(start, 0.0);
        // Initialize distances to all vertices as positive infinity except for the start vertex
        // for (V vertex : graph.vertices()) {
        //     if (vertex.equals(start))
        //         distTo.put(vertex, 0.0); // Distance from start to itself is 0
        //     else
        //         distTo.put(vertex, Double.POSITIVE_INFINITY); // Distance to other vertices is initially infinite
        // }

        // Insert start vertex with distance 0 into the priority queue
        minPQ.add(start, 0.0);

        while (!minPQ.isEmpty()) {
            V u = minPQ.removeMin(); // Extract vertex with minimum distance from the priority queue
            // Stop if the destination vertex is reached
            if (u.equals(end)) {
                break;
            }
            double uDist = distTo.get(u); // Distance to current vertex
            known.add(u);
            //distTo.put(u, Double.POSITIVE_INFINITY);

            // // Stop if the destination vertex is reached
            // if (u.equals(end)) {
            //     break;
            // }

            // Update shortest paths to neighbors of the current vertex
            for (E edge : graph.outgoingEdgesFrom(u)) {
                V v = edge.to();
                double w = edge.weight();
                double oldDist;
                if (distTo.containsKey(v)) {
                    oldDist = distTo.get(v);
                } else {
                    oldDist = Double.POSITIVE_INFINITY;
                }
                // double oldDist = distTo.get(v);
                double newDist = distTo.get(u) + w; // Calculate new distance to the neighbor through the current vertex

                if (newDist < oldDist) { // If new distance is less than the previously known distance
                    distTo.put(v, newDist); // Update distance to the neighbor
                    edgeTo.put(v, edge); // Update shortest path tree with the new edge
                    if (!minPQ.contains(v)) {
                        minPQ.add(v, newDist);
                    } else {
                        minPQ.changePriority(v, newDist);
                    }
                    //minPQ.add(v, newDist); // Add neighbor to priority queue with its updated distance
                    // add something if we haven't seen it before, or change priority
                }
            }
        }

        return edgeTo; // Return the shortest paths tree
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    // q: do we have call this in the above method, so once we have the end vertex we reach we can call shortest path
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        List<E> copy = new ArrayList<E>(); // vertex end of the edge (to)
        V currVert = end;
        while (currVert != start) {
            E holder = spt.get(currVert); // add to list, use it to get precceding vertex
            copy.add(holder);
            currVert = holder.from();
        }
        Collections.reverse(copy);

        // edge cases
        // start and end are the same
        // end is not in the spt at all

        return new ShortestPath.Success<>(copy);

    }

}
