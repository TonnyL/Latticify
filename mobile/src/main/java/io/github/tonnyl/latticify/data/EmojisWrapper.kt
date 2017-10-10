package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 09/10/2017.
 *
 * {
 * "ok": true,
 * "emoji": {
 * "bowtie": "https:\/\/my.slack.com\/emoji\/bowtie\/46ec6f2bb0.png",
 * "squirrel": "https:\/\/my.slack.com\/emoji\/squirrel\/f35f40c0e0.png",
 * "shipit": "alias:squirrel",
 * …
 * }
 * }
 */
data class EmojisWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("emoji")
        val emoji: Map<String, String>

)