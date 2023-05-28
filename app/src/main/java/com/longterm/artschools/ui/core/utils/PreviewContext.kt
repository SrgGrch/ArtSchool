package com.longterm.artschools.ui.core.utils

import androidx.compose.runtime.Composable
import com.longterm.artschools.di.previewList
import com.longterm.artschools.ui.core.theme.ArtSchoolsTheme
import org.koin.compose.KoinApplication
import org.koin.core.module.Module

@Composable
fun PreviewContext(modules: List<Module> = previewList, content: @Composable () -> Unit) {
    ArtSchoolsTheme {
        KoinApplication(
            moduleList = { modules }, content
        )
    }
}