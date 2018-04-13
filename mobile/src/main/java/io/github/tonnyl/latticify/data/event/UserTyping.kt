package io.github.tonnyl.latticify.data.event

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A channel member is typing a message.
 *
 * {
 * "type": "user_typing",
 * "channel": "C7FSD9QDQ",
 * "user": "UA5C8NZGT"
 * }
 *
 * The user_typing event is sent to all members of a channel when a user is typing a message in that channel.
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class UserTyping(

        @SerializedName("type")
        val type: String,

        @SerializedName("channel")
        val channel: String,

        @SerializedName("user")
        val user: String

) : Parcelable