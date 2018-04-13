package io.github.tonnyl.latticify.data.event

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * The client has successfully connected to the server.
 *
 * The [Hello] event is sent when a connection is opened to the message server.
 *
 * {
 * "type": "hello"
 * }
 *
 * This allows a client to confirm the connection has been correctly opened.
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class Hello(

        @SerializedName("type")
        val type: String

) : Parcelable