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
import ms.ralph.quartz.Optional
import ms.ralph.quartz.Required
import ms.ralph.quartz.compiler.util.ClassReference.BUNDLE_CLASS
import ms.ralph.quartz.compiler.util.ClassReference.CONTEXT_CLASS
import ms.ralph.quartz.compiler.util.ClassReference.INTENT_CLASS
import ms.ralph.quartz.compiler.util.ClassReference.QUARTZ_UTIL_CLASS
import ms.ralph.quartz.compiler.util.Constant
import ms.ralph.quartz.compiler.util.Constant.ACTIVITY_PARAMETER_NAME
import ms.ralph.quartz.compiler.util.Constant.BUILD_METHOD_NAME
import ms.ralph.quartz.compiler.util.Constant.BUNDLE_PARAMETER_NAME
import ms.ralph.quartz.compiler.util.Constant.CONTEXT_PARAMETER_NAME
import ms.ralph.quartz.compiler.util.Constant.CREATE_METHOD_NAME
import ms.ralph.quartz.compiler.util.Constant.GET_EXTRAS_METHOD_NAME
import ms.ralph.quartz.compiler.util.Constant.GET_INTENT_METHOD_NAME
import ms.ralph.quartz.compiler.util.Constant.GET_METHOD_NAME
import ms.ralph.quartz.compiler.util.Constant.INTENT_PARAMETER_NAME
import ms.ralph.quartz.compiler.util.Constant.PUT_EXTRAS_METHOD_NAME
import ms.ralph.quartz.compiler.util.Constant.PUT_METHOD_NAME
import ms.ralph.quartz.compiler.util.Constant.RESTORE_METHOD_NAME
import ms.ralph.quartz.compiler.util.Constant.SETTER_PREFIX
import ms.ralph.quartz.compiler.util.mapToParameterSpec
import ms.ralph.quartz.compiler.util.upperCase
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier.*

/**
 * Wrapper of TypeSpec.Builder
 */
class ClassBuilder(className: String) {
    /**
     * Class builder
     */
    val builder: TypeSpec.Builder = TypeSpec.classBuilder(className)

    /**
     * Container of generated FieldSpec
     */
    val fields = arrayListOf<Pair<FieldSpec, Element>>()

    /**
     * Set modifier to <code>public final</code> class
     */
    fun setSignature(): ClassBuilder = this.apply {
        builder.addModifiers(PUBLIC, FINAL)
    }

    /**
     * Create fields
     *
     * @param requiredElements Field information that is annotated with <code>@Required</code>
     * @param optionalElements Field information that is annotated with <code>@Optional</code>
     */
    fun createFields(requiredElements: List<Element>, optionalElements: List<Element>): ClassBuilder = this.apply {
        builder.addField(FieldSpec.builder(CONTEXT_CLASS, CONTEXT_PARAMETER_NAME, PRIVATE).build())
        val fieldRegister: (Element) -> Unit = {
            val field = FieldSpec.builder(ClassName.get(it.asType()), it.simpleName.toString(), PRIVATE).build()
            fields.add(field.to(it))
            builder.addField(field)
        }
        requiredElements.forEach(fieldRegister)
        optionalElements.forEach(fieldRegister)
    }

    /**
     * Create constructor with <code>Context</code> and element annotated with <code>@Required</code>
     *
     * @param requiredElements Field information that is annotated with <code>@Required</code>
     */
    fun createConstructor(requiredElements: List<Element>): ClassBuilder = this.apply {
        val constructor = MethodSpec.constructorBuilder()
                .addModifiers(PRIVATE)
                .addParameter(ParameterSpec.builder(CONTEXT_CLASS, CONTEXT_PARAMETER_NAME).build())
                .addParameters(requiredElements.mapToParameterSpec())
                .addStatement("this.\$L = \$L", CONTEXT_PARAMETER_NAME, CONTEXT_PARAMETER_NAME)
                .apply { requiredElements.map { it.simpleName.toString() }.forEach { addStatement("this.\$L = \$L", it, it) } }
                .build()
        builder.addMethod(constructor)
    }

    /**
     * Create constructor with <code>Context</code> and element annotated with <code>@Required</code>
     *
     * @param selfClassSpec    Class information of generated class
     * @param requiredElements Field information that is annotated with <code>@Required</code>
     */
    fun createCreateMethod(selfClassSpec: ClassName, requiredElements: List<Element>): ClassBuilder = this.apply {
        val requiredArguments = requiredElements.map { it.simpleName.toString() }.run { if (size != 0) joinToString(", ", ", ") else "" }
        val createMethod = MethodSpec.methodBuilder(CREATE_METHOD_NAME)
                .addModifiers(PUBLIC, STATIC)
                .returns(selfClassSpec)
                .addParameter(ParameterSpec.builder(CONTEXT_CLASS, CONTEXT_PARAMETER_NAME).build())
                .addParameters(requiredElements.mapToParameterSpec())
                .addStatement("return new \$T(\$L$requiredArguments)", selfClassSpec, CONTEXT_PARAMETER_NAME)
                .build()
        builder.addMethod(createMethod)
    }

    /**
     * Create methods for determining optional parameters
     *
     * @param selfClassSpec    Class information of generated class
     * @param optionalElements Field information that is annotated with <code>@Optional</code>
     */
    fun createOptionalMethods(selfClassSpec: ClassName, optionalElements: List<Element>): ClassBuilder = this.apply {
        val optionalMethods = optionalElements.map {
            MethodSpec.methodBuilder(it.simpleName.toString())
                    .addModifiers(PUBLIC)
                    .returns(selfClassSpec)
                    .addParameter(ParameterSpec.builder(ClassName.get(it.asType()), it.simpleName.toString()).build())
                    .addStatement("this.\$L = \$L", it.simpleName.toString(), it.simpleName.toString())
                    .addStatement("return this")
                    .build()
        }
        builder.addMethods(optionalMethods)
    }

    /**
     * Create <code>build</code> method
     *
     * @param activityClassSpec Class information of activity class
     */
    fun createBuildMethod(activityClassSpec: ClassName): ClassBuilder = this.apply {
        val buildMethod = MethodSpec.methodBuilder(BUILD_METHOD_NAME)
                .addModifiers(PUBLIC)
                .returns(INTENT_CLASS)
                .addStatement("\$T \$L = new \$T(\$L, \$T.class)", INTENT_CLASS, INTENT_PARAMETER_NAME, INTENT_CLASS, CONTEXT_PARAMETER_NAME, activityClassSpec)
                .addStatement("\$T \$L = new \$T()", BUNDLE_CLASS, BUNDLE_PARAMETER_NAME, BUNDLE_CLASS)
                .apply { fields.forEach { addStatement("\$T.\$N(\$N, \$S, \$N)", QUARTZ_UTIL_CLASS, PUT_METHOD_NAME, BUNDLE_PARAMETER_NAME, it.first.name.toUpperCase(), it.first.name) } }
                .addStatement("\$L.\$N(\$L)", INTENT_PARAMETER_NAME, PUT_EXTRAS_METHOD_NAME, BUNDLE_PARAMETER_NAME)
                .addStatement("return \$L", INTENT_PARAMETER_NAME)
                .build()
        builder.addMethod(buildMethod)
    }

    /**
     * Create <code>restore</code> method for restoring data from Intent
     *
     * @param activityClassSpec Class information of activity class
     */
    fun createRestoreMethod(activityClassSpec: ClassName): ClassBuilder = this.apply {
        val restoreMethod = MethodSpec.methodBuilder(RESTORE_METHOD_NAME)
                .addModifiers(PUBLIC, STATIC)
                .addParameter(activityClassSpec, ACTIVITY_PARAMETER_NAME)
                .addStatement("\$T \$N = \$N.\$N().\$N()", BUNDLE_CLASS, BUNDLE_PARAMETER_NAME, ACTIVITY_PARAMETER_NAME, GET_INTENT_METHOD_NAME, GET_EXTRAS_METHOD_NAME)
                .beginControlFlow("if (\$N == null)", BUNDLE_PARAMETER_NAME)
                .apply {
                    addStatement("return")
                }.endControlFlow()
                .apply {
                    fields.forEach {
                        val setterName = it.second.getAnnotation(Required::class.java)?.setter?.let { if (it.isBlank()) null else it }
                                ?: it.second.getAnnotation(Optional::class.java)?.setter?.let { if (it.isBlank()) null else it }
                                ?: if (it.second.modifiers.contains(PRIVATE)) "$SETTER_PREFIX${it.second.simpleName.toString().upperCase(0)}" else null
                        val key = it.first.name.toUpperCase()
                        beginControlFlow("if (\$N.\$N(\$S))", BUNDLE_PARAMETER_NAME, Constant.CONTAINS_KEY_METHOD_NAME, key)
                        if (setterName.isNullOrEmpty()) {
                            // Direct access
                            addStatement("\$N.\$N = \$T.\$N(\$N, \$S, \$T.class)", ACTIVITY_PARAMETER_NAME, it.second.simpleName, QUARTZ_UTIL_CLASS, GET_METHOD_NAME, BUNDLE_PARAMETER_NAME, key, it.second.asType())
                        } else {
                            // Access via setter
                            addStatement("\$N.\$N(\$T.\$N(\$N, \$S, \$T.class))", ACTIVITY_PARAMETER_NAME, setterName, QUARTZ_UTIL_CLASS, GET_METHOD_NAME, BUNDLE_PARAMETER_NAME, key, it.second.asType())
                        }
                        endControlFlow()
                    }
                }
                .build()
        builder.addMethod(restoreMethod)
    }

    /**
     * Build <code>TypeSpec</code>
     */
    fun build(): TypeSpec = builder.build()
}
