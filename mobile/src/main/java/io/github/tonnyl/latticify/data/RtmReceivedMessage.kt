package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 02/11/2017.
 *
 *
 */
data class RtmReceivedMessage(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("id")
        val id: String?,

        @SerializedName("reply_to")
        val replyTo: String?,

        // Several types: typing, ping, or nothing
        @SerializedName("type")
        val type: String?,

        @SerializedName("ts")
        val ts: String?,

        @SerializedName("text")
        val text: String?,

        @SerializedName("error")
        val error: RtmReceivedErrorMessage?

)