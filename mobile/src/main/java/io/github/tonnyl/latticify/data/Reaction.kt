package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 23/09/2017.
 *
 * "reactions": [
 * {
 * "name": "space_invader",
 * "count": 3,
 * "users": [
 * "U1",
 * "U2",
 * "U3"
 * ]
 * }
 * ]
 */
data class Reaction(

        @SerializedName("name")
        val name: String,

        @SerializedName("count")
        val count: Int,

        @SerializedName("users")
        val users: List<String>

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.createStringArrayList())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(count)
        parcel.writeStringList(users)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Reaction> {
        override fun createFromParcel(parcel: Parcel): Reaction {
            return Reaction(parcel)
        }

        override fun newArray(size: Int): Array<Reaction?> {
            return arrayOfNulls(size)
        }
    }

}