package com.longterm.artschools.ui.components.vkauth.components

fun interface OnVkAuthResult {
    fun onResult(token: String, userId: String)
}