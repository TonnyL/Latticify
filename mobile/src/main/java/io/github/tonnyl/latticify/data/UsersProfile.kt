package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 08/10/2017.
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class UsersProfile(

        @SerializedName("first_name")
        val firstName: String,

        @SerializedName("last_name")
        val lastName: String,

        @SerializedName("avatar_hash")
        val avatarHash: String,

        @SerializedName("image_24")
        val image24: String,

        @SerializedName("image_32")
        val image32: String,

        @SerializedName("image_48")
        val image48: String,

        @SerializedName("image_72")
        val image72: String,

        @SerializedName("image_192")
        val image192: String,

        @SerializedName("image_512")
        val image512: String?,

        @SerializedName("image_1024")
        val image1024: String?,

        @SerializedName("image_original")
        val imageOriginal: String?,

        @SerializedName("status_text")
        val statusText: String?,

        @SerializedName("status_emoji")
        val statusEmoji: String?,

        @SerializedName("title")
        val title: String?,

        @SerializedName("real_name")
        val realName: String,

        @SerializedName("display_name")
        val displayName: String,

        @SerializedName("real_name_normalized")
        val realNameNormalized: String,

        @SerializedName("display_name_normalized")
        val displayNameNormalized: String,

        @SerializedName("email")
        val email: String?,

        @SerializedName("phone")
        val phone: String?,

        @SerializedName("skype")
        val skype: String?,

        @SerializedName("team")
        val team: String?

) : Parcelable