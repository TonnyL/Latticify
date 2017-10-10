package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 10/10/2017.
 *
 * "reminder": {
 * "id": "Rm12345678",
 * "creator": "U18888888",
 * "user": "U18888888",
 * "text": "eat a banana",
 * "recurring": false,
 * "time": 1602288000,
 * "complete_ts": 0
 * }
 */
data class Reminder(

        @SerializedName("id")
        val id: String,

        @SerializedName("creator")
        val creator: String,

        @SerializedName("user")
        val user: String,

        @SerializedName("text")
        val text: String,

        @SerializedName("recurring")
        val recurring: Boolean,

        /**
         * Only non-recurring reminders will have time field.
         */
        @SerializedName("time")
        val time: Long?,

        /**
         * Only non-recurring reminders will have complete_ts field.
         */
        @SerializedName("complete_ts")
        val completeTs: Long?

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readValue(Long::class.java.classLoader) as? Long)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(creator)
        parcel.writeString(user)
        parcel.writeString(text)
        parcel.writeByte(if (recurring) 1.toByte() else 0.toByte())
        parcel.writeValue(time)
        parcel.writeValue(completeTs)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Reminder> {
        override fun createFromParcel(parcel: Parcel): Reminder {
            return Reminder(parcel)
        }

        override fun newArray(size: Int): Array<Reminder?> {
            return arrayOfNulls(size)
        }
    }


}