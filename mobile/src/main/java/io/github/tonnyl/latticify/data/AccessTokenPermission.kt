package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 * {
 * "scopes": [
 * "channels:read",
 * "chat:write:user"
 * ],
 * "resource_type": "channel",
 * "resource_id": 0
 * }
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class AccessTokenPermission(

        @SerializedName("scopes")
        val scopes: List<String>,

        @SerializedName("resource_type")
        val resourceType: String,

        @SerializedName("resource_id")
        val resourceId: Int

) : Parcelable