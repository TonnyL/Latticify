package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 *
 * "team": {
 * "id": "T4WLRK1UL",
 * "name": "AndroidDeveloper",
 * "domain": "androiddevsslack",
 * "email_domain": "",
 * "icon": {
 * "image_34": "https:\/\/slack-files2.s3-us-west-2.amazonaws.com\/avatars\/2017-04-13\/168134099793_a857415bd900bb7c6b23_34.png",
 * "image_44": "https:\/\/slack-files2.s3-us-west-2.amazonaws.com\/avatars\/2017-04-13\/168134099793_a857415bd900bb7c6b23_44.png",
 * "image_68": "https:\/\/slack-files2.s3-us-west-2.amazonaws.com\/avatars\/2017-04-13\/168134099793_a857415bd900bb7c6b23_68.png",
 * "image_88": "https:\/\/slack-files2.s3-us-west-2.amazonaws.com\/avatars\/2017-04-13\/168134099793_a857415bd900bb7c6b23_88.png",
 * "image_102": "https:\/\/slack-files2.s3-us-west-2.amazonaws.com\/avatars\/2017-04-13\/168134099793_a857415bd900bb7c6b23_102.png",
 * "image_132": "https:\/\/slack-files2.s3-us-west-2.amazonaws.com\/avatars\/2017-04-13\/168134099793_a857415bd900bb7c6b23_132.png",
 * "image_230": "https:\/\/slack-files2.s3-us-west-2.amazonaws.com\/avatars\/2017-04-13\/168134099793_a857415bd900bb7c6b23_230.png",
 * "image_original": "https:\/\/slack-files2.s3-us-west-2.amazonaws.com\/avatars\/2017-04-13\/168134099793_a857415bd900bb7c6b23_original.png"
 * }
 * }
 */
data class Team(

        @SerializedName("id")
        val id: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("domain")
        val domain: String,

        @SerializedName("email_domain")
        val emailDomain: String,

        @SerializedName("icon")
        val icon: TeamIcon,

        @SerializedName("enterprise_id")
        val enterpriseId: String?,

        @SerializedName("enterprise_name")
        val enterpriseName: String?

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(TeamIcon::class.java.classLoader),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(domain)
        parcel.writeString(emailDomain)
        parcel.writeParcelable(icon, flags)
        parcel.writeString(enterpriseId)
        parcel.writeString(enterpriseName)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Team> {
        override fun createFromParcel(parcel: Parcel): Team {
            return Team(parcel)
        }

        override fun newArray(size: Int): Array<Team?> {
            return arrayOfNulls(size)
        }
    }

}