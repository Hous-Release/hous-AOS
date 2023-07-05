package hous.release.android.presentation.practice.screen.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hous.release.android.presentation.practice.screen.ControllerScreen
import hous.release.android.presentation.practice.screen.GalleryScreens

@Composable
fun HomeNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = GalleryScreens.Controller.route,
        route = "dd"
    ) {
        composable(GalleryScreens.Controller.route) { ControllerScreen(navController) }
        galleryNavGraph(navController)
    }
}
