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

import com.dimitrismantas.torch.core.graph.Graph;
import com.dimitrismantas.torch.core.graph.Vertex;
import com.dimitrismantas.torch.core.graph.shortestpaths.GenericLabelSettingShortestPathAlgorithm;
import com.dimitrismantas.torch.core.graph.shortestpaths.OptimizationMode;
import com.dimitrismantas.torch.core.utils.serialization.readers.ALTDataReader;
import com.dimitrismantas.torch.core.utils.serialization.readers.GraphReader;
import org.junit.jupiter.api.Test;

public final class ALTDataReaderTest {
    @Test
    public void main() {
        final Graph graph = new GraphReader("src/main/resources/grc/grc_graph_8500875_8809699.dat").getGraph();
        final ALTDataReader reader1 = new ALTDataReader("src/main/resources/grc/alt/grc_alt_dist_16.dat");
        final ALTDataReader reader2 = new ALTDataReader("src/main/resources/grc/alt/grc_alt_trvl_16.dat");
        final GenericLabelSettingShortestPathAlgorithm calculator = new GenericLabelSettingShortestPathAlgorithm(graph, reader1, reader2);
        for (int i = 0; i < 10; i++) {
            final Vertex s = graph.vertices((int) (Math.random() * 8500874));
            final Vertex t = graph.vertices((int) (Math.random() * 8500874));
            assert reader1.getEstimatedCost(s, t) <= calculator.run(s, t, OptimizationMode.MINIMUM_DISTANCE).getLength();
        }
        for (int i = 0; i < 10; i++) {
            final Vertex s = graph.vertices((int) (Math.random() * 8500874));
            final Vertex t = graph.vertices((int) (Math.random() * 8500874));
            assert reader2.getEstimatedCost(s, t) <= calculator.run(s, t, OptimizationMode.MINIMUM_TRAVEL_TIME).getLength();
        }
    }
}
