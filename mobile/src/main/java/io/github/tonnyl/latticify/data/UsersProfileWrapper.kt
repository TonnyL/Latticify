package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 * {
 * "ok": true,
 * "profile": {
 * "status_text": "riding a train",
 * "status_emoji": ":mountain_railway:",
 * "first_name": "John",
 * "last_name": "Smith",
 * "email": "john@smith.com",
 * "skype": "johnsmith",
 * "image_24": "https://s3.amazonaws.com/slack-files/avatars/2015-11-16/123456_24.jpg",
 * "image_32": "https://s3.amazonaws.com/slack-files/avatars/2015-11-16/123456_32.jpg",
 * "image_48": "https://s3.amazonaws.com/slack-files/avatars/2015-11-16/123456_48.jpg",
 * "image_72": "https://s3.amazonaws.com/slack-files/avatars/2015-11-16/123456_72.jpg",
 * "image_192": "https://s3.amazonaws.com/slack-files/avatars/2015-11-16/123456_192.jpg",
 * "image_512": "https://s3.amazonaws.com/slack-files/avatars/2015-11-16/123456_512.jpg",
 * "image_1024": "https://s3.amazonaws.com/slack-files/avatars/2015-11-16/123456_1024.jpg",
 * "image_original": "https://s3.amazonaws.com/slack-files/avatars/2015-11-16/123456_original.jpg",
 * "fields": {
 * "Xf06054AAA": {
 * "value": "San Francisco",
 * "alt": "Giants, yo!",
 * "label": "Favorite Baseball Team"
 * },
 * "Xf06054BBB": {
 * "value": "Barista",
 * "alt": "I make the coffee & the tea!",
 * "label": "Position"
 * }
 * }
 * }
 * }
 */
data class UsersProfileWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("profile")
        val userProfile: UsersProfile,

        @SerializedName("username")
        val username: String?

)