package com.longterm.artschools.data.service

import io.ktor.client.request.HttpRequestBuilder

fun HttpRequestBuilder.withAuth() {
    headers.append("Authorization", "")
}