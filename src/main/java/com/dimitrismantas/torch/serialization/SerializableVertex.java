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
package com.dimitrismantas.torch.serialization;

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
