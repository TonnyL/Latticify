package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 24/09/2017.
 *
 * "attachments":
 * [
 * {
 * "title":"Subject:New Slack API token issued",
 * "thumb_url":"https:\/\/ifttt.com\/images\/file_not_found.png",
 * "thumb_width":800,
 * "thumb_height":494,
 * "text":"From: ...",
 * "fallback":"From: ...",
 * "mrkdwn_in":
 * [
 * "text",
 * "pretext"
 * ]
 * }
 * ]
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class Attachment(

        @SerializedName("title")
        val title: String,

        @SerializedName("title_link")
        val titleLink: String?,

        @SerializedName("thumb_url")
        val thumbUrl: String,

        @SerializedName("thumb_width")
        val thumbWidth: String,

        @SerializedName("thumb_height")
        val thumbHeight: String,

        @SerializedName("text")
        val text: String,

        @SerializedName("fallback")
        val fallback: String?,

        @SerializedName("mrkdwn_in")
        val markdownIn: List<String>?

) : Parcelable