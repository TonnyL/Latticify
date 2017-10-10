package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

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
data class AccessTokenPermission(

        @SerializedName("scopes")
        val scopes: List<String>,

        @SerializedName("resource_type")
        val resourceType: String,

        @SerializedName("resource_id")
        val resourceId: Int

) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.createStringArrayList(),
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(scopes)
        parcel.writeString(resourceType)
        parcel.writeInt(resourceId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<AccessTokenPermission> {
        override fun createFromParcel(parcel: Parcel): AccessTokenPermission {
            return AccessTokenPermission(parcel)
        }

        override fun newArray(size: Int): Array<AccessTokenPermission?> {
            return arrayOfNulls(size)
        }
    }

}