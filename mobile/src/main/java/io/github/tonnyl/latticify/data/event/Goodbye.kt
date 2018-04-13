package io.github.tonnyl.latticify.data.event

import com.google.gson.annotations.SerializedName

/**
 * The server intends to close the connection soon.
 *
 * {
 * "type": "goodbye"
 * }
 *
 * The [Goodbye] event may be sent by a server that expects it will close the connection after an unspecified amount of time.
 * A well formed client should reconnect to avoid data loss.
 */
data class Goodbye(

        @SerializedName("type")
        val type: String

)