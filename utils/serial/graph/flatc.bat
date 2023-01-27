:: The schemas must be compiled in this exact order.
flatc -j  --gen-jvmstatic  Edge.fbs
flatc -j  --gen-jvmstatic  --gen-mutable Vertex.fbs
flatc -j  --gen-jvmstatic  Graph.fbs
PAUSE
EXIT
