package com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.dijkstra;

import com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.AbstractHeuristic;
import com.dimitrismantas.torch.core.utils.serialization.graph.DeserializedVertex;

public class DijkstraHeuristic extends AbstractHeuristic {
    public DijkstraHeuristic(final DeserializedVertex refV) {
        super(refV);
    }

    public int estimateCostToReferenceVertex(final DeserializedVertex v) {
        return 0;
    }
}
