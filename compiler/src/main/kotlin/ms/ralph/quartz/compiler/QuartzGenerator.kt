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

package ms.ralph.quartz.compiler

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import ms.ralph.quartz.compiler.util.note
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier

object QuartzGenerator {
    fun createJavaFile(packageName: String, element: Element, messager: Messager): JavaFile {
        val className = "${element.simpleName}IntentBuilder"
        messager.note("Start: $className")
        val logger = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .build()
        return JavaFile.builder(packageName, logger).build()
    }
}