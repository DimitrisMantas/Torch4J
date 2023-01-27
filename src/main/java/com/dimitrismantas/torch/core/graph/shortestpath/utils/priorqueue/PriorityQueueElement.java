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
package com.dimitrismantas.torch.core.graph.shortestpath.utils.priorqueue;

public final class PriorityQueueElement {
    private final int k;
    private final int v;

    public PriorityQueueElement(final int k, final int v) {
        this.k = k;
        this.v = v;
    }

    public int getKey() {
        return k;
    }

    public int getValue() {
        return v;
    }
}
