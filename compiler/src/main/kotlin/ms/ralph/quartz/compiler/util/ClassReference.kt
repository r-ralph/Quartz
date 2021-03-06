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

import com.squareup.javapoet.ClassName

/**
 * Shelf class of class reference
 */
object ClassReference {
    /**
     * Class information of Intent
     */
    internal val INTENT_CLASS = ClassName.get("android.content", "Intent")

    /**
     * Class information of Context
     */
    internal val CONTEXT_CLASS = ClassName.get("android.content", "Context")

    /**
     * Class information of Bundle
     */
    internal val BUNDLE_CLASS = ClassName.get("android.os", "Bundle")

    /**
     * Class information of QuartzUtil
     */
    internal val QUARTZ_UTIL_CLASS = ClassName.get("ms.ralph.quartz", "QuartzUtil")
}
