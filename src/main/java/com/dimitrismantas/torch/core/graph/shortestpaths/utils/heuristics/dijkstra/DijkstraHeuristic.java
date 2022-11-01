package com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.dijkstra;

import com.dimitrismantas.torch.core.graph.Vertex;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.AbstractHeuristic;

public class DijkstraHeuristic extends AbstractHeuristic {
    public DijkstraHeuristic(final Vertex refV) {
        super(refV);
    }

    public int estimateCostToReferenceVertex(final Vertex v, final short numExecutions) {
//        if (v.numInitialized() != numExecutions) {
//            v.mutateEstimatedCostToTarget(0);
//        }
//        return v.estimatedCostToTarget();
        return 0;
    }
}
