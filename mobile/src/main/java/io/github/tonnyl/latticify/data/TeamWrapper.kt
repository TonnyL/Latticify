package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 * {
 * "ok": true,
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
 * }
 */
data class TeamWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("team")
        val team: Team

)