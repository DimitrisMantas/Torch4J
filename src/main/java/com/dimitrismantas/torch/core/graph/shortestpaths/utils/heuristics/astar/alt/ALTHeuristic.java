package com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.astar.alt;

import com.dimitrismantas.torch.core.graph.Vertex;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.AbstractHeuristic;
import com.dimitrismantas.torch.core.utils.serialization.readers.ALTDataReader;

public final class ALTHeuristic extends AbstractHeuristic {
    private ALTDataReader reader;

    public ALTHeuristic() {
    }

    public int estimateCostToReferenceVertex(final Vertex v, final short numExecutions) {
        // This assumes that each run will have a different target. So successive queries whose targets are really close
        // and possibly the same are not benefited from it.
//        System.out.println(v.numInitialized());
//        System.out.println(numExecutions);
//        System.out.println("HERE1");
        if (v.numInitialized() != numExecutions) {
            v.mutateEstimatedCostToTarget(this.reader.getEstimatedCost(v, this.refV));
//            System.out.println("HERE2");
        }
        return v.estimatedCostToTarget();
    }

    public void setReader(final ALTDataReader reader) {
        this.reader = reader;
    }
}
