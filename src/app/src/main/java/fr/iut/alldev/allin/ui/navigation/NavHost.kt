package fr.iut.alldev.allin.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.iut.alldev.allin.ui.home.Home
import fr.iut.alldev.allin.ui.profile.Profile

object Routes {
    const val HOME = "home"
    const val PROFILE = "profile"
}

@Composable
fun AllInNavHost(modifier: Modifier = Modifier,
                 navController: NavHostController = rememberNavController(),
                 startDestination: String = Routes.PROFILE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize()
    ) {
        composable(route = Routes.HOME){ Home() }
        composable(route = Routes.PROFILE){ Profile() }
    }
}


