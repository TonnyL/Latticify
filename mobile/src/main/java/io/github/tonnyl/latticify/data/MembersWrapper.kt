package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 09/10/2017.
 */
data class MembersWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("members")
        val members: List<String>,

        @SerializedName("response_metadata")
        val responseMetaData: ResponseMetaData

)