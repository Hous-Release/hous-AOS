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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import hous.release.android.component.PhotoGrid
import hous.release.android.presentation.practice.PhotoViewModel
import hous.release.android.presentation.practice.findActivity
import hous.release.designsystem.theme.HousTheme
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ImagePickerScreen(navController: NavController, viewModel: PhotoViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current.findActivity()
    val (fileList, setFileList) = remember { mutableStateOf<List<File?>>(listOf()) }
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
            PhotoGrid(photos = state.photos, onRemove = viewModel::removePhoto)
        }
    }
}
