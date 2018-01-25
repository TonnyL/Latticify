package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 25/01/2018.
 *
 * {
 * "ok": true,
 * "users": {
 * "U023BECGF": {
 * "dnd_enabled": true,
 * "next_dnd_start_ts": 1450387800,
 * "next_dnd_end_ts": 1450423800
 * },
 * "U058CJVAA": {
 * "dnd_enabled": false,
 * "next_dnd_start_ts": 1,
 * "next_dnd_end_ts": 1
 * }
 * }
 * }
 */
data class DndTeamInfoWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("users")
        val users: Map<String, DndTeamInfo>

)