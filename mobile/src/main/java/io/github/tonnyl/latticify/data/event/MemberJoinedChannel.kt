package io.github.tonnyl.latticify.data.event

import com.google.gson.annotations.SerializedName

/**
 * A user joined a public or private channel
 *
 * {
 * "type": "member_joined_channel",
 * "user": "W06GH7XHN",
 * "channel": "C0698JE0H",
 * "channel_type": "C",
 * "team": "T024BE7LD",
 * "inviter": "U123456789"
 * }
 *
 * The [MemberJoinedChannel] event is sent to all WebSocket connections and event subscriptions when users join public or private channels.
 * It's also triggered upon creating a new channel.
 *
 * @property user The provided [user] value is a user ID belonging to the user that joined the channel.
 * @property channel The [channel] value is the ID for a public channel or private channel (AKA group).
 * @property channelType The [channelType] value is a single letter indicating the type of channel used in channel:
 *                       C - typically a public channel
 *                       G - private channels (or groups) return this channel_type
 * @property team The [team] identifies which workspace the user is from.
 * @property inviter If the user was invited, the message will include an [inviter] property containing the user ID of the inviting user.
 *                   The property will be absent when a user manually joins a channel, or a user is added by default (e.g. #general channel).
 *                   Also, the property is not available when a channel is converted from a public to private, where the channel history is not shared with the user.
 *
 */
data class MemberJoinedChannel(

        @SerializedName("type")
        val type: String,

        @SerializedName("user")
        val user: String,

        @SerializedName("channel")
        val channel: String,

        @SerializedName("channel_type")
        val channelType: String,

        @SerializedName("team")
        val team: String,

        @SerializedName("inviter")
        val inviter: String?

)