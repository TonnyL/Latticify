package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
@Parcelize
@SuppressLint("ParcelCreator")
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

) : Parcelable