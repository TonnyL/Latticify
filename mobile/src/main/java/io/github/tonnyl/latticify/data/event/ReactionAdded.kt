package io.github.tonnyl.latticify.data.event

import com.google.gson.annotations.SerializedName

/**
 * A member has added an emoji reaction to an item.
 *
 * {
 * "type": "reaction_removed",
 * "user": "U024BE7LH",
 * "reaction": "thumbsup",
 * "item_user":"U0G9QF9C6",
 * "item": {
 * ...
 * },
 * "event_ts": "1360782804.083113"
 * }
 *
 * @property user The [user] field indicates the ID of the user who performed this event.
 * @property itemUser The [itemUser] field represents the ID of the user that created the original item that has been reacted to.
 *
 * When a reaction is added to an item the [ReactionAdded] event is sent to all connected clients for users who can see the content that was reacted to.
 */
data class ReactionAdded(

        @SerializedName("type")
        val type: String,

        @SerializedName("user")
        val user: String,

        @SerializedName("reaction")
        val reaction: String,

        @SerializedName("item_user")
        val itemUser: String,

        @SerializedName("item")
        val item: ReactionEmbeddedItem,

        @SerializedName("event_ts")
        val eventTs: String

)