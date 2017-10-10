package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 09/10/2017.
 */
data class ThreadInfo(

        @SerializedName("complete")
        val complete: Boolean,

        @SerializedName("count")
        val count: Int

)