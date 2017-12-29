package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
@Parcelize
@SuppressLint("ParcelCreator")
data class RtmResponseMessageWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("url")
        val url: String,

        @SerializedName("team")
        val team: Team,

        @SerializedName("self")
        val rtmSelf: RtmSelf

) : Parcelable