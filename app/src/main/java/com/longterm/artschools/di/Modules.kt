package com.longterm.artschools.di

import android.content.Context
import com.longterm.artschools.data.UserStorage
import com.longterm.artschools.data.network.HttpClientFactory
import com.longterm.artschools.ui.components.auth.AuthViewModel
import com.longterm.artschools.ui.components.main.MainViewModel
import com.longterm.artschools.ui.components.onboarding.OnboardingViewModel
import com.longterm.artschools.ui.components.onboarding.art.OnboardingArtViewModel
import com.longterm.artschools.ui.components.onboarding.register.RegisterViewModel
import com.longterm.artschools.ui.components.onboarding.target.OnboardingTargetViewModel
import com.longterm.artschools.ui.components.onboarding.userInfo.OnboardingUserInfoViewModel
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel() }
    viewModel { OnboardingViewModel() }
    viewModel { OnboardingArtViewModel() }
    viewModel { OnboardingTargetViewModel() }
    viewModel { OnboardingUserInfoViewModel() }
    viewModel { AuthViewModel() }
    viewModel { RegisterViewModel() }
}

val dataModule = module {
    factory { HttpClientFactory(get(), "http://dolgostroiki-20.game-kit.ru") }
    factory(qualifier = SharedPreferencesQualifier.UserStorage) {
        androidApplication().getSharedPreferences(
            "UserStorage",
            Context.MODE_PRIVATE
        )
    }

    factory { UserStorage(get(SharedPreferencesQualifier.UserStorage)) }
}

val domainModule = module {

}

val commonModule = module {
    single {
        Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
    }
}

val moduleList = listOf(
    commonModule,
    domainModule,
    dataModule,
    presentationModule,
)