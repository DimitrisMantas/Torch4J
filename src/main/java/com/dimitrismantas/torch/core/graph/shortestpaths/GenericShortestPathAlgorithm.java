/*
 * Torch is a model, open-source Android application for optimal routing
 * in offline mobile devices.
 * Copyright (C) 2021-2022  DIMITRIS(.)MANTAS(@outlook.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.dimitrismantas.torch.core.graph.shortestpaths;

import com.dimitrismantas.torch.core.graph.Edge;
import com.dimitrismantas.torch.core.graph.Graph;
import com.dimitrismantas.torch.core.graph.Path;
import com.dimitrismantas.torch.core.graph.Vertex;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.exceptions.EqualEndpointException;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.exceptions.UnreachableTargetException;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.heuristics.astar.alt.ALTHeuristic;
import com.dimitrismantas.torch.core.graph.shortestpaths.utils.priorityqueues.PriorityQueueElement;
import com.dimitrismantas.torch.core.utils.serialization.readers.ALTDataReader;
import com.dimitrismantas.torch.core.utils.serialization.readers.GraphReader;

import java.util.Comparator;
import java.util.PriorityQueue;

public final class GenericShortestPathAlgorithm {
    private final Graph graph;
    private final ALTDataReader reader1;
    private final ALTDataReader reader2;
    // TODO
    private final ALTHeuristic heuristic;
    private Path route;
    private PriorityQueue<PriorityQueueElement> priorityQueue;
    private short numExecutions;

    public GenericShortestPathAlgorithm(final Graph graph, final ALTDataReader reader1, final ALTDataReader reader2) {
        this.graph = graph;
        // TODO
        this.reader1 = reader1;
        this.reader2 = reader2;
        this.heuristic = new ALTHeuristic();
    }

    public Path run(final Vertex source, final Vertex target, final OptimizationMode OptimizationMode) {
        invalidatePreviousExecution(target, OptimizationMode);
        // This can happen if the origin and destination are so close to each other that their nearest neighbors are equal.
        if (GraphReader.equals(source, target)) {
            throw new EqualEndpointException("The source and target vertices are equal.");
        }
        // The correct value of the estimated cost to from the source vertex to the target is equal to the corresponding value of the appropriate heuristic. However, since the priority queue is initially empty, the source is guaranteed to be dequeued first.
        initialize(source, -1, 0, heuristic.estimateCostToReferenceVertex(source, numExecutions));
        while (!priorityQueue.isEmpty()) {
            final PriorityQueueElement entry = priorityQueue.poll();
            final Vertex curr = graph.vertices(entry.getValue());
            // Since there might be two "copies" of the current vertex in the priority queue, we must be able to differentiate between them so that we use the correct one (i.e., the one with the minimum key).
            if (entry.getKey() > curr.actualCostFromSource() + heuristic.estimateCostToReferenceVertex(curr, numExecutions)) {
                continue;
            }
            if (GraphReader.equals(curr, target)) {
                populatePath(source, curr);
                break;
            }
            for (int i = 0; i < curr.outgoingEdgesLength(); i++) {
                final Edge outEdge = curr.outgoingEdges(i);
                final Vertex adj = graph.vertices(outEdge.endVertexLabel());
                int costFromSource = curr.actualCostFromSource();
                switch (OptimizationMode) {
                    case MINIMIZE_DISTANCE:
                        costFromSource += outEdge.length();
                        break;
                    case MINIMIZE_TRAVEL_TIME:
                        costFromSource += outEdge.travelTime();
                        break;
                }
                final int costToTarget = heuristic.estimateCostToReferenceVertex(adj, numExecutions);
                if (adj.numInitialized() == numExecutions) {
                    relax(curr, adj, costFromSource, costToTarget);
                } else {
                    initialize(adj, curr.lbl(), costFromSource, costToTarget);
                }
            }
        }
        if (route.getEndpoints().isEmpty()) {
            throw new UnreachableTargetException("The target vertex is unreachable from the source.");
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
            case MINIMIZE_DISTANCE:
                this.heuristic.setReader(reader1);
                break;
            case MINIMIZE_TRAVEL_TIME:
                this.heuristic.setReader(reader2);
                break;
            default:
                throw new IllegalArgumentException(String.format("Failed to recognize optimization mode: %s", optMode));
        }
    }
}
