package com.longterm.artschools.data.api

import io.ktor.client.request.HttpRequestBuilder

fun HttpRequestBuilder.withAuth() {
    headers.append("Authorization", "")
}