package graphs.minspantrees;

import disjointsets.DisjointSets;
import disjointsets.UnionBySizeCompressingDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        // return new QuickFindDisjointSets<>();
        return new UnionBySizeCompressingDisjointSets<>();
        /*
        Disable the line above and enable the one below after you've finished implementing
        your `UnionBySizeCompressingDisjointSets`.
         */
        // return new UnionBySizeCompressingDisjointSets<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        // Here's some code to get you started; feel free to change or rearrange it if you'd like.

        // sort edges in the graph in ascending weight order
        List<E> edges = new ArrayList<>(graph.allEdges());
        edges.sort(Comparator.comparingDouble(E::weight));

        DisjointSets<V> disjointSets = createDisjointSets();
        List<E> mstEdges = new ArrayList<>();
        double mstWeight = 0.0;
        // loop through vertices and add
        Collection<V> vertices = graph.allVertices();
        for (V v : vertices) {
            disjointSets.makeSet(v);
        }

        for (E edge : edges) {
            V source = edge.from();
            V destination = edge.to();

            int root1 = disjointSets.findSet(source);
            int root2 = disjointSets.findSet(destination);

            if (root1 != root2) {
                mstEdges.add(edge);
                mstWeight += edge.weight();
                disjointSets.union(source, destination);
            }
        }

        if (mstEdges.size() < graph.allVertices().size() - 1) {
            // until find min span tree (once all vertices have been connected)
            // success = edges connects all nodes (no loops = acyclic)
            // failure = goes through all edges and all nodes don't connect, not mst
            return new MinimumSpanningTree.Failure<>();
        } else {
            return new MinimumSpanningTree.Success<>(mstEdges);
        }
    }
}
