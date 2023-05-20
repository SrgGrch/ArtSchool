package com.longterm.artschools.di

import com.longterm.artschools.data.network.HttpClientFactory
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val presentationModule = module {

}

val dataModule = module {
    factory { HttpClientFactory(get(), /*todo pass URL*/) }
}

val domainModule = module {

}

val commonModule = module {
    single { Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    } }
}