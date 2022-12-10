package hous.release.android.util.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import hous.release.android.R
import hous.release.android.util.style.HousTheme
import hous.release.domain.entity.HomyType

@Preview(showBackground = true)
@Composable
fun PersonalityResultImagePreview() {
    HousTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PersonalityResultImage(
                HomyType.BLUE,
                imageUrl = "drawable.ic_tutorial_1"
            )
        }
    }
}

@Composable
fun PersonalityResultImage(
    homyType: HomyType,
    imageUrl: String
) {
    val shadowColor = when (homyType) {
        HomyType.YELLOW -> R.color.hous_yellow
        HomyType.RED -> R.color.hous_red
        HomyType.BLUE -> R.color.hous_blue
        HomyType.PURPLE -> R.color.hous_purple
        HomyType.GREEN -> R.color.hous_green
        HomyType.GRAY -> R.color.hous_g_1
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 20.dp)
            .aspectRatio(1.0f)
            .shadow(
                ambientColor = colorResource(shadowColor),
                spotColor = colorResource(shadowColor),
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp)
            ),
        elevation = 10.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}
