package com.louisgautier.apicontracts

/**
 * Custom annotation. Same behavior as androidx.annotation.keep
 * Need to avoid android dependencies to :server module
 * @see :client:composeApp:proguard-rules.pro for implementation of this annotation
 */
//@OptIn(ExperimentalMultiplatform::class)
//@Target(
//    AnnotationTarget.CLASS,
//    AnnotationTarget.FUNCTION,
//    AnnotationTarget.PROPERTY,
//    AnnotationTarget.FIELD,
//    AnnotationTarget.CONSTRUCTOR
//)
//@Retention(AnnotationRetention.BINARY)
//@OptionalExpectation
annotation class KeepForR8()