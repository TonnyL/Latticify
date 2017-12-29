package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 * "icon": {
 * "image_34": "https:\/\/...",
 * "image_44": "https:\/\/...",
 * "image_68": "https:\/\/...",
 * "image_88": "https:\/\/...",
 * "image_102": "https:\/\/...",
 * "image_132": "https:\/\/...",
 * "image_default": true
 * }
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class TeamIcon(

        @SerializedName("image_34")
        val image34: String,

        @SerializedName("image_44")
        val image44: String,

        @SerializedName("image_68")
        val image68: String,

        @SerializedName("image_88")
        val image88: String,

        @SerializedName("image_102")
        val image102: String,

        @SerializedName("image_132")
        val image132: String,

        @SerializedName("image_230")
        val image230: String?,

        @SerializedName("image_original")
        val imageOriginal: String?

) : Parcelable