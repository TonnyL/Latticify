package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 09/10/2017.
 */
data class ImRepliesWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("messages")
        val messages: List<Message>,

        @SerializedName("thread_info")
        val threadInfo: ThreadInfo

)