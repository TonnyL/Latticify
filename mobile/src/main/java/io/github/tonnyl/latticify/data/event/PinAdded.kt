package io.github.tonnyl.latticify.data.event

import com.google.gson.annotations.SerializedName
import io.github.tonnyl.latticify.data.StarredPinnedItem

/**
 * A pin was added to a channel.
 *
 * {
 * "type": "pin_added",
 * "user": "U024BE7LH",
 * "channel_id": "C02ELGNBH",
 * "item": {
 * …
 * },
 * "event_ts": "1360782804.083113"
 * }
 *
 * When an item is pinned in a channel, the [PinAdded] event is sent to all members of that channel.
 */

data class PinAdded(

        @SerializedName("type")
        val type: String,

        @SerializedName("user")
        val user: String,

        @SerializedName("channel_id")
        val channelId: String,

        @SerializedName("item")
        val item: List<StarredPinnedItem>,

        @SerializedName("event_ts")
        val eventTs: String

)