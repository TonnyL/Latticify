package io.github.tonnyl.latticify.data.event

import com.google.gson.annotations.SerializedName

/**
 * A user left a public or private channel.
 *
 * {
 * "type": "member_joined_channel",
 * "user": "W06GH7XHN",
 * "channel": "C0698JE0H",
 * "channel_type": "C",
 * "team": "T024BE7LD",
 * }
 *
 * The [MemberLeftChannel] event is sent to all websocket connections and event subscriptions when users leave public or private channels.
 *
 * @property user The provided [user] value is a user ID belonging to the user that joined the channel.
 * @property channel The channel value is the ID for a public channel or private channel (AKA group).
 * @property channelType The [channelType] value is a single letter indicating the type of channel used in channel:
 *                       C - typically a public channel
 *                       G - private channels (or groups) return this channel_type
 * @property team The [team] identifies which workspace the user is from.
 *
 */
data class MemberLeftChannel(

        @SerializedName("type")
        val type: String,

        @SerializedName("user")
        val user: String,

        @SerializedName("channel")
        val channel: String,

        @SerializedName("channel_type")
        val channelType: String,

        @SerializedName("team")
        val team: String

)