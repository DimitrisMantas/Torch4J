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
package com.dimitrismantas.torch.core.graph.shortestpath;

import com.dimitrismantas.torch.core.graph.Edge;
import com.dimitrismantas.torch.core.graph.Graph;
import com.dimitrismantas.torch.core.graph.Path;
import com.dimitrismantas.torch.core.graph.Vertex;
import com.dimitrismantas.torch.core.graph.shortestpath.utils.exceptions.EqualPathEndpointsException;
import com.dimitrismantas.torch.core.graph.shortestpath.utils.exceptions.UnreachablePathTargetException;
import com.dimitrismantas.torch.core.graph.shortestpath.utils.heuristics.alt.ALTHeuristic;
import com.dimitrismantas.torch.core.graph.shortestpath.utils.priorqueue.PriorityQueueElement;
import com.dimitrismantas.torch.core.utils.serial.ALTDataReader;
import com.dimitrismantas.torch.core.utils.serial.GraphReader;

import java.util.Comparator;
import java.util.PriorityQueue;

public final class GenericLabelSettingShortestPathAlgorithm {
    private final Graph graph;
    // TODO
    private final ALTHeuristic heuristic;
    private final ALTDataReader reader;
    public short numExecutions;
    private LabelSettingShortestPathAlgorithm algorithm;
    private Path route;
    private PriorityQueue<PriorityQueueElement> priorityQueue;

    public GenericLabelSettingShortestPathAlgorithm(final Graph graph, final ALTDataReader reader) {
        this.graph = graph;
        // TODO
        this.reader = reader;
        this.heuristic = new ALTHeuristic(this);
    }
//    public GenericLabelSettingShortestPathAlgorithm(final Graph graph, final LabelSettingShortestPathAlgorithm algorithm, OptimizationMode optMode) {
//        this.graph = graph;
//        this.algorithm = algorithm;
//
//
//        switch (algorithm) {
//            case DIJKSTRA:
//                heuristic = new DijkstraHeuristic(this);
//            case A_STAR:
//                switch (optMode) {
//                    case MINIMUM_DISTANCE:
//                        heuristic = new AStarDistanceHeuristic(this);
//                        break;
//                    case MINIMUM_TRAVEL_TIME:
//                        heuristic = new AStarTravelTimeHeuristic(this);
//                        break;
//                    default:
//                        throw new IllegalArgumentException(String.format("Failed to recognize optimization mode: %s", optMode));
//                }
//            case ALT:
//                switch (optMode) {
//                    case MINIMUM_DISTANCE:
//                        heuristic = new ALTHeuristic(this);
//                        break;
//                    case MINIMUM_TRAVEL_TIME:
//                        heuristic = new ALTHeuristic(this);
//                        break;
//                    default:
//                        throw new IllegalArgumentException(String.format("Failed to recognize optimization mode: %s", optMode));
//                }
//            default:
//                throw new IllegalArgumentException(String.format("Failed to recognize algorithm: %s", algorithm));
//        }
//    }

    public Path run(final Vertex source, final Vertex target, final OptimizationMode OptimizationMode) {
        invalidatePreviousExecution(target, OptimizationMode);
        // This can happen if the origin and destination are so close to each other that their nearest neighbors are equal.
        if (GraphReader.equals(source, target)) {
            throw new EqualPathEndpointsException("The source and target vertices are equal.");
        }
        // The correct value of the estimated cost to from the source vertex to the target is equal to the corresponding value of the appropriate heuristic. However, since the priority queue is initially empty, the source is guaranteed to be dequeued first.
        initialize(source, -1, 0, heuristic.estimateCostToReferenceVertex(source));
        long t0 = System.nanoTime();
        while (!priorityQueue.isEmpty()) {
            final PriorityQueueElement entry = priorityQueue.poll();
            final Vertex curr = graph.vertices(entry.getValue());
            // Since there might be two "copies" of the current vertex in the priority queue, we must be able to differentiate between them so that we use the correct one (i.e., the one with the minimum key).
            if (entry.getKey() > curr.actualCostFromSource() + heuristic.estimateCostToReferenceVertex(curr)) {
                continue;
            }
            if (GraphReader.equals(curr, target)) {
                System.out.println((System.nanoTime() - t0) * 1e-6);
                populatePath(source, curr);
                break;
            }
            for (int i = 0; i < curr.outgoingEdgesLength(); i++) {
                final Edge outEdge = curr.outgoingEdges(i);
                final Vertex adj = graph.vertices(outEdge.endVertexLabel());
                int costFromSource = curr.actualCostFromSource();
                switch (OptimizationMode) {
                    case MINIMUM_DISTANCE:
                        costFromSource += outEdge.length();
                        break;
                    case MINIMUM_TRAVEL_TIME:
                        costFromSource += outEdge.travelTime();
                        break;
                }
                final int costToTarget = heuristic.estimateCostToReferenceVertex(adj);
                if (adj.numInitialized() == numExecutions) {
                    relax(curr, adj, costFromSource, costToTarget);
                } else {
                    initialize(adj, curr.lbl(), costFromSource, costToTarget);
                }
            }
        }
        if (route.getEndpoints().isEmpty()) {
            throw new UnreachablePathTargetException("The target vertex is unreachable from the source.");
        }
        return route;
    }

    private void invalidatePreviousExecution(final Vertex target, final OptimizationMode optMode) {
        updateHeuristic(target, optMode);
        route = new Path();
        priorityQueue = new PriorityQueue<>(Comparator.comparingInt(PriorityQueueElement::getKey));
        numExecutions++;
    }

    private void initialize(final Vertex vertex, final int predecessorLabel, final int costFromSource, final int costToTarget) {
        vertex.mutateActualCostFromSource(costFromSource);
        vertex.mutateNumInitialized(numExecutions);
        vertex.mutatePredecessorLabel(predecessorLabel);
        priorityQueue.add(new PriorityQueueElement(costFromSource + costToTarget, vertex.lbl()));
    }

    private void relax(final Vertex vertex, final Vertex adjacent, final int costFromSource, final int costToTarget) {
        if (costFromSource < adjacent.actualCostFromSource()) {
            adjacent.mutateActualCostFromSource(costFromSource);
            adjacent.mutatePredecessorLabel(vertex.lbl());
            // Add a "duplicate" vertex to the priority queue, whose key is smaller than that of its copy. This means that between these two vertices, this one will be dequeued first.
            priorityQueue.add(new PriorityQueueElement(costFromSource + costToTarget, adjacent.lbl()));
        }
    }

    private void populatePath(final Vertex source, Vertex current) {
        while (current.predecessorLabel() != -1) {
            route.addEndpoint(current);
            current = graph.vertices(current.predecessorLabel());
        }
        // The source vertex is originally not included as an endpoint, since it has no predecessor.
        route.addEndpoint(source);
        // Using each endpoint, increment the route length and travel time accordingly.
        for (int i = 0; i < route.getEndpoints().size() - 1; i++) {
            final Vertex endpoint = graph.vertices(route.getEndpoints().get(i).lbl());
            for (int j = 0; j < endpoint.outgoingEdgesLength(); j++) {
                final Edge outEdge = endpoint.outgoingEdges(j);
                if (outEdge.endVertexLabel() == route.getEndpoints().get(i + 1).lbl()) {
                    route.incrementLength(outEdge.length());
                    route.incrementTravelTime(outEdge.travelTime());
                    break;
                }
            }
        }
    }

    // TODO
    private void updateHeuristic(final Vertex target, final OptimizationMode optMode) {
        this.heuristic.setReferenceVertex(target);
        switch (optMode) {
            case MINIMUM_DISTANCE:
                this.heuristic.setReader(reader);
                break;
            case MINIMUM_TRAVEL_TIME:
                this.heuristic.setReader(reader);
                break;
            default:
                throw new IllegalArgumentException(String.format("Failed to recognize optimization mode: %s", optMode));
        }
    }
}
