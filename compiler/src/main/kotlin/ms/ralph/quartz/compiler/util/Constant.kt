/*
 * Copyright 2016 Ralph
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

package ms.ralph.quartz.compiler.util

/**
 * Shelf class of constant values
 */
object Constant {
    /**
     * Suffix of generated class
     */
    internal val CLASS_NAME_SUFFIX = "IntentBuilder"

    /**
     * Context parameter name
     */
    internal val CONTEXT_PARAMETER_NAME = "context"

    /**
     * Intent variable name
     */
    internal val INTENT_PARAMETER_NAME = "intent"

    /**
     * Builder instantiate method name
     */
    internal val CREATE_METHOD_NAME = "create"

    /**
     * Builder build method name
     */
    internal val BUILD_METHOD_NAME = "build"
}
