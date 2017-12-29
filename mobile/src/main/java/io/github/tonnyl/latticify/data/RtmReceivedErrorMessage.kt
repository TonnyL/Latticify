package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 02/11/2017.
 *
 * "error": {
 * "code": 2,
 * "msg": "message text is missing"
 * }
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class RtmReceivedErrorMessage(

        @SerializedName("code")
        val code: Short,

        @SerializedName("msg")
        val msg: String

) : Parcelable