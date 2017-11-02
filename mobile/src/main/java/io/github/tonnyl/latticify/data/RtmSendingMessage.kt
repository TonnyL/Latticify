package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 02/11/2017.
 */
data class RtmSendingMessage(

        @SerializedName("id")
        val id: String,

        @SerializedName("type")
        val type: String,

        @SerializedName("channel")
        val channel: String,

        @SerializedName("text")
        val text: String

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeString(channel)
        parcel.writeString(text)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<RtmSendingMessage> {
        override fun createFromParcel(parcel: Parcel): RtmSendingMessage {
            return RtmSendingMessage(parcel)
        }

        override fun newArray(size: Int): Array<RtmSendingMessage?> {
            return arrayOfNulls(size)
        }
    }

}