package com.longterm.artschools.di

import android.content.Context
import com.longterm.artschools.data.UserStorage
import com.longterm.artschools.data.api.UserApi
import com.longterm.artschools.data.api.VkApi
import com.longterm.artschools.data.network.HttpClientFactory
import com.longterm.artschools.data.repository.UserRepository
import com.longterm.artschools.domain.usecase.RegisterUseCase
import com.longterm.artschools.domain.usecase.VkAuthUseCase
import com.longterm.artschools.ui.components.auth.AuthViewModel
import com.longterm.artschools.ui.components.main.MainViewModel
import com.longterm.artschools.ui.components.onboarding.OnboardingViewModel
import com.longterm.artschools.ui.components.onboarding.art.OnboardingArtViewModel
import com.longterm.artschools.ui.components.onboarding.register.RegisterViewModel
import com.longterm.artschools.ui.components.onboarding.target.OnboardingTargetViewModel
import com.longterm.artschools.ui.components.onboarding.userInfo.OnboardingUserInfoViewModel
import com.longterm.artschools.ui.navigation.BottomBarCoordinator
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel() }
    viewModel { OnboardingViewModel() }
    viewModel { OnboardingArtViewModel() }
    viewModel { OnboardingTargetViewModel() }
    viewModel { params -> OnboardingUserInfoViewModel(params.get()) }
    viewModel { AuthViewModel() }
    viewModel { params -> RegisterViewModel(params.get(), androidApplication().resources) }

    factory { BottomBarCoordinator() }
}

val dataModule = module {
    factory { HttpClientFactory(get(), get()).create("http://dolgostroiki-20.game-kit.ru/api/") }

    factory(qualifier = SharedPreferencesQualifier.UserStorage) {
        androidApplication().getSharedPreferences(
            "UserStorage",
            Context.MODE_PRIVATE
        )
    }

    factory { UserStorage(get(SharedPreferencesQualifier.UserStorage)) }

    factory { UserApi(get()) }
    factory { VkApi(get()) }

    factory { UserRepository(get(), get()) }
}

val domainModule = module {
    scope<OnboardingScope> {
        scoped { RegisterUseCase(get(), get()) }
    }

    factory { VkAuthUseCase(get(), get()) }
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