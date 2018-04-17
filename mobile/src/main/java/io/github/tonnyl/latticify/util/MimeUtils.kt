package io.github.tonnyl.latticify.util

fun isImage(mimeType: String): Boolean = mimeType == "image/jpeg"
        || mimeType == "image/png"
        || mimeType == "image/gif"
        || mimeType == "image/x-ms-bmp"
        || mimeType == "image/webp"

fun isVideo(mimeType: String): Boolean = mimeType == "video/mpeg"
        || mimeType == "video/mp4"
        || mimeType == "video/quicktime"
        || mimeType == "video/3gpp"
        || mimeType == "video/3gpp2"
        || mimeType == "video/x-matroska"
        || mimeType == "video/webm"
        || mimeType == "video/mp2ts"
        || mimeType == "video/avi"
