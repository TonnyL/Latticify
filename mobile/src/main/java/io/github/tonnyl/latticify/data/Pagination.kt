package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 13/10/2017.
 */
data class Pagination(

        @SerializedName("total_count")
        val totalCount: Int,

        @SerializedName("page")
        val page: Int,

        @SerializedName("per_page")
        val perPage: Int,

        @SerializedName("page_count")
        val pageCount: Int,

        @SerializedName("first")
        val first: Int,

        @SerializedName("last")
        val last: Int

)