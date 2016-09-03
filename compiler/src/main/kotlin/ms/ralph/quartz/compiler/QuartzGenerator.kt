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

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import ms.ralph.quartz.Optional
import ms.ralph.quartz.Required
import ms.ralph.quartz.compiler.util.Constant.CLASS_NAME_SUFFIX
import ms.ralph.quartz.compiler.util.note
import javax.annotation.processing.Messager
import javax.lang.model.element.Element

/**
 * Code generator class
 */
object QuartzGenerator {
    /**
     * Create IntentBuilder Java file for annotated class
     *
     * @param packageName Package name of target class
     * @param element     Annotated class information
     * @param messager    Logger object
     */
    fun createJavaFile(packageName: String, element: Element, messager: Messager): JavaFile {
        val className = "${element.simpleName}$CLASS_NAME_SUFFIX"
        val classSpec = ClassName.get(packageName, className)
        messager.note("Start: $className")

        val requiredElements = searchRequiredElements(element)
        val optionalElements = searchOptionalElements(element)

        val classInfo = ClassBuilder(className)
                .setSignature()
                .createFields(requiredElements, optionalElements)
                .createConstructor(requiredElements)
                .createCreateMethod(classSpec, requiredElements)
                .createOptionalMethods(classSpec, optionalElements)
                .createBuildMethod(ClassName.get(packageName, element.simpleName.toString()))
                .build()
        return JavaFile.builder(packageName, classInfo).build()
    }

    /**
     * Search annotated field with @Required
     *
     * @param element Annotated class information
     */
    private fun searchRequiredElements(element: Element): List<Element> {
        return searchElement(element, Required::class.java)
    }

    /**
     * Search annotated field with @Optional
     *
     * @param element Annotated class information
     */
    private fun searchOptionalElements(element: Element): List<Element> {
        return searchElement(element, Optional::class.java)
    }

    /**
     * Search annotated field
     *
     * @param element Annotated class information
     * @param klass   Annotation class
     */
    private fun searchElement(element: Element, klass: Class<out Annotation>): List<Element> {
        val list = arrayListOf<Element>()
        element.enclosedElements.forEach {
            it.getAnnotation(klass)?.apply { list.add(it) }
        }
        return list.toList()

    }
}