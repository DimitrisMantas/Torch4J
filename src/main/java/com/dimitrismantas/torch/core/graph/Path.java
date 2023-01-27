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
package com.dimitrismantas.torch.core.graph;

import java.util.ArrayList;
import java.util.List;

public final class Path {
    private final List<Vertex> endpoints = new ArrayList<>();
    private int length;
    private int travelTime;

    public Path() {
    }

    public List<Vertex> getEndpoints() {
        return endpoints;
    }

    public void addEndpoint(final Vertex enpoint) {
        this.endpoints.add(0, enpoint);
    }

    public int getLength() {
        return length;
    }

    public void incrementLength(final int length) {
        this.length += length;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void incrementTravelTime(final int travelTime) {
        this.travelTime += travelTime;
    }
}
