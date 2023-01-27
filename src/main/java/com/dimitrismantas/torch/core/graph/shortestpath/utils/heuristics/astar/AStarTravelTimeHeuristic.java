/*
 * Copyright 2021-2023 Dimitris Mantas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dimitrismantas.torch.core.graph.shortestpath.utils.heuristics.astar;

import com.dimitrismantas.torch.core.graph.Vertex;
import com.dimitrismantas.torch.core.graph.shortestpath.GenericLabelSettingShortestPathAlgorithm;
import com.dimitrismantas.torch.core.graph.shortestpath.utils.heuristics.AbstractHeuristic;

public final class AStarTravelTimeHeuristic extends AbstractHeuristic {
    private static final double INV_AVG_SPEED = 0.036;
    private final AStarDistanceHeuristic distanceHeuristic;

    public AStarTravelTimeHeuristic(final GenericLabelSettingShortestPathAlgorithm genericAlgorithm) {
        this.distanceHeuristic = new AStarDistanceHeuristic(genericAlgorithm);
    }

    @Override
    public int estimateCostToReferenceVertex(final Vertex v) {
        if (v.numInitialized() != this.genericAlgorithm.numExecutions) {
            v.mutateEstimatedCostToTarget((int) (this.distanceHeuristic.estimateCostToReferenceVertex(v) * INV_AVG_SPEED));
        }
        return v.estimatedCostToTarget();
    }

    @Override
    public void setReferenceVertex(Vertex refV) {
        super.setReferenceVertex(refV);
        this.distanceHeuristic.setReferenceVertex(refV);
    }
}
