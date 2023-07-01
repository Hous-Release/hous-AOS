package hous.release.android.presentation.practice

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.navigation.compose.rememberNavController
import hous.release.android.presentation.practice.screen.GalleryNavGraph
import hous.release.android.presentation.practice.screen.GalleryScreens
import hous.release.designsystem.theme.HousTheme

class GalleryPracticeActivity : ComponentActivity() {
    val getContent =
        registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri: Uri? ->
            // Handle the returned Uri
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            GalleryNavGraph(navController)
        }
    }
}

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
            Button(onClick = { navController.navigate(GalleryScreens.OpenDocument.route) }) {
                Text(text = "OpenDocument")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(GalleryScreens.OpenDocument.route) }) {
                Text(text = "OpenDocument")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Suppress("DEPRECATION", "NewApi")
fun Uri.parseBitmap(context: Context): Bitmap {
    return when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { // 28
        true -> {
            val source = ImageDecoder.createSource(context.contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        }

        else -> {
            MediaStore.Images.Media.getBitmap(context.contentResolver, this)
        }
    }
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}
