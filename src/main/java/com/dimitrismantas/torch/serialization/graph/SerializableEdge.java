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

public final class SerializableEdge {
    private final int vLbl;
    private final short length;
    private final short travelTime;

    public SerializableEdge(final int vLbl, final short length, final short travelTime) {
        this.vLbl = vLbl;
        this.length = length;
        this.travelTime = travelTime;
    }

    public int getTailLabel() {
        return this.vLbl;
    }

    public short getLength() {
        return this.length;
    }

    public short getTravelTime() {
        return this.travelTime;
    }
}
