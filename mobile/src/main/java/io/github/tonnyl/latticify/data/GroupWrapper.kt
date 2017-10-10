package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 09/10/2017.
 *
 *
 */
data class GroupWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("group")
        val group: Channel

)