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
package com.dimitrismantas.torch.core.graph.shortestpath.utils.exceptions;

/**
 * An exception thrown in case the target vertex is unreachable from the source.
 *
 * @author Dimitris Mantas
 * @version 1.0.0
 * @see EqualPathEndpointsException
 * @since 1.0.0
 */
public final class UnreachablePathTargetException extends RuntimeException {
    public UnreachablePathTargetException(final String message) {
        super(message);
    }
}
