package com.longterm.artschools.di

import com.longterm.artschools.data.network.HttpClientFactory
import com.longterm.artschools.ui.components.auth.AuthViewModel
import com.longterm.artschools.ui.components.main.MainViewModel
import com.longterm.artschools.ui.components.onboarding.OnboardingViewModel
import com.longterm.artschools.ui.components.onboarding.art.OnboardingArtViewModel
import com.longterm.artschools.ui.components.onboarding.target.OnboardingTargetViewModel
import com.longterm.artschools.ui.components.onboarding.userInfo.OnboardingUserInfoViewModel
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel() }
    viewModel { OnboardingViewModel() }
    viewModel { OnboardingArtViewModel() }
    viewModel { OnboardingTargetViewModel() }
    viewModel { OnboardingUserInfoViewModel() }
    viewModel { AuthViewModel() }
}

val dataModule = module {
    factory { HttpClientFactory(get() /*todo pass URL*/) }
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