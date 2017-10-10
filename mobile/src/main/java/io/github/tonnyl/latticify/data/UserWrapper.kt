package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 23/09/2017.
 *
 * A user object contains information about a member.
 *
 * {
 * "ok": true,
 * "user": {
 *
 * }
 */
data class UserWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("user")
        val user: User
)