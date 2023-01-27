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

/**
 * A naive implementation of various mathematical functions using standard methods provided by the built-in {@link Math} module.
 *
 * @author Dimitris Mantas
 * @version 1.1.0
 * @see HaversineFormula
 * @since 1.0.0
 */
public final class SupplementalMath {
    private static final double INV_LOG2 = 1.4426950408889634;

    private SupplementalMath() {
    }

    /**
     * Computes the <a href="https://en.wikipedia.org/wiki/Versine#Haversine">haversine</a> of an angle.
     *
     * @param theta the angle in radians
     * @return the haversine of that angle
     * @see #ahav(double)
     */
    public static double hav(final double theta) {
        return pow2(Math.sin(0.5D * theta));
    }

    /**
     * Computes the inverse <a href="https://en.wikipedia.org/wiki/Versine#Haversine">haversine</a> of an angle.
     *
     * @param theta the angle in radians
     * @return the inverse haversine of that angle
     * @see #hav(double)
     */
    public static double ahav(final double theta) {
        return 2.0D * Math.asin(Math.sqrt(theta));
    }

    /**
     * Computes the n-th power of two.
     *
     * @param n the power to which two should be raised
     * @return two to that power
     * @see #log2(double)
     */
    public static double exp2(final double n) {
        return Math.pow(2.0D, n);
    }

    /**
     * Computes the binary logarithm of a number.
     *
     * @param x the number
     * @return the binary logarithm of the number
     * @see #exp2(double)
     */
    public static double log2(final double x) {
        return Math.log(x) * INV_LOG2;
    }

    /**
     * Computes the square of a number.
     *
     * @param x the number
     * @return that number raised to the power of two
     */
    public static double pow2(final double x) {
        return Math.pow(x, 2D);
    }

    /**
     * Determines of two numbers are equal or at least almost equal to each other.
     *
     * @param a the first number
     * @param b the second number
     * @return {@code true} if {@code a == b} or {@code a ≈ b}; {@code false} otherwise.
     * @apiNote this method is not commutative nor transitive (i.e., if {@code almostEquals(a, b)} and {@code almostEquals(a, c)}, then it is not necessarily the case that {@code almostEquals(b, a)}, {@code almostEquals(c, a)} and {@code almostEquals(a, c)})
     * @implNote in contrast to various other implementations of this method, it is always the case that {@code almostEquals(±0, ∓0)} and {@code almostEquals(NaN, NaN)}
     * @see Math#ulp(double)
     */
    public static boolean almostEquals(final double a, final double b) {
        final double eps = Math.max(Math.ulp(a), Math.ulp(b));
        return a == b || Math.abs(a - b) <= eps;
    }
}
