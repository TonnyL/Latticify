package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 23/09/2017.
 *
 * A mpim object contains information about a multiparty IM.
 * For compatibility with older clients, mpims can appear as groups
 * unless rtm.start is called with mpim_aware=1.
 *
 * {
 * "id": "G024BE91L",
 * "name": "mpdm-user1--user2--user3-1",
 * "is_mpim": true,
 * "is_group": false,
 * "created": 1360782804,
 * "creator": "U024BE7LH",
 * "members": [
 * "U024BE7LH"
 * ],
 * "last_read": "1401383885.000061",
 * "latest": { … }
 * "unread_count": 0,
 * "unread_count_display": 0
 * },
 */
data class MultiPartyIM(
        @SerializedName("id")
        val id: String,

        /**
         * The [name] parameter indicates the name of the mpim.
         */
        @SerializedName("name")
        val name: String,

        /**
         * [isMpim] is a boolean that indicated if a multiparty im (mpim) is being emulated as a group.
         */
        @SerializedName("is_mpim")
        val isMpim: Boolean,

        @SerializedName("is_group")
        val isGroup: Boolean,

        /**
         * [created] is a unix timestamp.
         */
        @SerializedName("created")
        val created: Long,

        /**
         * [creator] is the user ID of the member that created this mpim.
         */
        @SerializedName("creator")
        val creator: String,

        /**
         * [members] is a list of user ids for all users in this mpim.
         * This includes any disabled accounts that were in this mpim when they were disabled.
         */
        @SerializedName("members")
        val members: ArrayList<String>,

        @SerializedName("last_read")
        val lastRead: String,

        @SerializedName("latest")
        val latest: Any,

        @SerializedName("unread_count")
        val unreadCount: Int,

        @SerializedName("unread_count_display")
        val unreadCountDisplay: Int
)