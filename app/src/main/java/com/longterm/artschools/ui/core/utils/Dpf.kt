package com.longterm.artschools.ui.core.utils

import android.content.res.Resources

val Float.dpf: Float get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)