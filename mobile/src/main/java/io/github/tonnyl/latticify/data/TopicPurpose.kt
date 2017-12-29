package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
@Parcelize
@SuppressLint("ParcelCreator")
data class TopicPurpose(

        @SerializedName("value")
        val value: String,

        @SerializedName("creator")
        val creator: String,

        @SerializedName("last_set")
        val lastSet: Long

) : Parcelable