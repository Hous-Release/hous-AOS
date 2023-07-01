package hous.release.android.presentation.practice.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hous.release.android.presentation.practice.ControllerScreen

@Composable
fun GalleryNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = GalleryScreens.Controller.route) {
        composable(GalleryScreens.Controller.route) {
            ControllerScreen(navController)
        }
        composable(GalleryScreens.OpenDocument.route) { OpenDocumentScreen() }
//                composable(GalleryScreens.GetContent.route) { AddLogScreen(navController) }
//                composable(GalleryScreens.ImagePicker.route) { CameraScreen(navController) }
    }
}
