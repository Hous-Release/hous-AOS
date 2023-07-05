package hous.release.android.presentation.practice.screen

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hous.release.android.presentation.practice.BitmapManager
import hous.release.android.presentation.practice.findActivity
import hous.release.designsystem.theme.HousTheme
import kotlinx.coroutines.launch

@Composable
fun GetContentScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current.findActivity()
    val (bitmapList, setBitmapList) = remember { mutableStateOf<List<Bitmap>>(listOf()) }
    val takePhotoFromAlbumLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uriList ->
        if (uriList.isNotEmpty()) {
            setBitmapList(
                uriList.map {
                    BitmapManager(activity).optimizeBitmapFromUri(it)
                }
            )
            return@rememberLauncherForActivityResult
        }
        coroutineScope.launch {
            snackbarHostState.showSnackbar("You haven't completed all details")
        }
    }

    HousTheme {
        // Call the takePhotoFromAlbumLauncher to launch the gallery
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { takePhotoFromAlbumLauncher.launch("image/*") }) {
                Text(text = "Select Photo")
            }
            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(items = bitmapList, key = { index, _ -> index }) { index, bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "image",
                        modifier = Modifier
                            .size(80.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}
