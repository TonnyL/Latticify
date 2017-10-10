package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 * "paging": {
 * "per_page": 100,
 * "spill": 0,
 * "page": 1,
 * "total": 0,
 * "pages": 0
 * }
 */
data class Paging(

        @SerializedName("per_page")
        val perPage: Int,

        @SerializedName("spill")
        val spill: Int,

        @SerializedName("page")
        val page: Int,

        @SerializedName("pages")
        val pages: Int,

        @SerializedName("total")
        val total: Int

)