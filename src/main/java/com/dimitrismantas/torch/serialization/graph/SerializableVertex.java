/*
 * Copyright 2021-2022 Dimitris Mantas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dimitrismantas.torch.serialization.graph;

import com.dimitrismantas.torch.core.utils.annotations.EPSG4326;

import java.util.ArrayList;
import java.util.List;

public final class SerializableVertex {
    private final int lbl;
    @EPSG4326
    private final float lat;
    @EPSG4326
    private final float lon;
    private final List<SerializableEdge> outgoingEdges = new ArrayList<>();
    private final int predLbl = -1;
    private final int costFromS = Integer.MAX_VALUE;
    private final int costToT = -1;
    private final short numInitialized = 0;

    SerializableVertex(final int lbl, final float lat, final float lon) {
        this.lbl = lbl;
        this.lat = lat;
        this.lon = lon;
    }

    public int getLabel() {
        return this.lbl;
    }

    public float getLatitude() {
        return this.lat;
    }

    public float getLongitude() {
        return this.lon;
    }

    public short getNumInitialized() {
        return this.numInitialized;
    }

    public int getPredLbl() {
        return this.predLbl;
    }

    public int getCostFromS() {
        return this.costFromS;
    }

    public List<SerializableEdge> getOutEdges() {
        return this.outgoingEdges;
    }

    public void addOutboundEdge(final SerializableEdge edge) {
        this.outgoingEdges.add(edge);
    }

    public int getCostToT() {
        return this.costToT;
    }
}
