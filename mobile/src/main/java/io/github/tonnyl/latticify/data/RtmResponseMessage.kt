package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 02/11/2017.
 *
 *
 */
data class RtmResponseMessage(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("reply_to")
        val replyTo: String?,

        @SerializedName("ts")
        val ts: String,

        @SerializedName("text")
        val text: String?

)