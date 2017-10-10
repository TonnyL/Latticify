package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 24/09/2017.
 *
 * Cursors typically end with the = character. When presenting this value as a URL or POST parameter,
 * it must be encoded as %3D. There are no further results to retrieve when presented a next_cursor field
 * that is contains an empty string ("").
 */
data class ResponseMetaData(

        @SerializedName("next_cursor")
        val nextCursor: String

)