package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 13/10/2017.
 */
data class SearchMessageMatchPreNext(

        @SerializedName("type")
        val type: String,

        @SerializedName("user")
        val user: String,

        @SerializedName("username")
        val username: String,

        @SerializedName("ts")
        val ts: String,

        @SerializedName("attachments")
        val attachments: List<Attachment>,

        @SerializedName("text")
        val text: String,

        @SerializedName("iid")
        val iid: String

)