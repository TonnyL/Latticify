package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 * {
 * "user_id": "U45678",
 * "username": "alice",
 * "date_first": 1422922493,
 * "date_last": 1422922493,
 * "count": 1,
 * "ip": "127.0.0.1",
 * "user_agent": "SlackWeb Mozilla\/5.0 (iPhone; CPU iPhone OS 8_1_3 like Mac OS X) AppleWebKit\/600.1.4 (KHTML, like Gecko) Version\/8.0 Mobile\/12B466 Safari\/600.1.4",
 * "isp": "BigCo ISP",
 * "country": "US",
 * "region": "CA"
 * }
 */
data class TeamLog(

        @SerializedName("user_id")
        val userId: String,

        @SerializedName("username")
        val username: String,

        @SerializedName("date_first")
        val dateFirst: String,

        @SerializedName("date_last")
        val dateLast: String,

        @SerializedName("count")
        val count: Int,

        @SerializedName("ip")
        val ip: String,

        @SerializedName("user_agent")
        val userAgent: String,

        @SerializedName("isp")
        val isp: String,

        @SerializedName("country")
        val country: String,

        @SerializedName("region")
        val region: String

)