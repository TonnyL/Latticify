package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 13/10/2017.
 */
data class SearchedMessageMatch(

        @SerializedName("previous")
        val previous: SearchedMessageMatchPreNext?,

        @SerializedName("previous_2")
        val previous2: SearchedMessageMatchPreNext?,

        @SerializedName("next")
        val next: SearchedMessageMatchPreNext?,

        @SerializedName("next_2")
        val next2: SearchedMessageMatchPreNext?,

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
        val iid: String,

        @SerializedName("team")
        val team: String?,

        @SerializedName("channel")
        val channel: SearchedMessageMatchChannel,

        @SerializedName("permalink")
        val permalink: String

)