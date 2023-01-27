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
package com.dimitrismantas.torch.serial.graph;

import com.google.flatbuffers.FlatBufferBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class Graph {
    private static final String COLUMN_DELIMITER = " ";
    private final List<Vertex> vertices = new ArrayList<>(8500874);

    public Graph(final String vdfName, final String edfName) {
        importVertices(vdfName);
        importEdges(edfName);
    }

    private void importVertices(final String vdfName) {
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(vdfName))) {
            String elementDefinition;
            while ((elementDefinition = bufferedReader.readLine()) != null) {
                final String[] elementAttributes = elementDefinition.split(COLUMN_DELIMITER);
                vertices.add(new Vertex(Integer.parseInt(elementAttributes[0]), Float.parseFloat(elementAttributes[1]), Float.parseFloat(elementAttributes[2])));
            }
        } catch (final IOException e) {
            // TODO - This exception should be logged as a warning after migrating the backend to Android.
            e.printStackTrace();
        }
    }

    private void importEdges(final String edfName) {
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(edfName))) {
            String elementDefinition;
            while ((elementDefinition = bufferedReader.readLine()) != null) {
                final String[] elementAttributes = elementDefinition.split(COLUMN_DELIMITER);
                final Vertex startVertex = vertices.get(Integer.parseInt(elementAttributes[0]));
                final Vertex endVertex = vertices.get(Integer.parseInt(elementAttributes[1]));
                final short length = Short.parseShort(elementAttributes[2]);
                final short travelTime = Short.parseShort(elementAttributes[3]);
                vertices.get(startVertex.getLabel()).addOutboundEdge(new Edge(endVertex.getLabel(), length, travelTime));
                if (Integer.parseInt(elementAttributes[4]) == 1 && Integer.parseInt(elementAttributes[5]) == 1) {
                    vertices.get(endVertex.getLabel()).addOutboundEdge(new Edge(startVertex.getLabel(), length, travelTime));
                }
            }
        } catch (final IOException e) {
            // TODO - This exception should be logged as a warning after migrating the backend to Android.
            e.printStackTrace();
        }
    }

    // TODO - Write this method according to https://github.com/google/flatbuffers/blob/master/tests/JavaTest.java.
    public void serialize(final String outputFilePath) {
        final FlatBufferBuilder builder = new FlatBufferBuilder().forceDefaults(true);
        final int[] vertexData = new int[this.vertices.size()];
        for (int i = 0; i < this.vertices.size(); i++) {
            final Vertex vertex = this.vertices.get(i);
            final List<Edge> outgoingEdges = vertex.getOutEdges();
            final int numOutgoingEdges = vertex.getOutEdges().size();
            final int[] edgeData = new int[numOutgoingEdges];
            for (int j = 0; j < outgoingEdges.size(); j++) {
                final Edge edge = outgoingEdges.get(j);
                final int edgeOffset = com.dimitrismantas.torch.core.graph.Edge.createEdge(builder, edge.getTailLabel(), edge.getLength(), edge.getTravelTime());
                edgeData[j] = edgeOffset;
            }
            final int edgesOffset = com.dimitrismantas.torch.core.graph.Vertex.createOutgoingEdgesVector(builder, edgeData);
            final int vertexOffset = com.dimitrismantas.torch.core.graph.Vertex.createVertex(builder, vertex.getLabel(), vertex.getLatitude(), vertex.getLongitude(), vertex.getNumInitialized(), vertex.getCostFromS(), vertex.getCostToT(), vertex.getPredLbl(), edgesOffset);
            vertexData[i] = vertexOffset;
        }
        final int verticesOffset = com.dimitrismantas.torch.core.graph.Graph.createVerticesVector(builder, vertexData);
        final int graphOffset = com.dimitrismantas.torch.core.graph.Graph.createGraph(builder, verticesOffset);
        builder.finish(graphOffset);
        final byte[] serializedData = builder.sizedByteArray();
        try (final BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFilePath))) {
            outputStream.write(serializedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
