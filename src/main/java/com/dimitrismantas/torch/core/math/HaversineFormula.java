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
package com.dimitrismantas.torch.core.math;

import com.dimitrismantas.torch.core.utils.annotations.EPSG4326;

/**
 * A naive implementation of the <a href="https://en.wikipedia.org/wiki/Haversine_formula">Haversine formula</a> using standard methods provided by the built-in {@link Math} module.
 *
 * @author Dimitris Mantas
 * @version 1.1.0
 * @see SupplementalMath
 * @since 1.0.0
 */
@EPSG4326
public final class HaversineFormula {
    private static final double MEAN_EARTH_RADIUS = 6371008.7714150598325213;

    private HaversineFormula() {
    }

    /**
     * Computes the great-circle distance between two  points on a sphere. This method can estimate the smallest possible distance between any pair of points of the surface of the Earth with a maximum error of 5% when compared to <a href="https://en.wikipedia.org/wiki/Vincenty%27s_formulae">Vincenty's formulae</a>.
     *
     * @param lat1 the latitude of the first point in decimal degrees
     * @param lon1 the longitude of the first point in decimal degrees
     * @param lat2 the latitude of the second point in decimal degrees
     * @param lon2 the longitude of the second point in decimal degrees
     * @return an estimate of the smallest possible distance from the first to the second point along the surface of the Earth in meters
     * @apiNote the order of the points does not influence the return value
     * @implNote non-critical checks have been omitted for performance reasons; it is for this reason that this method may not return exactly {@code 0.0} when the first and second points are equal, but a positive real value close to it
     * @see SupplementalMath#hav(double)
     * @see SupplementalMath#ahav(double)
     */
    public static double run(final double lat1, final double lon1, final double lat2, final double lon2) {
        final double dLat = Math.toRadians(lat1 - lat2);
        final double sLat = Math.toRadians(lat1 + lat2);
        final double dLon = Math.toRadians(lon1 - lon2);
        final double angDist = SupplementalMath.hav(dLat) + (1.0D - SupplementalMath.hav(dLat) - SupplementalMath.hav(sLat)) * SupplementalMath.hav(dLon);
        final double constrainedAngularDistance = Math.min(Math.max(angDist, 0), 1);
        return MEAN_EARTH_RADIUS * SupplementalMath.ahav(constrainedAngularDistance);
    }
}
