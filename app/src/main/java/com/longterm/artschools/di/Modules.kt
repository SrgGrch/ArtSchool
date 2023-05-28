package com.longterm.artschools.di

import android.content.Context
import com.longterm.artschools.data.UserStorage
import com.longterm.artschools.data.api.NewsApi
import com.longterm.artschools.data.api.CoursesApi
import com.longterm.artschools.data.api.OnboardingApi
import com.longterm.artschools.data.api.QuizApi
import com.longterm.artschools.data.api.UserApi
import com.longterm.artschools.data.api.VkApi
import com.longterm.artschools.data.network.HttpClientFactory
import com.longterm.artschools.data.repository.NewsRepository
import com.longterm.artschools.data.repository.CoursesRepository
import com.longterm.artschools.data.repository.OnboardingRepository
import com.longterm.artschools.data.repository.PlaylistRepository
import com.longterm.artschools.data.repository.QuizRepository
import com.longterm.artschools.data.repository.UserRepository
import com.longterm.artschools.domain.usecase.AnswerQuizUseCase
import com.longterm.artschools.domain.usecase.AuthUseCase
import com.longterm.artschools.domain.usecase.GetFeedUseCase
import com.longterm.artschools.domain.usecase.RegisterUseCase
import com.longterm.artschools.domain.usecase.VkAuthUseCase
import com.longterm.artschools.ui.components.auth.AuthViewModel
import com.longterm.artschools.ui.components.course.CourseViewModel
import com.longterm.artschools.ui.components.coursesList.CoursesListViewModel
import com.longterm.artschools.ui.components.main.MainViewModel
import com.longterm.artschools.ui.components.news.ArticleVm
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
    viewModel { MainViewModel(get(), get()) }
    viewModel { OnboardingViewModel() }
    viewModel { params -> OnboardingArtViewModel(params.get(), get()) }
    viewModel { params -> OnboardingTargetViewModel(params.get(), get()) }
    viewModel { params -> OnboardingUserInfoViewModel(params.get()) }
    viewModel { AuthViewModel(get(), androidApplication().resources) }
    viewModel { params -> RegisterViewModel(params.get(), androidApplication().resources) }
    viewModel { CoursesListViewModel(get()) }
    viewModel { CourseViewModel(get()) }
    viewModel { params -> ArticleVm(params.get(), get()) }

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
    factory { OnboardingApi(get()) }
    factory { QuizApi(get()) }
    factory { NewsApi(get()) }
    factory { CoursesApi(get()) }

    factory { UserRepository(get(), get()) }
    factory { OnboardingRepository(get()) }
    factory { QuizRepository(get()) }
    factory { NewsRepository(get()) }
    factory { PlaylistRepository() }
    factory { CoursesRepository(get()) }
}

val domainModule = module {
    scope<OnboardingScope> {
        scoped { RegisterUseCase(get(), get()) }
    }

    factory { VkAuthUseCase(get(), get()) }
    factory { GetFeedUseCase(get(), get(), get()) }
    factory { AuthUseCase(get(), get()) }
    factory { AnswerQuizUseCase(get()) }
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