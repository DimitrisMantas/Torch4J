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
import com.dimitrismantas.torch.core.math.HaversineFormula;

public final class AStarDistanceHeuristic extends AbstractHeuristic {
    public AStarDistanceHeuristic(final GenericLabelSettingShortestPathAlgorithm genericAlgorithm) {
        super(genericAlgorithm);
    }

    @Override
    public int estimateCostToReferenceVertex(final Vertex v) {
        if (v.numInitialized() != this.genericAlgorithm.numExecutions) {
            v.mutateEstimatedCostToTarget((int) HaversineFormula.run(v.lat(), v.lon(), this.refV.lat(), this.refV.lon()));
        }
        return v.estimatedCostToTarget();
    }
}
