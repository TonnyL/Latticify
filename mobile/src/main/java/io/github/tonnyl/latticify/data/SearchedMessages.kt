package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 13/10/2017.
 */
data class SearchedMessages(

        @SerializedName("total")
        val total: Int,

        @SerializedName("pagination")
        val pagination: Pagination,

        @SerializedName("paging")
        val paging: Paging,

        @SerializedName("matches")
        val matches: List<SearchedMessageMatch>

)