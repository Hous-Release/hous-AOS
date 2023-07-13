package hous.release.android.presentation.practice.screen.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import hous.release.android.presentation.practice.screen.GalleryScreens
import hous.release.android.presentation.practice.screen.GetContentScreen
import hous.release.android.presentation.practice.screen.ImagePickerScreen
import hous.release.android.presentation.practice.screen.OpenDocumentScreen

fun NavGraphBuilder.galleryNavGraph(navController: NavController) {
    navigation(startDestination = GalleryScreens.OpenDocument.route, route = GalleryScreens.ROUTE) {
        composable(GalleryScreens.OpenDocument.route) {
            OpenDocumentScreen(
                navController,
                hiltViewModel()
            )
        }
        composable(GalleryScreens.GetContent.route) {
            GetContentScreen(
                navController,
                hiltViewModel()
            )
        }
        composable(GalleryScreens.ImagePicker.route) {
            ImagePickerScreen(
                navController,
                hiltViewModel()
            )
        }
    }
}
