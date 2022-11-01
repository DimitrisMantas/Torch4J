package com.dimitrismantas.torch.core.utils.serialization.readers;

import com.dimitrismantas.torch.core.graph.Vertex;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public final class ALTDataReader {
    private static final int INT_SIZE_BYTES = 4;
    private static final int LNG_SIZE_BYTES = 8;
    private MappedByteBuffer buffer;
    private int numLandmarks;
    private int numCostsPerVertex;

    public ALTDataReader(final String filename) {
        read(filename);
    }

    private void read(String filename) {
        try (final RandomAccessFile f = new RandomAccessFile(filename, "rw"); final FileChannel fileChannel = f.getChannel()) {
            this.buffer = fileChannel.map(FileChannel.MapMode.PRIVATE, 0, fileChannel.size());
            this.buffer.order(ByteOrder.LITTLE_ENDIAN);
            this.numLandmarks = (int) this.buffer.getLong(0);
            this.numCostsPerVertex = 2 * this.numLandmarks;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] getCosts(final Vertex v) {
        final int[] costs = new int[this.numCostsPerVertex];
        for (int i = 0; i < this.numCostsPerVertex; i++) {
            costs[i] = this.buffer.getInt(i * INT_SIZE_BYTES + v.lbl() * this.numCostsPerVertex * INT_SIZE_BYTES + this.numLandmarks * INT_SIZE_BYTES + LNG_SIZE_BYTES);
        }
        return costs;
    }

    public int getEstimatedCost(final Vertex v, final Vertex t) {
        final int[] srcDists = getCosts(v);
        final int[] trgDists = getCosts(t);
        int minDist = 0;
        for (int i = 0; i < this.numCostsPerVertex; i++) {
            int lowerBoundDist = trgDists[i] - srcDists[i];
            if (minDist < lowerBoundDist) {
                minDist = lowerBoundDist;
            }
        }
        return minDist;
    }
}