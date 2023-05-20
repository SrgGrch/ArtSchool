package com.longterm.artschools.ui.core.utils

import androidx.compose.runtime.Composable
import com.longterm.artschools.di.moduleList
import org.koin.compose.KoinApplication
import org.koin.core.module.Module

@Composable
fun PreviewContext(modules: List<Module> = moduleList, content: @Composable () -> Unit) {
    KoinApplication(
        moduleList = { modules }, content
    )
}