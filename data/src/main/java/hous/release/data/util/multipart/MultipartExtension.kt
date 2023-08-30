package hous.release.data.util.multipart

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun File.toImagePart(): MultipartBody.Part {
    val requestBody = this.asRequestBody("image/jpeg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("images ", this.name, requestBody)
}
