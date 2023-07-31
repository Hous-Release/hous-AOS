package hous.release.data.util.multipart

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File

fun File.toImagePart(): MultipartBody.Part {
    Timber.e("File이 존재하나요? ${this.exists()}")
    Timber.e("absolutePath: ${this.absolutePath}")
    val requestBody = this.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", this.name, requestBody)
}
