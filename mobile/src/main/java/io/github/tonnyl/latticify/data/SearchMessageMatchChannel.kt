package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 13/10/2017.
 */
data class SearchMessageMatchChannel(

        @SerializedName("id")
        val id: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("is_shared")
        val isShared: Boolean,

        @SerializedName("is_org_shared")
        val isOrgShared: Boolean,

        @SerializedName("is_ext_shared")
        val isExtShared: Boolean,

        @SerializedName("is_private")
        val isPrivate: Boolean,

        @SerializedName("is_mpim")
        val isMpim: Boolean,

        @SerializedName("pending_shared")
        val pendingShared: Boolean,

        @SerializedName("is_pending_ext_shared")
        val isPendingExtShared: Boolean

)