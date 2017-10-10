package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 09/10/2017.
 */
data class GroupsWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("groups")
        val groups: ArrayList<Channel>,

        @SerializedName("response_metadata")
        val responseMetaData: ResponseMetaData?

)