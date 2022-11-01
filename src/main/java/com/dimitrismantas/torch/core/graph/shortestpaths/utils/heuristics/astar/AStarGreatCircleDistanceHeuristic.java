/*
 * Torch is a model, open-source Android application for optimal routing
 * in offline mobile devices.
 * Copyright (C) 2021-2022  DIMITRIS(.)MANTAS(@outlook.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.astar;

import com.dimitrismantas.torch.core.graph.Vertex;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.AbstractHeuristic;
import com.dimitrismantas.torch.core.math.HaversineFormula;

/**
 * A heuristic that is used to estimate the great circle distance to a reference vertex.
 *
 * @author Dimitris Mantas
 * @version 1.0.0
 * @since 1.0.0
 */
public final class AStarGreatCircleDistanceHeuristic extends AbstractHeuristic {
    public AStarGreatCircleDistanceHeuristic(final Vertex refV) {
        super(refV);
    }

    @Override
    public int estimateCostToReferenceVertex(final Vertex v, final short numExecutions) {
        if (v.numInitialized() != numExecutions) {
            v.mutateEstimatedCostToTarget((int) HaversineFormula.run(v.lat(), v.lon(), this.refV.lat(), this.refV.lon()));
        }
        return v.estimatedCostToTarget();
    }
}
