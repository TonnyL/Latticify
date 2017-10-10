package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 09/10/2017.
 *
 * {
 * "ok": true,
 * "url": "wss:\/\/ms9.slack-msgs.com\/websocket\/2I5yBpcvk",
 * "team": {
 * "id": "T654321",
 * "name": "Librarian Society of Soledad",
 * "domain": "libsocos",
 * "enterprise_id": "E234567",
 * "enterprise_name": "Intercontinental Librarian Society"
 * },
 * "self": {
 * "id": "W123456",
 * "name": "brautigan"
 * }
 * }
 */
data class RtmResponseMessageWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("url")
        val url: String,

        @SerializedName("team")
        val team: Team,

        @SerializedName("self")
        val rtmSelf: RtmSelf

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readParcelable(Team::class.java.classLoader),
            parcel.readParcelable(RtmSelf::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (ok) 1.toByte() else 0.toByte())
        parcel.writeString(url)
        parcel.writeParcelable(team, flags)
        parcel.writeParcelable(rtmSelf, flags)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<RtmResponseMessageWrapper> {
        override fun createFromParcel(parcel: Parcel): RtmResponseMessageWrapper {
            return RtmResponseMessageWrapper(parcel)
        }

        override fun newArray(size: Int): Array<RtmResponseMessageWrapper?> {
            return arrayOfNulls(size)
        }
    }

}