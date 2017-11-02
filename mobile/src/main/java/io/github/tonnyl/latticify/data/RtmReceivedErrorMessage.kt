package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 02/11/2017.
 *
 * "error": {
 * "code": 2,
 * "msg": "message text is missing"
 * }
 */
data class RtmReceivedErrorMessage(

        @SerializedName("code")
        val code: Short,

        @SerializedName("msg")
        val msg: String

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt().toShort(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(code.toInt())
        parcel.writeString(msg)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<RtmReceivedErrorMessage> {
        override fun createFromParcel(parcel: Parcel): RtmReceivedErrorMessage {
            return RtmReceivedErrorMessage(parcel)
        }

        override fun newArray(size: Int): Array<RtmReceivedErrorMessage?> {
            return arrayOfNulls(size)
        }
    }

}