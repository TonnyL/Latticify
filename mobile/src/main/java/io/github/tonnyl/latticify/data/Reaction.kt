package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 23/09/2017.
 *
 * "reactions": [
 * {
 * "name": "space_invader",
 * "count": 3,
 * "users": [
 * "U1",
 * "U2",
 * "U3"
 * ]
 * }
 * ]
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class Reaction(

        @SerializedName("name")
        val name: String,

        @SerializedName("count")
        val count: Int,

        @SerializedName("users")
        val users: List<String>

) : Parcelable