package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 02/11/2017.
 *
 *
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class RtmReceivedMessage(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("id")
        val id: String?,

        @SerializedName("reply_to")
        val replyTo: String?,

        /**
         * All supported Real Time Messaging event types:
         * accounts_changed -> The list of accounts a user is signed into has changed.
         * bot_added -> A bot user was added.
         * bot_changed -> A bot user was changed.
         * channel_archive -> A channel was archived.
         * channel_created -> A channel was created.
         * channel_deleted -> A channel was deleted.
         * channel_history_changed -> Bulk updates were made to a channel's history.
         * channel_joined -> You joined a channel.
         * channel_left -> You left a channel.
         * channel_marked -> Your channel read marker was updated.
         * channel_rename -> A channel was renamed.
         * channel_unarchive -> A channel was unarchived.
         * commands_changed -> A slash command has been added or changed.
         * dnd_updated -> Do not Disturb settings changed for the current user.
         * dnd_updated_user -> Do not Disturb settings changed for a member.
         * email_domain_changed -> The workspace email domain has changed.
         * emoji_changed -> A custom emoji has been added or changed.
         * file_change -> A file was changed.
         * file_comment_added -> A file comment was added.
         * file_comment_deleted -> A file comment was deleted.
         * file_comment_edited -> A file comment was edited.
         * file_created -> A file was created.
         * file_deleted -> A file was deleted.
         * file_public -> A file was made public.
         * file_shared -> A file was shared.
         * file_unshared -> A file was unshared.
         * goodbye -> The server intends to close the connection soon.
         * group_archive -> A private channel was archived.
         * group_close -> You closed a private channel.
         * group_history_changed -> Bulk updates were made to a private channel's history.
         * group_joined -> You joined a private channel.
         * group_left -> You left a private channel.
         * group_marked -> A private channel read marker was updated.
         * group_open -> You created a group DM.
         * group_rename -> A private channel was renamed.
         * group_unarchive -> A private channel was unarchived.
         * hello -> The client has successfully connected to the server.
         * im_close -> You closed a DM.
         * im_created -> A DM was created.
         * im_history_changed -> Bulk updates were made to a DM's history.
         * im_marked -> A direct message read marker was updated.
         * im_open -> You opened a DM.
         * manual_presence_change -> You manually updated your presence.
         * member_joined_channel -> A user joined a public or private channel.
         * member_left_channel -> A user left a public or private channel.
         * message -> A message was sent to a channel.
         * pin_added -> A pin was added to a channel.
         * pin_removed -> A pin was removed from a channel.
         * pref_change -> You have updated your preferences.
         * presence_change -> A member's presence changed.
         * presence_query -> Determine the current presence status for a list of users.
         * presence_sub -> Subscribe to presence events for the specified users.
         * ReactionAdded -> A member has added an emoji reaction to an item.
         * reaction_removed -> A member removed an emoji reaction.
         * reconnect_url -> Experimental.
         * star_added -> A member has starred an item.
         * star_removed -> A member removed a star.
         * subteam_created -> A User Group has been added to the workspace.
         * subteam_members_changed -> The membership of an existing User Group has changed.
         * subteam_self_added -> You have been added to a User Group.
         * subteam_self_removed -> You have been removed from a User Group.
         * subteam_updated -> An existing User Group has been updated or its members changed.
         * team_domain_change -> The workspace domain has changed.
         * team_join -> A new member has joined.
         * team_migration_started -> The workspace is being migrated between servers.
         * team_plan_change -> The account billing plan has changed.
         * team_pref_change -> A preference has been updated.
         * team_profile_change -> The workspace profile fields have been updated.
         * team_profile_delete -> The workspace profile fields have been deleted.
         * team_profile_reorder -> The workspace profile fields have been reordered.
         * team_rename -> The workspace name has changed.
         * user_change -> A member's data has changed.
         * user_typing -> A channel member is typing a message.
         *
         * See https://api.slack.com/rtm for more information.
         */
        @SerializedName("type")
        val type: String?,

        @SerializedName("ts")
        val ts: String?,

        @SerializedName("text")
        val text: String?,

        @SerializedName("error")
        val error: RtmReceivedErrorMessage?

) : Parcelable