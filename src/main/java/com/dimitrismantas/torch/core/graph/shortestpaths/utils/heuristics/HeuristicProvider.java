package com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics;

import com.dimitrismantas.torch.core.graph.Vertex;
import com.dimitrismantas.torch.core.graph.shortestpaths.OptimizationMode;
import com.dimitrismantas.torch.core.graph.shortestpaths.ShortestPathAlgorithm;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.astar.AStarGreatCircleDistanceHeuristic;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.astar.AStarTravelTimeHeuristic;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.dijkstra.DijkstraHeuristic;

public final class HeuristicProvider {
    private HeuristicProvider() {
    }

    public AbstractHeuristic getHeuristic(final Vertex refV, ShortestPathAlgorithm shortestPathAlgorithm, final OptimizationMode optMode) {
        switch (shortestPathAlgorithm) {
            case DIJKSTRA:
                return new DijkstraHeuristic(refV);
            case STANDARD_A_STAR:
                switch (optMode) {
                    case MINIMIZE_DISTANCE:
                        return new AStarGreatCircleDistanceHeuristic(refV);
                    case MINIMIZE_TRAVEL_TIME:
                        return new AStarTravelTimeHeuristic(refV);
                    default:
                        throw new IllegalArgumentException(String.format("Failed to recognize optimization mode: %s", optMode));
                }
            case ALT:
                // TODO - Fill this in.
                return null;
            default:
                throw new IllegalArgumentException(String.format("Failed to recognize algorithm type: %s", shortestPathAlgorithm));
        }
    }
}
