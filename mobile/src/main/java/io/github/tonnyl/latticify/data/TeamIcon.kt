package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 * "icon": {
 * "image_34": "https:\/\/...",
 * "image_44": "https:\/\/...",
 * "image_68": "https:\/\/...",
 * "image_88": "https:\/\/...",
 * "image_102": "https:\/\/...",
 * "image_132": "https:\/\/...",
 * "image_default": true
 * }
 */
data class TeamIcon(

        @SerializedName("image_34")
        val image34: String,

        @SerializedName("image_44")
        val image44: String,

        @SerializedName("image_68")
        val image68: String,

        @SerializedName("image_88")
        val image88: String,

        @SerializedName("image_102")
        val image102: String,

        @SerializedName("image_132")
        val image132: String,

        @SerializedName("image_230")
        val image230: String?,

        @SerializedName("image_original")
        val imageOriginal: String?

) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image34)
        parcel.writeString(image44)
        parcel.writeString(image68)
        parcel.writeString(image88)
        parcel.writeString(image102)
        parcel.writeString(image132)
        parcel.writeString(image230)
        parcel.writeString(imageOriginal)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<TeamIcon> {
        override fun createFromParcel(parcel: Parcel): TeamIcon {
            return TeamIcon(parcel)
        }

        override fun newArray(size: Int): Array<TeamIcon?> {
            return arrayOfNulls(size)
        }
    }

}