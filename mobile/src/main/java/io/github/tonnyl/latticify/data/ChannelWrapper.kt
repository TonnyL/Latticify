package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 * {
 * "ok": true,
 * "channel": {
 * "id": "C024BE91L",
 * "name": "fun",
 *
 * "created": 1360782804,
 * "creator": "U024BE7LH",
 *
 * "is_archived": false,
 * "is_general": false,
 * "is_member": true,
 * "is_starred": true,
 *
 * "members": [ ... ],
 *
 * "topic": { ... },
 * "purpose": { ... },
 *
 * "last_read": "1401383885.000061",
 * "latest": { ... },
 * "unread_count": 0,
 * "unread_count_display": 0
 * }
 * }
 */
data class ChannelWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("channel")
        val channel: Channel
)