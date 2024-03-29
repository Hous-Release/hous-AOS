package hous.release.android.presentation.practice.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import hous.release.android.presentation.practice.PhotoViewModel
import hous.release.designsystem.theme.HousTheme
import kotlinx.coroutines.launch

@Composable
fun ImagePickerScreen(
    navController: NavController,
    viewModel: PhotoViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val takePhotoFromAlbumLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(5)
    ) { uriList ->
        if (uriList.isNotEmpty()) {
            viewModel.loadImagesFrom(uriList)
            return@rememberLauncherForActivityResult
        }
        coroutineScope.launch {
            snackbarHostState.showSnackbar("You haven't completed all details")
        }
    }

    HousTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                takePhotoFromAlbumLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }) {
                Text(text = "Select Photo")
            }
//            PhotoGrid(photos = state.photos, onRemove = viewModel::removePhoto)
        }
    }
}
