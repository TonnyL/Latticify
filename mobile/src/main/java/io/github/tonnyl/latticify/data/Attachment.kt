package io.github.tonnyl.latticify.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

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
data class Attachment(

        @SerializedName("title")
        val title: String,

        @SerializedName("thumb_url")
        val thumbUrl: String,

        @SerializedName("thumb_width")
        val thumbWidth: String,

        @SerializedName("thumb_height")
        val thumbHeight: String,

        @SerializedName("text")
        val text: String,

        @SerializedName("fallback")
        val fallback: String,

        @SerializedName("mrkdwn_in")
        val markdownIn: List<String>

) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createStringArrayList())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(thumbUrl)
        parcel.writeString(thumbWidth)
        parcel.writeString(thumbHeight)
        parcel.writeString(text)
        parcel.writeString(fallback)
        parcel.writeStringList(markdownIn)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Attachment> {
        override fun createFromParcel(parcel: Parcel): Attachment {
            return Attachment(parcel)
        }

        override fun newArray(size: Int): Array<Attachment?> {
            return arrayOfNulls(size)
        }
    }

}