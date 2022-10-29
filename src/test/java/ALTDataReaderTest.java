import com.dimitrismantas.torch.core.graph.shortestpaths.GenericShortestPathAlgorithm;
import com.dimitrismantas.torch.core.graph.shortestpaths.OptimizationMode;
import com.dimitrismantas.torch.core.utils.serialization.graph.DeserializedGraph;
import com.dimitrismantas.torch.core.utils.serialization.graph.DeserializedVertex;
import com.dimitrismantas.torch.core.utils.serialization.readers.ALTDataReader;
import com.dimitrismantas.torch.core.utils.serialization.readers.SerializedGraphReader;
import org.junit.jupiter.api.Test;

public final class ALTDataReaderTest {
    @Test
    public void main() {
        final DeserializedGraph graph = new SerializedGraphReader("src/main/resources/grc/grc_graph_8500875_8809699.dat").getGraph();
        final ALTDataReader reader1 = new ALTDataReader("src/main/resources/grc/alt/grc_alt_dist_16.dat");
        final ALTDataReader reader2 = new ALTDataReader("src/main/resources/grc/alt/grc_alt_trvl_16.dat");

        final GenericShortestPathAlgorithm calculator = new GenericShortestPathAlgorithm(graph, reader1, reader2);

        for (int i = 0; i < 10; i++) {
            final DeserializedVertex s = graph.vertices((int) (Math.random() * 8500874));
            final DeserializedVertex t = graph.vertices((int) (Math.random() * 8500874));

            assert reader1.getEstimatedCost(s, t) <= calculator.run(s, t, OptimizationMode.MINIMIZE_DISTANCE).getLength();
        }

        for (int i = 0; i < 10; i++) {
            final DeserializedVertex s = graph.vertices((int) (Math.random() * 8500874));
            final DeserializedVertex t = graph.vertices((int) (Math.random() * 8500874));

            assert reader2.getEstimatedCost(s, t) <= calculator.run(s, t, OptimizationMode.MINIMIZE_TRAVEL_TIME).getLength();
        }
    }
}
