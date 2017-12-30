package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 30/12/2017.
 */
data class FileWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("file")
        val file: File,

        @SerializedName("content")
        val content: String,

        @SerializedName("is_truncated")
        val isTruncated: Boolean,

        @SerializedName("content_highlight_html")
        val contentHighlightHtml: String,

        @SerializedName("content_highlight_css")
        val contentHighlightCss: String,

        @SerializedName("comments")
        val comments: List<FileComment>,

        @SerializedName("paging")
        val paging: Paging

)