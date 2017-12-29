package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.github.tonnyl.latticify.database.converters.AccessTokenTypeConverters
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
@Entity(tableName = "access_tokens")
@TypeConverters(AccessTokenTypeConverters::class)
@Parcelize
@SuppressLint("ParcelCreator")
data class AccessToken(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Long? = null,

        @SerializedName("ok")
        val ok: Boolean,

        @ColumnInfo(name = "access_token")
        @SerializedName("access_token")
        val accessToken: String,

        @ColumnInfo(name = "token_type")
        @SerializedName("token_type")
        val tokenType: String?,

        @ColumnInfo(name = "app_id")
        @SerializedName("app_id")
        val appId: String,

        @ColumnInfo(name = "app_user_id")
        @SerializedName("app_user_id")
        val appUserId: String?,

        @ColumnInfo(name = "installer_user_id")
        @SerializedName("installer_user_id")
        val installerUserId: String?,

        @ColumnInfo(name = "scope")
        @SerializedName("scope")
        val scope: String?,

        @ColumnInfo(name = "user_id")
        @SerializedName("user_id")
        val userId: String?,

        @ColumnInfo(name = "team_name")
        @SerializedName("team_name")
        val teamName: String,

        @ColumnInfo(name = "team_id")
        @SerializedName("team_id")
        val teamId: String,

        @ColumnInfo(name = "permissions")
        @SerializedName("permissions")
        val permissions: List<AccessTokenPermission>?

) : Parcelable