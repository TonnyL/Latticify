package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 25/01/2018.
 *
 * {
 * "ok": true,
 * "user": {
 * "name": "Sonny Whether",
 * "id": "U0G9QF9C6",
 * "email": "bobby@example.com",
 * "image_24": "https:\/\/cdn.example.com\/sonny_24.jpg",
 * "image_32": "https:\/\/cdn.example.com\/sonny_32.jpg",
 * "image_48": "https:\/\/cdn.example.com\/sonny_48.jpg",
 * "image_72": "https:\/\/cdn.example.com\/sonny_72.jpg",
 * "image_192": "https:\/\/cdn.example.com\/sonny_192.jpg"
 * },
 * "team": {
 * "id": "T4WLRK1UL",
 * "name": "AndroidDeveloper",
 * "domain": "androiddevsslack",
 * "image_xxx": "",
 * "image_xxx": "",
 * "image_original": "https:\/\/slack-files2.s3-us-west-2.amazonaws.com\/avatars\/2017-04-13\/168134099793_a857415bd900bb7c6b23_original.png"
 * }
 * }
 */
data class UserIdentityWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("user")
        val user: UserIdentityUser,

        @SerializedName("team")
        val team: UserIdentityTeam

)