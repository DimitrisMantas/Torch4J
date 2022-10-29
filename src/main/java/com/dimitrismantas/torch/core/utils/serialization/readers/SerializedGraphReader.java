package com.dimitrismantas.torch.core.utils.serialization.readers;

import com.dimitrismantas.torch.core.graph.shortestpaths.utils.exceptions.EqualEndpointException;
import com.dimitrismantas.torch.core.utils.serialization.graph.DeserializedGraph;
import com.dimitrismantas.torch.core.utils.serialization.graph.DeserializedVertex;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public final class SerializedGraphReader {
    private MappedByteBuffer buffer;

    public SerializedGraphReader(final String filename) {
        read(filename);
    }

    /**
     * Determines if two given vertices are equal to each other.
     *
     * @param a The first vertex.
     * @param b The second vertex.
     * @return {@code true} of the numbers are almost equal; {@code false} otherwise.
     * @see EqualEndpointException
     */
    public static boolean equals(final DeserializedVertex a, final DeserializedVertex b) {
        return a == b || (a != null && a.lbl() == b.lbl());
    }

    private void read(final String filename) {
        try (final RandomAccessFile f = new RandomAccessFile(filename, "rw"); final FileChannel channel = f.getChannel()) {
            this.buffer = channel.map(FileChannel.MapMode.PRIVATE, 0, channel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DeserializedGraph getGraph() {
        return DeserializedGraph.getRootAsDeserializedGraph(buffer);
    }
}
