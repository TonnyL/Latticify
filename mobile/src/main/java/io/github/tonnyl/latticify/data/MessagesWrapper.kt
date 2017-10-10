package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 * {
 * "ok": true,
 * "latest": "1358547726.000003",
 * "messages": [
 * {
 * "type": "message",
 * "ts": "1358546515.000008",
 * "user": "U2147483896",
 * "text": "Hello"
 * },
 * {
 * "type": "message",
 * "ts": "1358546515.000007",
 * "user": "U2147483896",
 * "text": "World",
 * "is_starred": true,
 * "reactions": [
 * {
 * "name": "space_invader",
 * "count": 3,
 * "users": [
 * "U1",
 * "U2",
 * "U3"
 * ]
 * },
 * {
 * "name": "sweet_potato",
 * "count": 5,
 * "users": [
 * "U1",
 * "U2",
 * "U3",
 * "U4",
 * "U5"
 * ]
 * }
 * ]
 * },
 * {
 * "type": "something_else",
 * "ts": "1358546515.000007",
 * "wibblr": true
 * }
 * ],
 * "has_more": false
 * }
 */
data class MessagesWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("latest")
        val latest: String?,

        @SerializedName("messages")
        val messages: ArrayList<Message>?,

        @SerializedName("has_more")
        val hasMore: Boolean?

)