package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 24/09/2017.
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class Icons(

        @SerializedName("image_36")
        val image36: String?,

        @SerializedName("image_48")
        val image48: String?,

        @SerializedName("image_72")
        val image72: String?

) : Parcelable