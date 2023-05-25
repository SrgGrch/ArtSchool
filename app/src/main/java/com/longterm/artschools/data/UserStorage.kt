package com.longterm.artschools.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.longterm.artschools.domain.models.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserStorage(private val sharedPreferences: SharedPreferences) {
    var user: User?
        get() = sharedPreferences.getString(::user.name, null)?.let { Json.decodeFromString(it) }
        set(value) = sharedPreferences.edit {
            putString(::user.name, Json.encodeToString(value))
        }

    val requireUser
        get() = user ?: error("No user in DB")

    val isLoggedIn: Boolean
        get() = sharedPreferences.contains(::user.name)

    var token: String?
        get() = sharedPreferences.getString(::token.name, null)
        set(value) = sharedPreferences.edit {
            putString(::token.name, value)
        }

    fun setUser(user: User, token: String) {
        this.user = user
        this.token = token
    }
}