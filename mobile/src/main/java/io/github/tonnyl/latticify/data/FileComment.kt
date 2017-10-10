package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 * "comment": {
 * "id": "Fc1234567890",
 * "created": 1356032811,
 * "timestamp": 1356032811,
 * "user": "U1234567890",
 * "comment": "Everyone should take a moment to read this file.",
 * "channel": "C1234467890"
 * }
 */
data class FileComment(

        @SerializedName("id")
        val id: String,

        @SerializedName("created")
        val created: String,

        @SerializedName("timestamp")
        val timestamp: String,

        @SerializedName("user")
        val user: String,

        @SerializedName("comment")
        val comment: String,

        @SerializedName("channel")
        val channel: String

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(created)
        parcel.writeString(timestamp)
        parcel.writeString(user)
        parcel.writeString(comment)
        parcel.writeString(channel)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<FileComment> {
        override fun createFromParcel(parcel: Parcel): FileComment {
            return FileComment(parcel)
        }

        override fun newArray(size: Int): Array<FileComment?> {
            return arrayOfNulls(size)
        }
    }

}