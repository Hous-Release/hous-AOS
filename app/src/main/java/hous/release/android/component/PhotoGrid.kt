package hous.release.android.component

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.designsystem.graphic.skeletonBrush
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun PhotoGrid(
    modifier: Modifier = Modifier.fillMaxWidth(),
    photoFraction: Float = 0.6f,
    edgeWidthDp: Dp = 16.dp,
    spaceWidthDp: Dp = 12.dp,
    photos: List<File?>,
    onRemove: (photo: File) -> Unit = {},
    isEditable: Boolean = true
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceWidthDp)
    ) {
        itemsIndexed(items = photos, key = { index, _ -> index }) { idx, file ->
            if (idx == 0) Spacer(modifier = Modifier.width(edgeWidthDp))
            PhotoItem(
                modifier = photoWidthModifier(photoFraction),
                photo = file,
                onRemove = onRemove,
                isEditable = isEditable
            )
            if (idx == photos.lastIndex) Spacer(modifier = Modifier.width(edgeWidthDp))
        }
    }
}

@Composable
fun PhotoItem(
    modifier: Modifier,
    photo: File?,
    onRemove: (photo: File) -> Unit = {},
    isEditable: Boolean = true
) {
    val src = remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(key1 = photo) {
        if (photo == null) return@LaunchedEffect
        val bitmap = withContext(Dispatchers.IO) {
            BitmapFactory.decodeFile(photo.absolutePath)
        }
        bitmap?.let {
            src.value = it
        }
    }
    val imageModifier = Modifier.fillMaxSize(0.96f).clip(RoundedCornerShape(10.dp))
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = modifier
    ) {
        src.value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                modifier = imageModifier,
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )
            if (isEditable) {
                IconButton(
                    onClick = {
                        onRemove(photo!!)
                    },
                    modifier = Modifier.deleteButtonLayout()
                ) {
                    Image(
                        modifier = Modifier.fillMaxWidth(0.16f),
                        painter = painterResource(id = R.drawable.ic_image_delete),
                        contentDescription = "delete",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        } ?: Box(
            modifier = imageModifier.background(skeletonBrush())
        )
    }
}

private fun Modifier.deleteButtonLayout() = this.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val placeableY = (placeable.height * 0.3f).toInt()
    val placeableX = (placeable.width * 0.3f).toInt()
    layout(placeable.width, placeable.height) {
        placeable.place(
            x = placeableX,
            y = -placeableY
        )
    }
}

@SuppressLint("ModifierFactoryExtensionFunction", "UnnecessaryComposedModifier")
fun LazyItemScope.photoWidthModifier(fraction: Float): Modifier = Modifier.composed {
    this.fillParentMaxWidth(0.6f).aspectRatio(1f)
}
