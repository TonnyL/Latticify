package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 30/12/2017.
 */
data class FileCommentWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("comment")
        val comment: FileComment

)