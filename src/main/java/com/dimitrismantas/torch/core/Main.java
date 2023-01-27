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
package com.dimitrismantas.torch.core;

import com.dimitrismantas.torch.core.graph.Graph;
import com.dimitrismantas.torch.core.graph.Path;
import com.dimitrismantas.torch.core.graph.Vertex;
import com.dimitrismantas.torch.core.graph.shortestpath.GenericLabelSettingShortestPathAlgorithm;
import com.dimitrismantas.torch.core.graph.spatial.NearestNeighborSearch;
import com.dimitrismantas.torch.core.utils.serial.ALTDataReader;
import com.dimitrismantas.torch.core.utils.serial.GraphReader;

import static com.dimitrismantas.torch.core.graph.shortestpath.OptimizationMode.MINIMUM_DISTANCE;

public class Main {
    public static void main(String[] args) {
        final Graph graph = new GraphReader("src/main/resources/grc/grc_graph_8500875_8809699.bin").getGraph();
        final ALTDataReader reader1 = new ALTDataReader("src/main/resources/grc/alt/grc_alt_dist_2.bin");
        final ALTDataReader reader2 = new ALTDataReader("src/main/resources/grc/alt/grc_alt_time_2.bin");
        final double[] o = {37.9838, 23.7275};
        final double[] d = {40.6401, 22.9444};
        final NearestNeighborSearch NNS = new NearestNeighborSearch(graph);
        final Vertex s = NNS.run(o[0], o[1]);
        final Vertex t = NNS.run(d[0], d[1]);
        final GenericLabelSettingShortestPathAlgorithm ShortestPathAlgorithm = new GenericLabelSettingShortestPathAlgorithm(graph, reader1);
        final long t0 = System.nanoTime();
        Path shortestPath = ShortestPathAlgorithm.run(s, t, MINIMUM_DISTANCE);
        System.out.println((System.nanoTime() - t0) * 1e-6);
        System.out.println(shortestPath.getEndpoints().size());
        System.out.println(shortestPath.getLength());
        System.out.println(shortestPath.getTravelTime());
    }
}