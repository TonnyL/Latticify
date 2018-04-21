package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.arch.persistence.room.ColumnInfo
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 08/10/2017.
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class UsersProfile(

        @ColumnInfo(name = "first_name")
        @SerializedName("first_name")
        val firstName: String?,

        @ColumnInfo(name = "last_name")
        @SerializedName("last_name")
        val lastName: String?,

        @ColumnInfo(name = "avatar_hash")
        @SerializedName("avatar_hash")
        val avatarHash: String,

        @ColumnInfo(name = "image_24")
        @SerializedName("image_24")
        val image24: String,

        @ColumnInfo(name = "image_32")
        @SerializedName("image_32")
        val image32: String,

        @ColumnInfo(name = "image_48")
        @SerializedName("image_48")
        val image48: String,

        @ColumnInfo(name = "image_72")
        @SerializedName("image_72")
        val image72: String,

        @ColumnInfo(name = "image_192")
        @SerializedName("image_192")
        val image192: String,

        @ColumnInfo(name = "image_512")
        @SerializedName("image_512")
        val image512: String?,

        @ColumnInfo(name = "image_1024")
        @SerializedName("image_1024")
        val image1024: String?,

        @ColumnInfo(name = "image_original")
        @SerializedName("image_original")
        val imageOriginal: String?,

        @ColumnInfo(name = "status_text")
        @SerializedName("status_text")
        val statusText: String?,

        @ColumnInfo(name = "status_emoji")
        @SerializedName("status_emoji")
        val statusEmoji: String?,

        @ColumnInfo(name = "title")
        @SerializedName("title")
        val title: String?,

        @ColumnInfo(name = "profile_real_name")
        @SerializedName("real_name")
        val realName: String,

        @ColumnInfo(name = "display_name")
        @SerializedName("display_name")
        val displayName: String,

        @ColumnInfo(name = "real_name_normalized")
        @SerializedName("real_name_normalized")
        val realNameNormalized: String,

        @ColumnInfo(name = "display_name_normalized")
        @SerializedName("display_name_normalized")
        val displayNameNormalized: String,

        @ColumnInfo(name = "email")
        @SerializedName("email")
        val email: String?,

        @ColumnInfo(name = "phone")
        @SerializedName("phone")
        val phone: String?,

        @ColumnInfo(name = "skype")
        @SerializedName("skype")
        val skype: String?,

        @ColumnInfo(name = "team")
        @SerializedName("team")
        val team: String?

) : Parcelable