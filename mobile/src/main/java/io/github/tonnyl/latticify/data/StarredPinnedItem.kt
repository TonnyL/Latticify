package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 * {
 * "type": "message",
 * "channel": "C2147483705",
 * "message": {...}
 * },
 * {
 * "type": "file",
 * "file": { ... }
 * }
 * {
 * "type": "file_comment",
 * "file": { ... },
 * "comment": { ... }
 * }
 * {
 * "type": "channel",
 * "channel": "C2147483705"
 * },
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class StarredPinnedItem(

        @SerializedName("type")
        val type: String,

        @SerializedName("channel")
        val channel: String?,

        @SerializedName("message")
        val message: Message?,

        @SerializedName("file")
        val file: File?,

        @SerializedName("comment")
        val comment: FileComment?,

        @SerializedName("created")
        val created: Long,

        @SerializedName("created_by")
        val createdBy: String?

) : Parcelable