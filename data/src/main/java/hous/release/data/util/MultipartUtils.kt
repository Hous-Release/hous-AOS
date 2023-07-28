package hous.release.data.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun String.toPlainTextRequestBody(): RequestBody =
    this.toRequestBody("text/plain".toMediaTypeOrNull())

fun File.toImagePart(name: String): MultipartBody.Part {
    val requestBody = this.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name, this.name, requestBody)
}
