package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 23/09/2017.
 *
 * A group object contains information about a private channel.
 * Private channels were once known as "private groups."
 * Consider a group object the same thing as a private channel object.
 *
 * {
 * "id": "G024BE91L",
 * "name": "secretplans",
 * "is_group": "true",
 * "created": 1360782804,
 * "creator": "U024BE7LH",
 * "is_archived": false,
 * "is_mpim": false,
 * "members": [
 * "U024BE7LH"
 * ],
 * "topic": {
 * "value": "Secret plans on hold",
 * "creator": "U024BE7LV",
 * "last_set": 1369677212
 * },
 * "purpose": {
 * "value": "Discuss secret plans that no-one else should know",
 * "creator": "U024BE7LH",
 * "last_set": 1360782804
 * },
 *
 * "last_read": "1401383885.000061",
 * "latest": { … }
 * "unread_count": 0,
 * "unread_count_display": 0
 *
 * },
 */
data class Group(

        @SerializedName("id")
        val id: String,

        /**
         * The [name] parameter indicates the name of the private channel.
         */
        @SerializedName("name")
        val name: String,

        @SerializedName("is_group")
        val isGroup: Boolean,

        /**
         * [created] is a unix timestamp.
         */
        @SerializedName("created")
        val created: Long,

        /**
         * [creator] is the user ID of the member that created this private channel.
         */
        @SerializedName("creator")
        val creator: String,

        /**
         * [isArchived] will be true if the private channel is archived.
         */
        @SerializedName("is_archived")
        val isArchived: Boolean,

        /**
         * [isMpim] is a boolean that indicated if a multiparty im ([MultiPartyIM]) is being emulated as a private channel.
         * For compatibility with older clients, [isMpim] can appear as private channels unless rtm.start is called with mpim_aware=1.
         */
        @SerializedName("is_mpim")
        val isMpim: Boolean,

        /**
         * [members] is a list of user ids for all users in this private channel.
         * This includes any disabled accounts that were in this private channel when they were disabled.
         */
        @SerializedName("members")
        val members: ArrayList<String>,

        /**
         * [topic] provides information about the private channel topic.
         */
        @SerializedName("topic")
        val topic: TopicPurpose,

        /**
         * [purpose] provides information about the private channel purpose.
         */
        @SerializedName("purpose")
        val purpose: TopicPurpose,

        /**
         * [lastRead] is the timestamp for the last message the calling user has read in this channel.
         */
        @SerializedName("last_read")
        val lastRead: String,

        /**
         * [latest] is the latest message in the channel.
         */
        @SerializedName("latest")
        val latest: Message,

        /**
         * [unreadCount] is a full count of visible messages that the calling user has yet to read.
         */
        @SerializedName("unread_count")
        val unreadCount: Int,

        /**
         * [unreadCountDisplay] is a count of messages that the calling user has yet to read that matter to them
         * (this means it excludes things like join/leave messages).
         */
        @SerializedName("unread_count_display")
        val unreadCountDisplay: Int,

        @SerializedName("is_open")
        val isOpen: Boolean
)