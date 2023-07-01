package hous.release.android.presentation.practice.screen

sealed class GalleryScreens(val route: String) {
    object Controller : GalleryScreens("controller")
    object OpenDocument : GalleryScreens("open_document_screen")
    object GetContent : GalleryScreens("get_content_route")
    object ImagePicker : GalleryScreens("image_picker_route")
}
