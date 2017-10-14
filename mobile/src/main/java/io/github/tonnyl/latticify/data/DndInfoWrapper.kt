package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 14/10/2017.
 */
data class DndInfoWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("dnd_enabled")
        val dndEnabled: Boolean,

        @SerializedName("next_dnd_start_ts")
        val nextDndStartTs: Long?,

        @SerializedName("next_dnd_end_ts")
        val nextDndEndTs: Long?,

        @SerializedName("snooze_enabled")
        val snoozeEnabled: Boolean?,

        @SerializedName("snooze_endtime")
        val snoozeEndtime: Long,

        @SerializedName("snooze_remaining")
        val snoozeRemaining: Long


)