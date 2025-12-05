package com.louisgautier.utils

/**
 * Custom annotation. Same behavior as androidx.annotation.keep
 * @see :client:composeApp:proguard-rules.pro for implementation of this annotation
 */
@OptIn(ExperimentalMultiplatform::class)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.CONSTRUCTOR
)
@Retention(AnnotationRetention.BINARY)
@OptionalExpectation
expect annotation class CoreKeepForR8()