package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 24/09/2017.
 */
data class ChannelsWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("channels")
        val channels: ArrayList<Channel>,

        @SerializedName("response_metadata")
        val responseMetaData: ResponseMetaData?

)