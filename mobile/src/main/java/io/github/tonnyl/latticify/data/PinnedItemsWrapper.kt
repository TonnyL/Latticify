package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 10/10/2017.
 *
 * {
 * "ok": true,
 * "items": [
 * {
 * "type": "message",
 * "channel": "C2147483705",
 * "message": {...},
 * "created": 1456335673,
 * "created_by": "U07BVMD97"
 * },
 * {
 * "type": "file",
 * "file": { ... },
 * "created": 1456335673,
 * "created_by": "U07BVMD97"
 * },
 * {
 * "type": "file_comment",
 * "file": { ... },
 * "comment": { ... },
 * "created": 1456335673,
 * "created_by": "U07BVMD97"
 * }
 * ]
 * }
 */
data class PinnedItemsWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("items")
        val pinnedItems: List<StarredPinnedItem>

)