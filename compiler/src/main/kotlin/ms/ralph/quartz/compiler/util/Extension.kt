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
import com.squareup.javapoet.ParameterSpec
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.util.Elements
import javax.tools.Diagnostic

// Element

fun Element.getPackageName(elements: Elements): String =
        elements.getPackageOf(this).qualifiedName.toString()

fun List<Element>.mapToParameterSpec(): List<ParameterSpec> =
        map { ParameterSpec.builder(ClassName.get(it.asType()), it.simpleName.toString()).build() }

// Messager

fun Messager.note(text: String) = printMessage(Diagnostic.Kind.NOTE, text)

fun Messager.warn(text: String) = printMessage(Diagnostic.Kind.WARNING, text)

fun Messager.error(text: String) = printMessage(Diagnostic.Kind.ERROR, text)

fun Messager.other(text: String) = printMessage(Diagnostic.Kind.OTHER, text)