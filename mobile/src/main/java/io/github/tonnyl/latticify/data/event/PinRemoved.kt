package io.github.tonnyl.latticify.data.event

import com.google.gson.annotations.SerializedName
import io.github.tonnyl.latticify.data.StarredPinnedItem

/**
 * A pin was removed from a channel
 *
 * {
 * "type": "pin_removed",
 * "user": "U024BE7LH",
 * "channel_id": "C02ELGNBH",
 * "item": {
 * …
 * },
 * "has_pins": false,
 * "event_ts": "1360782804.083113"
 * }
 *
 * @property hasPins The [hasPins] property indicates that there are other pinned items in that channel.
 *
 * When an item is un-pinned from a channel, the [PinRemoved] event is sent to all members of that channel.
 */
data class PinRemoved(

        @SerializedName("type")
        val type: String,

        @SerializedName("user")
        val user: String,

        @SerializedName("channel_id")
        val channelId: String,

        @SerializedName("item")
        val item: List<StarredPinnedItem>,

        @SerializedName("has_pins")
        val hasPins: Boolean,

        @SerializedName("event_ts")
        val eventTs: String

)