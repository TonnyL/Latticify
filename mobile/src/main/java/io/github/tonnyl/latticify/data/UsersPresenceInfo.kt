package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 * {
 * "ok": true,
 * "presence": "active",
 * "online": true,
 * "auto_away": false,
 * "manual_away": false,
 * "connection_count": 1,
 * "last_activity": 1419027078
 * }
 */
data class UsersPresenceInfo(

        @SerializedName("ok")
        val ok: Boolean,

        /**
         * Indicates the user's overall presence, it will either be active or away.
         */
        @SerializedName("presence")
        val presence: String,

        /**
         * [online] will be true if they have a client currently connected to Slack.
         */
        @SerializedName("online")
        val online: Boolean?,

        /**
         * [autoAway] will be true if our servers haven't detected any activity from the user in the last 30 minutes.
         */
        @SerializedName("auto_away")
        val autoAway: Boolean?,

        /**
         * [manualAway] will be true if the user has manually set their presence to away.
         */
        @SerializedName("manual_away")
        val manualAway: Boolean?,

        /**
         * Gives a count of total connections.
         */
        @SerializedName("connection_count")
        val connectionCount: Int?,

        /**
         * Indicates the last activity seen by our servers. If a user has no connected clients then this property will be absent.
         */
        @SerializedName("last_activity")
        val lastActivity: Long?

)