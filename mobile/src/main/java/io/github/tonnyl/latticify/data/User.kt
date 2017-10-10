package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 23/09/2017.
 *
 * "id": "W012A3CDE",
 * "team_id": "T012AB3C4",
 * "name": "spengler",
 * "deleted": false,
 * "color": "9f69e7",
 * "real_name": "Egon Spengler",
 * "tz": "America/Los_Angeles",
 * "tz_label": "Pacific Daylight Time",
 * "tz_offset": -25200,
 * "profile": {
 * "avatar_hash": "ge3b51ca72de",
 * "status_text": "Print is dead",
 * "status_emoji": ":books:",
 * "real_name": "Egon Spengler",
 * "display_name": "spengler",
 * "real_name_normalized": "Egon Spengler",
 * "display_name_normalized": "spengler",
 * "email": "spengler@ghostbusters.example.com",
 * "image_24": "https://.../avatar/e3b51ca72dee4ef87916ae2b9240df50.jpg",
 * "image_32": "https://.../avatar/e3b51ca72dee4ef87916ae2b9240df50.jpg",
 * "image_48": "https://.../avatar/e3b51ca72dee4ef87916ae2b9240df50.jpg",
 * "image_72": "https://.../avatar/e3b51ca72dee4ef87916ae2b9240df50.jpg",
 * "image_192": "https://.../avatar/e3b51ca72dee4ef87916ae2b9240df50.jpg",
 * "image_512": "https://.../avatar/e3b51ca72dee4ef87916ae2b9240df50.jpg",
 * "team": "T012AB3C4"
 * },
 * "is_admin": true,
 * "is_owner": false,
 * "is_primary_owner": false,
 * "is_restricted": false,
 * "is_ultra_restricted": false,
 * "is_bot": false,
 * "updated": 1502138686,
 * "is_app_user": false,
 * "has_2fa": false,
 * "locale": "en-US"
 */
data class User(

        /**
         * The [id] field is a string identifier for this team member.
         * It is only unique to the workspace/team containing the user.
         * Use this field instead of the [name] field when storing related data or when specifying the user in API requests.
         * Though the ID field usually begins with U, it is also possible to encounter user IDs beginning with W.
         */
        @SerializedName("id")
        val id: String,

        @SerializedName("team_id")
        val teamId: String,

        /**
         * The [name] parameter indicates the username for this user, without a leading @ sign.
         */
        @SerializedName("name")
        val name: String,

        /**
         * For deactivated users, [deleted] will be true.
         */
        @SerializedName("deleted")
        val deleted: Boolean,

        /**
         * The [color] field is used in some clients to display a colored username.
         */
        @SerializedName("color")
        val color: String,

        @SerializedName("real_name")
        val realName: String,

        /**
         * Those users with a provided timezone will have populated [tz], [tzLabel], and [tzOffset] fields.
         * [tz] provides a somewhat human readable string for the geographic region like "America/Los_Angels".
         */
        @SerializedName("tz")
        val tz: String,

        /**
         * [tzLabel] is a string describing the name of that timezone (like "Pacific Standard Time").
         */
        @SerializedName("tz_label")
        val tzLabel: String,

        /**
         * [tzOffset] is a signed integer indicating the number of seconds to offset UTC time by.
         */
        @SerializedName("tz_offset")
        val tzOffset: Long,

        /**
         * The [profile] hash contains as much information as the user has supplied in the default
         * profile fields: first_name, last_name, real_name, display_name, email, skype, and the image_* fields.
         * Only the image_* fields are guaranteed to be included. Email address access may require additional OAuth scopes.
         *
         * Data that has not been supplied may not be present at all, may be null or may contain the empty string ("").
         * A user's custom profile fields may be discovered using users.profile.get.
         */
        @SerializedName("profile")
        val profile: UsersProfile,

        /**
         * A boolean value indicating whether this user administers this enterprise
         */
        @SerializedName("is_admin")
        val isAdmin: Boolean,

        /**
         * A boolean value indicating whether this user is the owner of this enterprise
         */
        @SerializedName("is_owner")
        val isOwner: Boolean,

        @SerializedName("is_primary_owner")
        val isPrimaryOwner: Boolean,

        @SerializedName("is_restricted")
        val isRestricted: Boolean,

        @SerializedName("is_ultra_restricted")
        val isUltraRestricted: Boolean,

        @SerializedName("is_bot")
        val isBot: Boolean,

        @SerializedName("updated")
        val updated: Long,

        @SerializedName("is_app_user")
        val isAppUser: Boolean,

        /**
         * The [has2fa] field describes whether two-step verification is enabled for this user.
         * This field will always be displayed if you are looking at your own user information.
         * If you are looking at another user's information this field will only be displayed if you are Workspace Admin or owner.
         */
        @SerializedName("has_2fa")
        val has2fa: Boolean,

        @SerializedName("locale")
        val locale: String

)