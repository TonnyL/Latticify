package io.github.tonnyl.latticify.data.event

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.github.tonnyl.latticify.data.Attachment
import io.github.tonnyl.latticify.data.MessageEdited
import kotlinx.android.parcel.Parcelize

/**
 * A message was sent to a channel.
 *
 * @property channel The [channel] property is the ID of the channel, private group or DM channel this message is posted in.
 * @property user The [user] property is the ID of the user speaking.
 * @property text The text property is the text spoken.
 * @property ts The [ts] is the unique (per-channel) timestamp.
 *
 * {
 * "type": "message",
 * "channel": "C2147483705",
 * "user": "U2147483697",
 * "text": "Hello world",
 * "ts": "1355517523.000005"
 * }
 *
 * Messages can also include an attachments property, containing a list of [Attachment] objects.
 *
 * If the message has been edited after posting it will include an edited property, including the user ID of the editor, and the timestamp the edit happened.
 * The original text of the message is not available.
 *
 * {
 * "type": "message",
 * "channel": "C2147483705",
 * "user": "U2147483697",
 * "text": "Hello, world!",
 * "ts": "1355517523.000005",
 * "edited": {
 * "user": "U2147483697",
 * "ts": "1355517536.000001"
 * }
 * }
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class Message(

        @SerializedName("type")
        val type: String,

        @SerializedName("channel")
        val channel: String,

        @SerializedName("user")
        val user: String,

        @SerializedName("text")
        val text: String,

        @SerializedName("ts")
        val ts: String,

        @SerializedName("source_team")
        val sourceTeam: String?,

        @SerializedName("team")
        val team: String?,

        @SerializedName("subtype")
        val subtype: String?,

        @SerializedName("hidden")
        val hidden: Boolean?,

        @SerializedName("deleted_ts")
        val deletedTs: String?,

        @SerializedName("previous_message")
        val previousMessage: Message?,

        @SerializedName("message")
        val message: Message?,

        @SerializedName("event_ts")
        val eventTs: String?,

        @SerializedName("edited")
        val edited: MessageEdited?,

        @SerializedName("attachments")
        val attachments: List<Attachment>?

) : Parcelable