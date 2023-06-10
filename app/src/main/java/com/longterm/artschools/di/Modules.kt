package com.longterm.artschools.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.longterm.artschools.data.UserStorage
import com.longterm.artschools.data.api.CoursesApi
import com.longterm.artschools.data.api.NewsApi
import com.longterm.artschools.data.api.OnboardingApi
import com.longterm.artschools.data.api.PointsApi
import com.longterm.artschools.data.api.QuizApi
import com.longterm.artschools.data.api.UserApi
import com.longterm.artschools.data.api.VkApi
import com.longterm.artschools.data.network.HttpClientFactory
import com.longterm.artschools.data.repository.BoughtCoursesRepository
import com.longterm.artschools.data.repository.CoursesRepository
import com.longterm.artschools.data.repository.NewsRepository
import com.longterm.artschools.data.repository.OnboardingRepository
import com.longterm.artschools.data.repository.PlaylistRepository
import com.longterm.artschools.data.repository.PointsRepository
import com.longterm.artschools.data.repository.QuizRepository
import com.longterm.artschools.data.repository.UserRepository
import com.longterm.artschools.domain.usecase.AnswerQuizUseCase
import com.longterm.artschools.domain.usecase.AuthUseCase
import com.longterm.artschools.domain.usecase.GetFeedUseCase
import com.longterm.artschools.domain.usecase.LessonUseCase
import com.longterm.artschools.domain.usecase.RegisterUseCase
import com.longterm.artschools.domain.usecase.VkAuthUseCase
import com.longterm.artschools.ui.components.achievements.AchievementsVm
import com.longterm.artschools.ui.components.auth.AuthViewModel
import com.longterm.artschools.ui.components.course.CourseViewModel
import com.longterm.artschools.ui.components.coursesList.CoursesListViewModel
import com.longterm.artschools.ui.components.lesson.LessonViewModel
import com.longterm.artschools.ui.components.main.MainViewModel
import com.longterm.artschools.ui.components.map.MapVm
import com.longterm.artschools.ui.components.map.dialog.MapPointInfoVm
import com.longterm.artschools.ui.components.news.ArticleVm
import com.longterm.artschools.ui.components.onboarding.OnboardingViewModel
import com.longterm.artschools.ui.components.onboarding.art.OnboardingArtViewModel
import com.longterm.artschools.ui.components.onboarding.register.RegisterViewModel
import com.longterm.artschools.ui.components.onboarding.target.OnboardingTargetViewModel
import com.longterm.artschools.ui.components.onboarding.userInfo.OnboardingUserInfoViewModel
import com.longterm.artschools.ui.components.profile.ProfileVm
import com.longterm.artschools.ui.navigation.BottomBarCoordinator
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { OnboardingViewModel() }
    viewModel { params -> OnboardingArtViewModel(params.get(), get()) }
    viewModel { params -> OnboardingTargetViewModel(params.get(), get()) }
    viewModel { params -> OnboardingUserInfoViewModel(params.get()) }
    viewModel { CoursesListViewModel(get(), get()) }
    viewModel { params -> CourseViewModel(params.get(), get(), get()) }
    viewModel { params -> LessonViewModel(params.get(), get(), get(), get()) }
    viewModel { AuthViewModel(get(), get()) }
    viewModel { params -> RegisterViewModel(params.get(), get()) }
    viewModel { params -> ArticleVm(params.get(), get()) }
    viewModel { ProfileVm(get(), get()) }
    viewModel { MapVm(get()) }
    viewModel { MapPointInfoVm(get()) }
    viewModel { AchievementsVm(get()) }

    factory {
        ExoPlayer.Builder(androidApplication())
            .build()
    }

    factory { BottomBarCoordinator() }
}

val androidModule = module {
    factory { androidApplication().resources }

    factory(qualifier = SharedPreferencesQualifier.UserStorage) {
        androidApplication().getSharedPreferences(
            "UserStorage",
            Context.MODE_PRIVATE
        )
    }

    factory(qualifier = SharedPreferencesQualifier.BoughtCourses) {
        androidApplication().getSharedPreferences(
            "BoughtCourses",
            Context.MODE_PRIVATE
        )
    }
}

val dataModule = module {
    factory { HttpClientFactory(get(), get()).create("http://dolgostroiki-20.game-kit.ru/api/") }

    factory { UserStorage(get(SharedPreferencesQualifier.UserStorage)) }

    factory { UserApi(get()) }
    factory { VkApi(get()) }
    factory { OnboardingApi(get()) }
    factory { QuizApi(get()) }
    factory { NewsApi(get()) }
    factory { CoursesApi(get()) }
    factory { PointsApi(get()) }

    factory { UserRepository(get(), get()) }
    factory { OnboardingRepository(get()) }
    factory { QuizRepository(get()) }
    factory { NewsRepository(get()) }
    factory { PlaylistRepository() }
    factory { CoursesRepository(get()) }
    single { PointsRepository(get()) }

    factory { BoughtCoursesRepository(get(SharedPreferencesQualifier.BoughtCourses)) }
}

val domainModule = module {
    scope<OnboardingScope> {
        scoped { RegisterUseCase(get(), get()) }
    }

    factory { VkAuthUseCase(get(), get()) }
    factory { GetFeedUseCase(get(), get(), get()) }
    factory { AuthUseCase(get(), get()) }
    factory { AnswerQuizUseCase(get()) }
    factory { LessonUseCase(get(), get()) }
}

val commonModule = module {
    single {
        Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }
}

val moduleList = listOf(
    commonModule,
    domainModule,
    dataModule,
    presentationModule,
    androidModule
)