package com.dimitrismantas.torch.core.utils.serialization.readers;

import com.dimitrismantas.torch.core.graph.Graph;
import com.dimitrismantas.torch.core.graph.Vertex;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.exceptions.EqualEndpointException;

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
     * @see EqualEndpointException
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
