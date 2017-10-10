package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 * {
 * "ok": true,
 * "topic": "This is the new topic!"
 * }
 */
data class SetTopicWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("topic")
        val topic: String

)