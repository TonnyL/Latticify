package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 10/10/2017.
 */
data class AuthRevokeWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("revoked")
        val revoked: Boolean

)