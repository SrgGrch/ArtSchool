package com.longterm.artschools.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

interface SharedPreferencesQualifier : Qualifier {
    object UserStorage : SharedPreferencesQualifier {
        override val value: QualifierValue
            get() = this::class.simpleName!!
    }
}