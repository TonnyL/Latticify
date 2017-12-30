package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 30/12/2017.
 */
data class FileListWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("files")
        val files: List<File>,

        @SerializedName("paging")
        val paging: Paging

)