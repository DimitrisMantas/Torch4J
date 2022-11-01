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
package com.dimitrismantas.torch.core.graph.spatialindexing;

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
