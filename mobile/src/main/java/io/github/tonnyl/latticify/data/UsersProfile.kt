package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 08/10/2017.
 */
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

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(avatarHash)
        parcel.writeString(image24)
        parcel.writeString(image32)
        parcel.writeString(image48)
        parcel.writeString(image72)
        parcel.writeString(image192)
        parcel.writeString(image512)
        parcel.writeString(image1024)
        parcel.writeString(imageOriginal)
        parcel.writeString(statusText)
        parcel.writeString(statusEmoji)
        parcel.writeString(title)
        parcel.writeString(realName)
        parcel.writeString(displayName)
        parcel.writeString(realNameNormalized)
        parcel.writeString(displayNameNormalized)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(skype)
        parcel.writeString(team)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<UsersProfile> {
        override fun createFromParcel(parcel: Parcel): UsersProfile {
            return UsersProfile(parcel)
        }

        override fun newArray(size: Int): Array<UsersProfile?> {
            return arrayOfNulls(size)
        }
    }


}