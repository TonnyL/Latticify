package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 13/10/2017.
 *
 */
data class SearchedAllWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("query")
        val query: String,

        @SerializedName("messages")
        val messages: SearchedMessages,

        @SerializedName("files")
        val files: SearchedFiles

)