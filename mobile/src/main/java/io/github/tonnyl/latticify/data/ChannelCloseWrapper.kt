package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 09/10/2017.
 */
data class ChannelCloseWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("no_op")
        val noOp: Boolean?,

        @SerializedName("already_closed")
        val alreadyClosed: Boolean?

)