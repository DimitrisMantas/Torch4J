package com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.astar.alt;

import com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.AbstractHeuristic;
import com.dimitrismantas.torch.core.utils.serialization.graph.DeserializedVertex;
import com.dimitrismantas.torch.core.utils.serialization.readers.ALTDataReader;

public final class ALTHeuristic extends AbstractHeuristic {
    private final ALTDataReader reader;

    public ALTHeuristic(final ALTDataReader reader, final DeserializedVertex refV) {
        super(refV);
        this.reader = reader;
    }

    public int estimateCostToReferenceVertex(final DeserializedVertex v) {
        return this.reader.getEstimatedCost(v, this.refV);
    }
}
