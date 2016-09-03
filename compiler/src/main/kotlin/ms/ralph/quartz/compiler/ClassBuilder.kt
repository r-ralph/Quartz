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

import com.squareup.javapoet.*
import ms.ralph.quartz.compiler.util.ClassReference.CONTEXT_CLASS
import ms.ralph.quartz.compiler.util.ClassReference.INTENT_CLASS
import ms.ralph.quartz.compiler.util.Constant
import ms.ralph.quartz.compiler.util.Constant.CONTEXT_PARAMETER_NAME
import ms.ralph.quartz.compiler.util.Constant.CREATE_METHOD_NAME
import ms.ralph.quartz.compiler.util.Constant.INTENT_PARAMETER_NAME
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier

/**
 * Wrapper of TypeSpec.Builder
 */
class ClassBuilder(className: String) {
    /**
     * Class builder
     */
    val builder: TypeSpec.Builder = TypeSpec.classBuilder(className)

    /**
     * Set modifier to <code>public final</code> class
     */
    fun setSignature(): ClassBuilder = this.apply {
        builder.addModifiers(Modifier.PUBLIC, Modifier.FINAL)
    }

    /**
     * Create fields
     *
     * @param requiredElements Field information that is annotated with <code>@Required</code>
     * @param optionalElements Field information that is annotated with <code>@Optional</code>
     */
    fun createFields(requiredElements: Array<Element>, optionalElements: Array<Element>): ClassBuilder = this.apply {
        builder.addField(FieldSpec.builder(CONTEXT_CLASS, CONTEXT_PARAMETER_NAME, Modifier.PRIVATE).build())
        // requiredElements
        // optionalElements
    }

    /**
     * Create constructor with <code>Context</code> and element annotated with <code>@Required</code>
     *
     * @param requiredElements Field information that is annotated with <code>@Required</code>
     */
    fun createConstructor(requiredElements: Array<Element>): ClassBuilder = this.apply {
        val constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameter(ParameterSpec.builder(CONTEXT_CLASS, CONTEXT_PARAMETER_NAME).build())
                //.addParameters(requiredElements.mapToParameterSpec)
                .addStatement("this.\$L = \$L", CONTEXT_PARAMETER_NAME, CONTEXT_PARAMETER_NAME)
                //.addStatement()
                .build()
        builder.addMethod(constructor)
    }

    /**
     * Create constructor with <code>Context</code> and element annotated with <code>@Required</code>
     *
     * @param requiredElements Field information that is annotated with <code>@Required</code>
     */
    fun createCreateMethod(requiredElements: Array<Element>): ClassBuilder = this.apply {
        val createMethod = MethodSpec.methodBuilder(CREATE_METHOD_NAME)
                .addModifiers(Modifier.PRIVATE)
                .addParameter(ParameterSpec.builder(CONTEXT_CLASS, CONTEXT_PARAMETER_NAME).build())
                //.addParameters(requiredElements.mapToParameterSpec)
                .build()
        builder.addMethod(createMethod)
    }

    /**
     * Create methods for determining optional parameters
     *
     * @param optionalElements Field information that is annotated with <code>@Optional</code>
     */
    fun createOptionalMethods(optionalElements: Array<Element>): ClassBuilder = this.apply {
    }

    /**
     * Create <code>build</code> method
     */
    fun createBuildMethod(classSpec: ClassName): ClassBuilder = this.apply {
        val buildMethod = MethodSpec.methodBuilder(Constant.BUILD_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC)
                .returns(INTENT_CLASS)
                .addStatement("\$T \$L = new \$T(\$L, \$T.class)", INTENT_CLASS, INTENT_PARAMETER_NAME, INTENT_CLASS, CONTEXT_PARAMETER_NAME, classSpec)
                // Bundleでいろいろやる
                .addStatement("return \$L", INTENT_PARAMETER_NAME)
                .build()
        builder.addMethod(buildMethod)
    }

    /**
     * Build <code>MethodSpec</code>
     */
    fun build(): TypeSpec = builder.build()
}
