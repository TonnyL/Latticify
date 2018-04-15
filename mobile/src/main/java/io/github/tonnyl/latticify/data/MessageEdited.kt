package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * "edited": {
 * "user": "UA5C8NZGT",
 * "ts": "1523604089.000000"
 * }
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class MessageEdited(

        @SerializedName("user")
        val user: String,

        @SerializedName("ts")
        val ts: String

) : Parcelable