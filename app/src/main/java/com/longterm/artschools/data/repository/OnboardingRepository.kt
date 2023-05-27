package com.longterm.artschools.data.repository

import com.longterm.artschools.data.api.OnboardingApi
import com.longterm.artschools.domain.models.Preference
import com.longterm.artschools.domain.models.Target

class OnboardingRepository(
    private val onboardingApi: OnboardingApi
) {
    suspend fun getTargets(): Result<List<Target>> = onboardingApi.targets()
    suspend fun getPreferences(): Result<List<Preference>> = onboardingApi.preferences()
}