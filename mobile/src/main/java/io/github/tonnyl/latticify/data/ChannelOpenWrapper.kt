package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 09/10/2017.
 *
 * {
 * "ok": true,
 * "no_op": true,
 * "already_open": true
 * }
 */
data class ChannelOpenWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("no_op")
        val noOp: Boolean?,

        @SerializedName("already_open")
        val alreadyOpen: Boolean?

)