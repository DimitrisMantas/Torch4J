package com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics;

import com.dimitrismantas.torch.core.graph.shortestpaths.OptimizationMode;
import com.dimitrismantas.torch.core.graph.shortestpaths.ShortestPathAlgorithm;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.astar.AStarGreatCircleDistanceHeuristic;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.astar.AStarTravelTimeHeuristic;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.dijkstra.DijkstraHeuristic;
import com.dimitrismantas.torch.core.utils.serialization.graph.DeserializedVertex;

public final class HeuristicProvider {
    private HeuristicProvider() {

    }

    public AbstractHeuristic getHeuristic(final DeserializedVertex refV, ShortestPathAlgorithm shortestPathAlgorithm, final OptimizationMode optMode) {
        switch (shortestPathAlgorithm) {
            case DIJKSTRA:
                return new DijkstraHeuristic(refV);
            case STANDARD_A_STAR:
                switch (optMode) {
                    case MINIMIZE_DISTANCE:
                        return new AStarGreatCircleDistanceHeuristic(refV);
                    case MINIMIZE_TRAVEL_TIME:
                        return new AStarTravelTimeHeuristic(refV);
                }
            case ALT:
                // TODO - Fill this in.
                return null;
            default:
                throw new IllegalArgumentException("");
        }
    }
}
