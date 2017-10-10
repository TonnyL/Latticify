package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 10/10/2017.
 *
 * {
 * "ok": true,
 * "reminders": [
 * {
 * "id": "Rm12345678",
 * "creator": "U18888888",
 * "user": "U18888888",
 * "text": "eat a banana",
 * "recurring": false,
 * "time": 1458678068,
 * "complete_ts": 0
 * },
 * {
 * "id": "Rm23456789",
 * "creator": "U18888888",
 * "user": "U18888888",
 * "text": "drink water",
 * "recurring": true
 * }
 * ]
 * }
 */
data class RemindersWrapper(

        @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("reminders")
        val reminders: List<Reminder>

)