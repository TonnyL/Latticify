package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 19/09/2017.
 *
 * {
 * "ok": true,
 * "access_token": "xoxa-access-token-string",
 * "token_type": "app",
 * "app_id": "A012345678",
 * "app_user_id": "U0AB12ABC",
 * "installer_user_id": "U061F7AUR",
 * "team_name": "Subarachnoid Workspace",
 * "team_id": "T061EG9Z9",
 * "permissions": [
 * {
 * "scopes": [
 * "channels:read",
 * "chat:write:user"
 * ],
 * "resource_type": "channel",
 * "resource_id": 0
 * }
 * ]
 * }
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class AccessToken(

        @SerializedName("id")
        val id: Long? = null,

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("access_token")
        val accessToken: String,

        @SerializedName("token_type")
        val tokenType: String?,

        @SerializedName("app_id")
        val appId: String,

        @SerializedName("app_user_id")
        val appUserId: String?,

        @SerializedName("installer_user_id")
        val installerUserId: String?,

        @SerializedName("scope")
        val scope: String?,

        @SerializedName("user_id")
        val userId: String?,

        @SerializedName("team_name")
        val teamName: String,

        @SerializedName("team_id")
        val teamId: String,

        @SerializedName("permissions")
        val permissions: List<AccessTokenPermission>?

) : Parcelable