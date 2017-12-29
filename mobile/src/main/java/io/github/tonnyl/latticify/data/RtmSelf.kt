package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 09/10/2017.
 *
 * "self": {
 * "id": "W123456",
 * "name": "brautigan"
 * }
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class RtmSelf(

        @SerializedName("id")
        val id: String,

        @SerializedName("name")
        val name: String

) : Parcelable