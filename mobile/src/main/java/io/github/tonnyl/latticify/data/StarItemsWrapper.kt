package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 08/10/2017.
 */
data class StarItemsWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("items")
        val items: List<StarredPinnedItem>,

        @SerializedName("paging")
        val paging: Paging

)