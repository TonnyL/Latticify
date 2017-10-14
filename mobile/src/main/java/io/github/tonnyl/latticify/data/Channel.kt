package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 23/09/2017.
 *
 * A channel object contains information about a workspace channel.
 *
 * {
 * "id":"G4VV3JZ4H",
 * "name":"my_secret_channel",
 * "is_channel":false,
 * "is_group":true,
 * "is_im":false,
 * "created":1491730093,
 * "creator":"U4XAJCHM5",
 * "is_archived":false,
 * "is_general":false,
 * "unlinked":0,
 * "name_normalized":"my_secret_channel",
 * "is_shared":false,
 * "is_ext_shared":false,
 * "is_org_shared":false,
 * "pending_shared":[],
 * "is_pending_ext_shared":false,
 * "is_member":true,
 * "is_private":true,
 * "is_mpim":false,
 * "last_read":"1506217422.000033",
 * "latest":
 * {
 * "username":"IFTTT",
 * "icons":
 * {
 * "image_36":"https:\/\/a.slack-edge.com\/8f51\/img\/services\/ifttt_36.png",
 * "image_48":"https:\/\/a.slack-edge.com\/8f51\/img\/services\/ifttt_48.png",
 * "image_72":"https:\/\/a.slack-edge.com\/8f51\/img\/services\/ifttt_72.png"
 * },
 * "attachments":
 * [
 * {
 * "title":"Subject:New Slack API token issued",
 * "thumb_url":"https:\/\/ifttt.com\/images\/file_not_found.png",
 * "thumb_width":800,
 * "thumb_height":494,
 * "text":"From: ...",
 * "fallback":"From: ...",
 * "mrkdwn_in":
 * [
 * "text",
 * "pretext"
 * ]
 * }
 * ],
 * "mrkdwn":true,
 * "bot_id":"B4WM6V27N",
 * "text":null,
 * "type":"message",
 * "subtype":"bot_message",
 * "ts":"1506240102.000020"
 * },
 * "unread_count":2,
 * "unread_count_display":1,
 * "is_open":true,
 * "topic":
 * {
 * "value":"",
 * "creator":"U4XAJCHM5",
 * "last_set":1491730312
 * },
 * "purpose":
 * {
 * "value":"To receive gmails",
 * "creator":"U4XAJCHM5",
 * "last_set":1491730312
 * }
 * },
 */
data class Channel(

        @SerializedName("id")
        val id: String,

        /**
         * The [name] parameter indicates the name of the channel-like thing, without a leading hash sign.
         * Don't get too attached to that name. It might change. Don't bother storing it even.
         * When thinking about channel-like things, think about their IDs and their type and the team/workspace they belong to.
         */
        @SerializedName("name")
        val name: String?,

        /**
         * [isChannel] indicates whether a conversation is a public channel.
         * Everything said in a public channel can be read by anyone else belonging to a workspace.
         * [isPrivate] will be false.
         */
        @SerializedName("is_channel")
        val isChannel: Boolean?,

        /**
         * [isGroup] means the channel is a private channel.
         * [isPrivate] will also be true. Check yourself before you wreck yourself.
         */
        @SerializedName("is_group")
        val isGroup: Boolean?,

        /**
         * [isIm] means the conversation is a direct message between two distinguished individuals or a user and a bot.
         * Yes, it's an [isPrivate] conversation.
         */
        @SerializedName("is_im")
        val isIm: Boolean?,

        @SerializedName("user")
        val user: String?,

        @SerializedName("is_user_deleted")
        val isUserDeleted: Boolean?,

        /**
         * [created] is a unix timestamp.
         */
        @SerializedName("created")
        val created: Long,

        /**
         * [creator] is the user ID of the member that created this channel.
         */
        @SerializedName("creator")
        val creator: String?,

        /**
         * [isArchived] indicates a conversation is archived. Frozen in time.
         */
        @SerializedName("is_archived")
        val isArchived: Boolean?,

        /**
         * [isGeneral] means the channel is the workspace's "general" discussion channel
         * (even if it's not named #general but it might be anyway).
         * That might be important to your app because almost every user is a member.
         */
        @SerializedName("is_general")
        val isGeneral: Boolean?,

        @SerializedName("is_starred")
        val isStarred: Boolean?,

        @SerializedName("unlinked")
        val unlinked: Int?,

        @SerializedName("name_normalized")
        val nameNormalized: String?,

        /**
         * [isReadOnly] means the conversation can't be written to by typical users. Admins may have the ability.
         */
        @SerializedName("is_read_only")
        val isReadOnly: Boolean?,

        /**
         * [isShared] means the conversation is in some way shared between multiple workspaces.
         * Look for [isExtShared] and [isOrgShared] to learn which kind it is, and if that matters, act accordingly.
         */
        @SerializedName("is_shared")
        val isShared: Boolean?,

        /**
         * [isExtShared] represents this conversation as being part of a Shared Channel
         * with a remote organization. Your app should make sure the data it shares in such a channel
         * is appropriate for both workspaces. [isShared] will also be true.
         */
        @SerializedName("is_ext_shared")
        val isExtShared: Boolean?,

        /**
         * [isOrgShared] explains whether this shared channel is shared between Enterprise Grid workspaces within the same organization.
         * It's a little different from (externally) Shared Channels. Yet, [isShared] will be true.
         */
        @SerializedName("is_org_shared")
        val isOrgShared: Boolean?,

        /**
         * [isPendingExtShared] is intriguing. It means the conversation is ready to become an [isExtShared] channel
         * but isn't quite ready yet and needs some kind of approval or sign off. Best to treat it as if it were a shared channel,
         * even if it traverses only one workspace.
         */
        @SerializedName("is_pending_ext_shared")
        val isPendingExtShared: Boolean?,

        /**
         * [isMember] indicates the user or bot user or Slack app associated with the token
         * making the API call is itself a member of the conversation.
         */
        @SerializedName("is_member")
        val isMember: Boolean?,

        /**
         * [isPrivate] means the conversation is privileged between two or more members. Meet their privacy expectations.
         */
        @SerializedName("is_private")
        val isPrivate: Boolean?,

        /**
         * [isMpim] represents an unnamed private conversation between multiple users.
         * It's an [isPrivate] kind of thing.
         */
        @SerializedName("is_mpim")
        val isMpim: Boolean?,

        /**
         * [lastRead] is the timestamp for the last message the calling user has read in this channel.
         */
        @SerializedName("last_read")
        val lastRead: String?,

        @SerializedName("latest")
        val latest: Message?,

        @SerializedName("unread_count")
        val unreadCount: Int?,

        @SerializedName("unread_count_display")
        val unreadCountDisplay: Int?,

        @SerializedName("is_open")
        val isOpen: Boolean?,

        /**
         * [topic] provides information about the channel topic.
         */
        @SerializedName("topic")
        val topic: TopicPurpose?,

        /**
         * [purpose] provides information about the channel purpose.
         */
        @SerializedName("purpose")
        val purpose: TopicPurpose?,

        @SerializedName("previous_names")
        val previousNames: List<String>?,

        @SerializedName("num_members")
        val numMembers: Int?,

        @SerializedName("members")
        val members: List<String>?,

        @SerializedName("locale")
        val locale: String?

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readLong(),
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readString(),
            parcel.readParcelable(Message::class.java.classLoader),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readParcelable(TopicPurpose::class.java.classLoader),
            parcel.readParcelable(TopicPurpose::class.java.classLoader),
            parcel.createStringArrayList(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.createStringArrayList(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeValue(isChannel)
        parcel.writeValue(isGroup)
        parcel.writeValue(isIm)
        parcel.writeString(user)
        parcel.writeValue(isUserDeleted)
        parcel.writeLong(created)
        parcel.writeString(creator)
        parcel.writeValue(isArchived)
        parcel.writeValue(isGeneral)
        parcel.writeValue(isStarred)
        parcel.writeValue(unlinked)
        parcel.writeString(nameNormalized)
        parcel.writeValue(isReadOnly)
        parcel.writeValue(isShared)
        parcel.writeValue(isExtShared)
        parcel.writeValue(isOrgShared)
        parcel.writeValue(isPendingExtShared)
        parcel.writeValue(isMember)
        parcel.writeValue(isPrivate)
        parcel.writeValue(isMpim)
        parcel.writeString(lastRead)
        parcel.writeParcelable(latest, flags)
        parcel.writeValue(unreadCount)
        parcel.writeValue(unreadCountDisplay)
        parcel.writeValue(isOpen)
        parcel.writeParcelable(topic, flags)
        parcel.writeParcelable(purpose, flags)
        parcel.writeStringList(previousNames)
        parcel.writeValue(numMembers)
        parcel.writeStringList(members)
        parcel.writeString(locale)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Channel> {
        override fun createFromParcel(parcel: Parcel): Channel {
            return Channel(parcel)
        }

        override fun newArray(size: Int): Array<Channel?> {
            return arrayOfNulls(size)
        }
    }

}