package io.github.klahap.mccgen.util

import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass


fun fileBuilder(
    packageName: String,
    fileName: String,
    block: FileSpec.Builder.() -> Unit,
) = FileSpec.builder(packageName, fileName).apply(block).build()


fun TypeSpec.Builder.addProperty(
    name: String,
    type: TypeName,
    block: PropertySpec.Builder.() -> Unit,
) = addProperty(PropertySpec.builder(name, type).apply(block).build())


fun TypeSpec.Builder.primaryConstructor(
    block: FunSpec.Builder.() -> Unit,
) = primaryConstructor(FunSpec.constructorBuilder().apply(block).build())

context(TypeSpec.Builder)
fun FunSpec.Builder.addPrimaryConstructorProperty(
    name: String,
    type: KClass<*>,
    block: PropertySpec.Builder.() -> Unit
) {
    addParameter(name, type)
    addProperty(name, type) {
        initializer(name)
        block()
    }
}

context(TypeSpec.Builder)
fun FunSpec.Builder.addPrimaryConstructorProperty(
    name: String,
    type: TypeName,
    block: PropertySpec.Builder.() -> Unit
) {
    addParameter(name, type)
    addProperty(name, type) {
        initializer(name)
        block()
    }
}

fun TypeSpec.Builder.addProperty(
    name: String,
    type: KClass<*>,
    block: PropertySpec.Builder.() -> Unit,
) = addProperty(buildProperty(name, type, block))

fun TypeSpec.Builder.addEnumConstant(
    name: String,
    block: TypeSpec.Builder.() -> Unit,
) = addEnumConstant(name, anonymousClassBuilder(block))

fun buildProperty(
    name: String,
    type: KClass<*>,
    block: PropertySpec.Builder.() -> Unit,
) = PropertySpec.builder(name = name, type = type).apply(block).build()

fun anonymousClassBuilder(
    block: TypeSpec.Builder.() -> Unit,
) = TypeSpec.anonymousClassBuilder().apply(block).build()


fun FileSpec.Builder.addEnum(
    name: String,
    block: TypeSpec.Builder.() -> Unit,
) = addType(TypeSpec.enumBuilder(name).apply(block).build())
