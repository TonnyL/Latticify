package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 25/01/2018.
 *
 * "user": {
 * "name": "Sonny Whether",
 * "id": "U0G9QF9C6",
 * "email": "bobby@example.com",
 * "image_24": "https:\/\/cdn.example.com\/sonny_24.jpg",
 * "image_32": "https:\/\/cdn.example.com\/sonny_32.jpg",
 * "image_48": "https:\/\/cdn.example.com\/sonny_48.jpg",
 * "image_72": "https:\/\/cdn.example.com\/sonny_72.jpg",
 * "image_192": "https:\/\/cdn.example.com\/sonny_192.jpg"
 * }
 */
data class UserIdentityUser(

        @SerializedName("name")
        val name: String,

        @SerializedName("id")
        val id: String,

        @SerializedName("email")
        val email: String,

        @SerializedName("image_24")
        val image24: String,

        @SerializedName("image_32")
        val image32: String,

        @SerializedName("image_48")
        val image48: String,

        @SerializedName("image72")
        val image72: String,

        @SerializedName("image192")
        val image192: String,

        @SerializedName("image_512")
        val image512: String?,

        @SerializedName("image_1024")
        val image1024: String?

)