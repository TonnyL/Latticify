package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 25/01/2018.
 *
 * "U058CJVAA": {
 * "dnd_enabled": false,
 * "next_dnd_start_ts": 1,
 * "next_dnd_end_ts": 1
 * }
 */
data class DndTeamInfo(

        @SerializedName("dnd_enabled")
        val dndEnabled: Boolean,

        @SerializedName("next_dnd_start_ts")
        val nextDndStartTs: Long,

        @SerializedName("next_dnd_end_ts")
        val nextDndEndTs: Long

)