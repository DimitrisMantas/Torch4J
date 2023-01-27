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
package com.dimitrismantas.torch.core.graph.spatial;

import com.dimitrismantas.torch.core.graph.Graph;
import com.dimitrismantas.torch.core.graph.Vertex;
import com.dimitrismantas.torch.core.math.HaversineFormula;
import com.dimitrismantas.torch.core.utils.annotations.EPSG4326;
import com.dimitrismantas.torch.core.utils.annotations.O;

/**
 * An implementation of a linear nearest neighbor search algorithm.
 *
 * @author Dimitris Mantas
 * @version 1.0.0
 * @since 1.0.0
 */
@O("n")
public final class NearestNeighborSearch {
    private final Graph graph;

    public NearestNeighborSearch(final Graph graph) {
        this.graph = graph;
    }

    /**
     * Finds the nearest neighbor to a given point on the surface of the Earth.
     *
     * @param lat The latitude of the point to be used during the search, in decimal degrees.
     * @param lon The latitude of the point to be used during the search, in decimal degrees.
     * @return The nearest neighbor to this point.
     */
    @EPSG4326
    public Vertex run(final double lat, final double lon) {
        // This is equivalent to setting match equal to null, since graph.vertices(-1) will throw a NullPointerException.
        int matchIndex = -1;
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < graph.verticesLength(); i++) {
            final Vertex matchCandidate = graph.vertices(i);
            final double currentDistance = HaversineFormula.run(lat, lon, matchCandidate.lat(), matchCandidate.lon());
            if (minDistance > currentDistance) {
                minDistance = currentDistance;
                matchIndex = i;
            }
        }
        return graph.vertices(matchIndex);
    }
}
