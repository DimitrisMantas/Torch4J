// automatically generated by the FlatBuffers compiler, do not modify

package com.dimitrismantas.torch.core.graph;

import com.google.flatbuffers.BaseVector;
import com.google.flatbuffers.BooleanVector;
import com.google.flatbuffers.ByteVector;
import com.google.flatbuffers.Constants;
import com.google.flatbuffers.DoubleVector;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.FloatVector;
import com.google.flatbuffers.IntVector;
import com.google.flatbuffers.LongVector;
import com.google.flatbuffers.ShortVector;
import com.google.flatbuffers.StringVector;
import com.google.flatbuffers.Struct;
import com.google.flatbuffers.Table;
import com.google.flatbuffers.UnionVector;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class Vertex extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_23_1_21(); }
  public static Vertex getRootAsVertex(ByteBuffer _bb) { return getRootAsVertex(_bb, new Vertex()); }
  public static Vertex getRootAsVertex(ByteBuffer _bb, Vertex obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Vertex __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public int lbl() { int o = __offset(4); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public boolean mutateLbl(int lbl) { int o = __offset(4); if (o != 0) { bb.putInt(o + bb_pos, lbl); return true; } else { return false; } }
  public float lat() { int o = __offset(6); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public boolean mutateLat(float lat) { int o = __offset(6); if (o != 0) { bb.putFloat(o + bb_pos, lat); return true; } else { return false; } }
  public float lon() { int o = __offset(8); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public boolean mutateLon(float lon) { int o = __offset(8); if (o != 0) { bb.putFloat(o + bb_pos, lon); return true; } else { return false; } }
  public short numInitialized() { int o = __offset(10); return o != 0 ? bb.getShort(o + bb_pos) : 0; }
  public boolean mutateNumInitialized(short num_initialized) { int o = __offset(10); if (o != 0) { bb.putShort(o + bb_pos, num_initialized); return true; } else { return false; } }
  public int actualCostFromSource() { int o = __offset(12); return o != 0 ? bb.getInt(o + bb_pos) : -1; }
  public boolean mutateActualCostFromSource(int actual_cost_from_source) { int o = __offset(12); if (o != 0) { bb.putInt(o + bb_pos, actual_cost_from_source); return true; } else { return false; } }
  public int estimatedCostToTarget() { int o = __offset(14); return o != 0 ? bb.getInt(o + bb_pos) : -1; }
  public boolean mutateEstimatedCostToTarget(int estimated_cost_to_target) { int o = __offset(14); if (o != 0) { bb.putInt(o + bb_pos, estimated_cost_to_target); return true; } else { return false; } }
  public int predecessorLabel() { int o = __offset(16); return o != 0 ? bb.getInt(o + bb_pos) : -1; }
  public boolean mutatePredecessorLabel(int predecessor_label) { int o = __offset(16); if (o != 0) { bb.putInt(o + bb_pos, predecessor_label); return true; } else { return false; } }
  public com.dimitrismantas.torch.core.graph.Edge outgoingEdges(int j) { return outgoingEdges(new com.dimitrismantas.torch.core.graph.Edge(), j); }
  public com.dimitrismantas.torch.core.graph.Edge outgoingEdges(com.dimitrismantas.torch.core.graph.Edge obj, int j) { int o = __offset(18); return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null; }
  public int outgoingEdgesLength() { int o = __offset(18); return o != 0 ? __vector_len(o) : 0; }
  public com.dimitrismantas.torch.core.graph.Edge.Vector outgoingEdgesVector() { return outgoingEdgesVector(new com.dimitrismantas.torch.core.graph.Edge.Vector()); }
  public com.dimitrismantas.torch.core.graph.Edge.Vector outgoingEdgesVector(com.dimitrismantas.torch.core.graph.Edge.Vector obj) { int o = __offset(18); return o != 0 ? obj.__assign(__vector(o), 4, bb) : null; }

  public static int createVertex(FlatBufferBuilder builder,
      int lbl,
      float lat,
      float lon,
      short numInitialized,
      int actualCostFromSource,
      int estimatedCostToTarget,
      int predecessorLabel,
      int outgoingEdgesOffset) {
    builder.startTable(8);
    Vertex.addOutgoingEdges(builder, outgoingEdgesOffset);
    Vertex.addPredecessorLabel(builder, predecessorLabel);
    Vertex.addEstimatedCostToTarget(builder, estimatedCostToTarget);
    Vertex.addActualCostFromSource(builder, actualCostFromSource);
    Vertex.addLon(builder, lon);
    Vertex.addLat(builder, lat);
    Vertex.addLbl(builder, lbl);
    Vertex.addNumInitialized(builder, numInitialized);
    return Vertex.endVertex(builder);
  }

  public static void startVertex(FlatBufferBuilder builder) { builder.startTable(8); }
  public static void addLbl(FlatBufferBuilder builder, int lbl) { builder.addInt(0, lbl, 0); }
  public static void addLat(FlatBufferBuilder builder, float lat) { builder.addFloat(1, lat, 0.0f); }
  public static void addLon(FlatBufferBuilder builder, float lon) { builder.addFloat(2, lon, 0.0f); }
  public static void addNumInitialized(FlatBufferBuilder builder, short numInitialized) { builder.addShort(3, numInitialized, 0); }
  public static void addActualCostFromSource(FlatBufferBuilder builder, int actualCostFromSource) { builder.addInt(4, actualCostFromSource, -1); }
  public static void addEstimatedCostToTarget(FlatBufferBuilder builder, int estimatedCostToTarget) { builder.addInt(5, estimatedCostToTarget, -1); }
  public static void addPredecessorLabel(FlatBufferBuilder builder, int predecessorLabel) { builder.addInt(6, predecessorLabel, -1); }
  public static void addOutgoingEdges(FlatBufferBuilder builder, int outgoingEdgesOffset) { builder.addOffset(7, outgoingEdgesOffset, 0); }
  public static int createOutgoingEdgesVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startOutgoingEdgesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endVertex(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }
  public static void finishVertexBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
  public static void finishSizePrefixedVertexBuffer(FlatBufferBuilder builder, int offset) { builder.finishSizePrefixed(offset); }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Vertex get(int j) { return get(new Vertex(), j); }
    public Vertex get(Vertex obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

