package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 23/09/2017.
 *
 * "topic":
 * {
 * "value":"",
 * "creator":"U4XAJCHM5",
 * "last_set":1491730312
 * },
 * "purpose":
 * {
 * "value":"To receive gmails",
 * "creator":"U4XAJCHM5",
 * "last_set":1491730312
 * }
 */
data class TopicPurpose(

        @SerializedName("value")
        val value: String,

        @SerializedName("creator")
        val creator: String,

        @SerializedName("last_set")
        val lastSet: Long

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readLong())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(value)
        parcel.writeString(creator)
        parcel.writeLong(lastSet)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<TopicPurpose> {
        override fun createFromParcel(parcel: Parcel): TopicPurpose {
            return TopicPurpose(parcel)
        }

        override fun newArray(size: Int): Array<TopicPurpose?> {
            return arrayOfNulls(size)
        }
    }

}