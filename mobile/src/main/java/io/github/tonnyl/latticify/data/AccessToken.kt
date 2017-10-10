package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

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
data class AccessToken(

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

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(AccessTokenPermission))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (ok) 1 else 0)
        parcel.writeString(accessToken)
        parcel.writeString(tokenType)
        parcel.writeString(appId)
        parcel.writeString(appUserId)
        parcel.writeString(installerUserId)
        parcel.writeString(scope)
        parcel.writeString(userId)
        parcel.writeString(teamName)
        parcel.writeString(teamId)
        parcel.writeTypedList(permissions)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<AccessToken> {
        override fun createFromParcel(parcel: Parcel): AccessToken {
            return AccessToken(parcel)
        }

        override fun newArray(size: Int): Array<AccessToken?> {
            return arrayOfNulls(size)
        }
    }

}
