package com.longterm.artschools.ui.components.common

open class Action {
    open operator fun invoke(block: () -> Unit) = block()

    class Handled : Action() {
        override operator fun invoke(block: () -> Unit) = Unit
    }
}
//
//open class ValueAction<T>(protected open val value: T) {
//    open operator fun invoke(block: (T) -> Unit) = block(value)
//
//    class Handled : ValueAction<Nothing>() {
//        override operator fun invoke(block: (Nothing) -> Unit) = Unit
//    }
//}