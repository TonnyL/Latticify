package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
@Parcelize
@SuppressLint("ParcelCreator")
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

) : Parcelable