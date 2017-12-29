package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 02/11/2017.
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class RtmSendingMessage(

        @SerializedName("id")
        val id: String,

        @SerializedName("type")
        val type: String,

        @SerializedName("channel")
        val channel: String,

        @SerializedName("text")
        val text: String

) : Parcelable