package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 09/10/2017.
 *
 * "self": {
 * "id": "W123456",
 * "name": "brautigan"
 * }
 */
data class RtmSelf(

        @SerializedName("id")
        val id: String,

        @SerializedName("name")
        val name: String

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<RtmSelf> {
        override fun createFromParcel(parcel: Parcel): RtmSelf {
            return RtmSelf(parcel)
        }

        override fun newArray(size: Int): Array<RtmSelf?> {
            return arrayOfNulls(size)
        }
    }

}