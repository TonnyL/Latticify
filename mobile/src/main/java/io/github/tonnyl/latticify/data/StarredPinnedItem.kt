package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 * {
 * "type": "message",
 * "channel": "C2147483705",
 * "message": {...}
 * },
 * {
 * "type": "file",
 * "file": { ... }
 * }
 * {
 * "type": "file_comment",
 * "file": { ... },
 * "comment": { ... }
 * }
 * {
 * "type": "channel",
 * "channel": "C2147483705"
 * },
 */
data class StarredPinnedItem(

        @SerializedName("type")
        val type: String,

        @SerializedName("channel")
        val channel: String?,

        @SerializedName("message")
        val message: Message?,

        @SerializedName("file")
        val file: File?,

        @SerializedName("comment")
        val comment: FileComment?,

        @SerializedName("date_create")
        val dateCreate: String

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Message::class.java.classLoader),
            parcel.readParcelable(File::class.java.classLoader),
            parcel.readParcelable(FileComment::class.java.classLoader),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(channel)
        parcel.writeParcelable(message, flags)
        parcel.writeParcelable(file, flags)
        parcel.writeParcelable(comment, flags)
        parcel.writeString(dateCreate)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<StarredPinnedItem> {
        override fun createFromParcel(parcel: Parcel): StarredPinnedItem {
            return StarredPinnedItem(parcel)
        }

        override fun newArray(size: Int): Array<StarredPinnedItem?> {
            return arrayOfNulls(size)
        }
    }

}