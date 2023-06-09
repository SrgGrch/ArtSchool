package com.longterm.artschools.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit

class BoughtCoursesRepository(private val sp: SharedPreferences) {
    fun buy(id: Int) {
        sp.edit {
            putBoolean(id.toString(), true)
        }
    }

    fun isBought(id: Int): Boolean {
        return sp.getBoolean(id.toString(), false)
    }
}