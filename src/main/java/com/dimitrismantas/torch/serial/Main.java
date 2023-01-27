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
package com.dimitrismantas.torch.serial;

import com.dimitrismantas.torch.serial.graph.Graph;

public final class Main {
    public static void main(String[] args) {
        new Graph("utils/resources/grc/grc_graph_vertices_8500875.txt", "utils/resources/grc/grc_graph_edges_8809699.txt").serialize("src/main/resources/grc/grc_graph_8500875_8809699.dat");
    }
}
