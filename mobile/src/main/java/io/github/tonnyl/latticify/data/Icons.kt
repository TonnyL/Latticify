package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 24/09/2017.
 */
data class Icons(

        @SerializedName("image_36")
        val image36: String?,

        @SerializedName("image_48")
        val image48: String?,

        @SerializedName("image_72")
        val image72: String?

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image36)
        parcel.writeString(image48)
        parcel.writeString(image72)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Icons> {
        override fun createFromParcel(parcel: Parcel): Icons {
            return Icons(parcel)
        }

        override fun newArray(size: Int): Array<Icons?> {
            return arrayOfNulls(size)
        }
    }


}