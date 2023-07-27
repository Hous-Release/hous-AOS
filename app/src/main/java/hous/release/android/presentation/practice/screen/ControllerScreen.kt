package hous.release.android.presentation.practice.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import hous.release.designsystem.theme.HousTheme

@Composable
fun ControllerScreen(navController: NavHostController) {
    HousTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navController.navigate(GalleryScreens.OpenDocument.route) }) {
                Text(text = "OpenDocument")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(GalleryScreens.GetContent.route) }) {
                Text(text = "GetContent")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(GalleryScreens.ImagePicker.route) }) {
                Text(text = "ImagePicker")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
