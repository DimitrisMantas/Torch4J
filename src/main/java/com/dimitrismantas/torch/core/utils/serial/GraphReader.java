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

import com.dimitrismantas.torch.core.graph.Graph;
import com.dimitrismantas.torch.core.graph.Vertex;
import com.dimitrismantas.torch.core.graph.shortestpath.utils.exceptions.EqualPathEndpointsException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public final class GraphReader {
    private MappedByteBuffer buffer;

    public GraphReader(final String filename) {
        read(filename);
    }

    /**
     * Determines if two given vertices are equal to each other.
     *
     * @param a The first vertex.
     * @param b The second vertex.
     * @return {@code true} of the numbers are almost equal; {@code false} otherwise.
     * @see EqualPathEndpointsException
     */
    public static boolean equals(final Vertex a, final Vertex b) {
        return a == b || (a != null && a.lbl() == b.lbl());
    }

    private void read(final String filename) {
        try (final RandomAccessFile f = new RandomAccessFile(filename, "rw"); final FileChannel channel = f.getChannel()) {
            this.buffer = channel.map(FileChannel.MapMode.PRIVATE, 0, channel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Graph getGraph() {
        return Graph.getRootAsGraph(buffer);
    }
}
