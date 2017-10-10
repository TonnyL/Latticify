package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 23/09/2017.
 *
 * A User Group object contains information about a group of users.
 *
 * {
 * "id": "S0614TZR7",
 * "team_id": "T060RNRCH",
 * "is_usergroup": true,
 * "name": "Workspace Admins",
 * "description": "A group of all Administrators on your workspace.",
 * "handle": "admins",
 * "is_external": false,
 * "date_create": 1446598059,
 * "date_update": 1446670362,
 * "date_delete": 0,
 * "auto_type": "admin",
 * "created_by": "USLACKBOT",
 * "updated_by": "U060RNRCZ",
 * "deleted_by": null,
 * "prefs": {
 * "channels": [
 *
 * ],
 * "groups": [
 *
 * ]
 * },
 * "users": [
 * "U060RNRCZ",
 * "U060ULRC0",
 * "U06129G2V",
 * "U061309JM"
 * ],
 * "user_count": "4"
 * }
 */
data class UserGroup(
        @SerializedName("id")
        val id: String,

        @SerializedName("team_id")
        val teamId: String,

        @SerializedName("is_usergroup")
        val isUserGroup: Boolean,

        /**
         * The [name] parameter indicates the friendly name of the group.
         */
        @SerializedName("name")
        val name: String,

        /**
         * The [description] parameter explains the purpose of the group (optional).
         */
        @SerializedName("description")
        val description: String?,

        /**
         * The [handle] parameter indicates the value used to notify group members
         * via a mention without a leading @ sign.
         */
        @SerializedName("handle")
        val handle: String,

        @SerializedName("is_external")
        val isExternal: Boolean,

        @SerializedName("date_create")
        val dateCreate: Long,

        @SerializedName("date_update")
        val dateUpdate: Long,

        /**
         * For disabled groups, [dateDelete] will be non-zero.
         */
        @SerializedName("date_delete")
        val dateDelete: Long,

        /**
         * The [autoType] parameter can be admins for a Workspace Admins group,
         * owners for a Workspace Owners group or null for a custom group.
         */
        @SerializedName("auto_type")
        val autoType: String?,

        @SerializedName("created_by")
        val createdBy: String,

        @SerializedName("updated_by")
        val updatedBy: String,

        @SerializedName("deleted_by")
        val deletedBy: String?,

        /**
         * The [prefs] parameter contains default channels and groups (private channels)
         * that members of this group will be invited to upon joining.
         */
        @SerializedName("prefs")
        val prefs: Pref,

        /**
         * The [users] parameter contains a list of [User] object [User.id] values which belong to the User Group.
         * This parameter is included if the include_users option is enabled on some API endpoints.
         */
        @SerializedName("users")
        val users: ArrayList<String>,

        /**
         * The [userCount] parameter indicates the total number of users in a group.
         */
        @SerializedName("user_count")
        val userCount: Int
)