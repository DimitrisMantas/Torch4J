package com.dimitrismantas.torch.serialization;

public final class GraphSerializer {
    public static void main(String[] args) {
        new SerializableGraph("utils/resources/grc/grc_graph_vertices_8500875.txt", "utils/resources/grc/grc_graph_edges_8809699.txt").serialize("src/main/resources/grc/grc_graph_8500875_8809699.dat");
    }
}
