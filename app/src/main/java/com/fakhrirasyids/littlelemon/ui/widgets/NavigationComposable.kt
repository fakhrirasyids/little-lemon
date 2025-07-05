package com.fakhrirasyids.littlelemon.ui.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fakhrirasyids.littlelemon.data.local.AppDatabase
import com.fakhrirasyids.littlelemon.ui.navigation.HomeDestination
import com.fakhrirasyids.littlelemon.ui.navigation.OnboardingDestination
import com.fakhrirasyids.littlelemon.ui.navigation.ProfileDestination
import com.fakhrirasyids.littlelemon.ui.screens.home.HomeScreen
import com.fakhrirasyids.littlelemon.ui.screens.onboarding.OnBoardingScreen
import com.fakhrirasyids.littlelemon.ui.screens.profile.ProfileScreen
import com.fakhrirasyids.littlelemon.utils.EMAIL
import com.fakhrirasyids.littlelemon.utils.USER_PROFILE


@Composable
fun NavigationComposable(navController: NavHostController, database: AppDatabase) {
    val hasUserData = hasUserDataInSharedPreferences()

    NavHost(
        navController = navController,
        startDestination = if (hasUserData) HomeDestination.route else OnboardingDestination.route
    ) {
        composable(OnboardingDestination.route) { OnBoardingScreen(navController = navController) }
        composable(HomeDestination.route) { HomeScreen(navController = navController, database) }
        composable(ProfileDestination.route) { ProfileScreen(navController = navController) }
    }
}

@Composable
fun hasUserDataInSharedPreferences(): Boolean {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)
    val email = sharedPreferences.getString(EMAIL, "")
    return !email.isNullOrEmpty()
}
