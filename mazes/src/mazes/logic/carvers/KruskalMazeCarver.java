package mazes.logic.carvers;

import graphs.BaseEdge;
import graphs.EdgeWithData;
import graphs.minspantrees.MinimumSpanningTree;
import graphs.minspantrees.MinimumSpanningTreeFinder;
import mazes.entities.Room;
import mazes.entities.Wall;
import mazes.logic.MazeGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Carves out a maze based on Kruskal's algorithm.
 */
public class KruskalMazeCarver extends MazeCarver {
    MinimumSpanningTreeFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder;
    private final Random rand;

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random();
    }

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder,
                             long seed) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random(seed);
    }

    @Override
    protected Set<Wall> chooseWallsToRemove(Set<Wall> walls) {
        Collection<EdgeWithData<Room, Wall>> edges = new ArrayList<>();
        for (Wall wall : walls) {
            Room fromVertex = wall.getRoom1();
            Room toVertex = wall.getRoom2();
            double weight = wall.getDistance() * rand.nextDouble();

            EdgeWithData<Room, Wall> edge = new EdgeWithData<>(fromVertex, toVertex, weight, wall);
            edges.add(edge);
        }

        MazeGraph mGraph = new MazeGraph(edges);

        Set<Wall> weightedWalls = new HashSet<Wall>();
        MinimumSpanningTree<Room, EdgeWithData<Room, Wall>> minTree = minimumSpanningTreeFinder.findMinimumSpanningTree(mGraph);
        for (EdgeWithData<Room, Wall> edge : minTree.edges()) {
            weightedWalls.add(edge.data());
        }

        return weightedWalls;
    }
}
