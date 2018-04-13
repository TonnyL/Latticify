package io.github.tonnyl.latticify.data.event

import com.google.gson.annotations.SerializedName

/**
 * [ReactionEmbeddedItem] includes several structures:
 *
 * Message:
 * "item": {
 * "type": "message",
 * "channel": "C0G9QF9GZ",
 * "ts": "1360782400.498405"
 * }
 *
 * File:
 * "item": {
 * "type": "file",
 * "file": "F0HS27V1Z"
 * }
 *
 * File Comment:
 * "item": {
 * "type": "file_comment",
 * "file_comment": "Fc0HS2KBEZ",
 * "file": "F0HS27V1Z"
 * }
 *
 */
data class ReactionEmbeddedItem(

        @SerializedName("type")
        val type: String,

        @SerializedName("channel")
        val channel: String?,

        @SerializedName("ts")
        val ts: String?,

        @SerializedName("file")
        val file: String?,

        @SerializedName("file_comment")
        val fileComment: String?

)