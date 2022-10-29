package com.dimitrismantas.torch;

import com.dimitrismantas.torch.core.graph.Path;
import com.dimitrismantas.torch.core.graph.shortestpaths.GenericShortestPathAlgorithm;
import com.dimitrismantas.torch.core.graph.spatialindexing.NearestNeighborSearch;
import com.dimitrismantas.torch.core.utils.serialization.graph.DeserializedGraph;
import com.dimitrismantas.torch.core.utils.serialization.graph.DeserializedVertex;
import com.dimitrismantas.torch.core.utils.serialization.readers.ALTDataReader;
import com.dimitrismantas.torch.core.utils.serialization.readers.SerializedGraphReader;

import static com.dimitrismantas.torch.core.graph.shortestpaths.OptimizationMode.MINIMIZE_DISTANCE;

public class Main {
    public static void main(String[] args) {
        final DeserializedGraph graph = new SerializedGraphReader("src/main/resources/grc/grc_graph_8500875_8809699.dat").getGraph();
        final ALTDataReader reader1 = new ALTDataReader("src/main/resources/grc/alt/grc_alt_dist_16.dat");
        final ALTDataReader reader2 = new ALTDataReader("src/main/resources/grc/alt/grc_alt_trvl_16.dat");

        final double[] o = {37.9838, 23.7275};
        final double[] d = {40.6401, 22.9444};

        final NearestNeighborSearch NNS = new NearestNeighborSearch(graph);
        final DeserializedVertex s = NNS.run(o[0], o[1]);
        final DeserializedVertex t = NNS.run(d[0], d[1]);

        final GenericShortestPathAlgorithm ShortestPathAlgorithm = new GenericShortestPathAlgorithm(graph, reader1, reader2);
        final long t0 = System.nanoTime();
        Path shortestPath = ShortestPathAlgorithm.run(s, t, MINIMIZE_DISTANCE);
        System.out.println((System.nanoTime() - t0) * 1e-6);
        System.out.println(shortestPath.getEndpoints().size());
        System.out.println(shortestPath.getLength());
        System.out.println(shortestPath.getTravelTime());
    }
}