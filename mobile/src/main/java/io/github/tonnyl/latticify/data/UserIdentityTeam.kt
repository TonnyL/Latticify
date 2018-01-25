package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 25/01/2018.
 *
 * "team": {
 * "id": "T4WLRK1UL",
 * "name": "AndroidDeveloper",
 * "domain": "androiddevsslack",
 * "image_xxx": "",
 * "image_xxx": "",
 * "image_original": "https:\/\/slack-files2.s3-us-west-2.amazonaws.com\/avatars\/2017-04-13\/168134099793_a857415bd900bb7c6b23_original.png"
 * }
 */
data class UserIdentityTeam(

        @SerializedName("id")
        val id: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("domain")
        val domain: String,

        @SerializedName("image_original")
        val imageOriginal: String

)