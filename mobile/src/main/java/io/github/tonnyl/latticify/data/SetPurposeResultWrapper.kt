package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 * {
 * "ok": true,
 * "purpose": "This is the new purpose!"
 * }
 */
data class SetPurposeResultWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("purpose")
        val purpose: String

)