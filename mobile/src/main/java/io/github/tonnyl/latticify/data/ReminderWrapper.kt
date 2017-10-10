package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 10/10/2017.
 *
 * {
 * "ok": true,
 * "reminder": {
 * "id": "Rm12345678",
 * "creator": "U18888888",
 * "user": "U18888888",
 * "text": "eat a banana",
 * "recurring": false,
 * "time": 1602288000,
 * "complete_ts": 0
 * }
 * }
 */
data class ReminderWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("reminder")
        val reminder: Reminder

)