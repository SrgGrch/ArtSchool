package com.longterm.artschools.ui.core

import java.util.Optional

fun <T : Any> T?.optional(): Optional<T> {
    return if (this == null) {
        Optional.empty()
    } else {
        Optional.of(this)
    }
}
