import com.dimitrismantas.torch.core.graph.shortestpaths.ShortestPathAlgorithm;
import com.dimitrismantas.torch.core.utils.serialization.graph.DeserializedGraph;
import com.dimitrismantas.torch.core.utils.serialization.graph.DeserializedVertex;
import com.dimitrismantas.torch.core.utils.serialization.readers.ALTDataReader;
import com.dimitrismantas.torch.core.utils.serialization.readers.SerializedGraphReader;
import org.junit.jupiter.api.Test;

public final class ALTDataReaderTest {
    @Test
    public void main() {
        final DeserializedGraph graph = new SerializedGraphReader("src/main/resources/grc/grc_graph_8500874.dat").getGraph();
        final ALTDataReader altDistReader = new ALTDataReader("src/main/resources/grc/alt/grc_alt_dist_16.dat");
        final ALTDataReader altTrvlReader = new ALTDataReader("src/main/resources/grc/alt/grc_alt_trvl_16.dat");

        final ShortestPathAlgorithm calculator = new ShortestPathAlgorithm(graph);

        for (int i = 0; i < 10; i++) {
            final DeserializedVertex s = graph.vertices((int) (Math.random() * 8500874));
            final DeserializedVertex t = graph.vertices((int) (Math.random() * 8500874));

            assert altDistReader.getEstimatedCost(s, t) <= calculator.run(s, t, ShortestPathAlgorithm.OptimizationMode.MINIMIZE_DISTANCE).getLength();
        }

        for (int i = 0; i < 10; i++) {
            final DeserializedVertex s = graph.vertices((int) (Math.random() * 8500874));
            final DeserializedVertex t = graph.vertices((int) (Math.random() * 8500874));

            assert altTrvlReader.getEstimatedCost(s, t) <= calculator.run(s, t, ShortestPathAlgorithm.OptimizationMode.MINIMIZE_DISTANCE).getLength();
        }
    }
}
