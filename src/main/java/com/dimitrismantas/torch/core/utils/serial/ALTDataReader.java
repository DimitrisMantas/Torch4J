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
package com.dimitrismantas.torch.core.utils.serial;

import com.dimitrismantas.torch.core.graph.Vertex;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public final class ALTDataReader {
    private static final int INT_SIZE_BYTES = 4;
    private static final int LNG_SIZE_BYTES = 8;
    //    private MappedByteBuffer buffer;
    private ByteBuffer buffer;
    private int numLandmarks;
    private int numCostsPerVertex;

    public ALTDataReader(final String filename) {
        read(filename);
    }

    private void read(String filename) {
        try (final RandomAccessFile f = new RandomAccessFile(filename, "r"); final FileChannel fileChannel = f.getChannel()) {
            // Read the whole file into memory.
            byte[] b = new byte[(int) fileChannel.size()];
            f.read(b);
            buffer = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN);

            this.numLandmarks = (int) this.buffer.getLong(0);
            this.numCostsPerVertex = 2 * this.numLandmarks;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    private static void

    public int[] getCosts(final Vertex v) {
        final int[] costs = new int[this.numCostsPerVertex];
        for (int i = 0; i < this.numCostsPerVertex; i++) {
            costs[i] = this.buffer.getInt(i * INT_SIZE_BYTES + v.lbl() * this.numCostsPerVertex * INT_SIZE_BYTES + this.numLandmarks * INT_SIZE_BYTES + LNG_SIZE_BYTES);
        }
        return costs;
    }

    public int getEstimatedCost(final Vertex v, final Vertex t) {
        final int[] vCosts = getCosts(v);
        final int[] tCosts = getCosts(t);
        int minCost = 0;
        for (int i = 0; i < this.numCostsPerVertex; i++) {
            final int currCost = tCosts[i] - vCosts[i];
            if (minCost < currCost) {
                minCost = currCost;
            }
        }
        return minCost;
    }
}