package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 10/10/2017.
 */
data class AuthTestWrapper(

        @SerializedName("ok")
        val ok: String,

        @SerializedName("url")
        val url: String,

        @SerializedName("team")
        val team: String,

        @SerializedName("user")
        val user: String,

        @SerializedName("team_id")
        val teamId: String,

        @SerializedName("user_id")
        val userId: String,

        @SerializedName("enterprise_id")
        val enterpriseId: String?

)