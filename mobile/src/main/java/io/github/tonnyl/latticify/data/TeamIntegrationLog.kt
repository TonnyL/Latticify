package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 08/10/2017.
 *
 * {
 * "service_id": "1234567890",
 * "service_type": "Google Calendar",
 * "user_id": "U1234ABCD",
 * "user_name": "Johnny",
 * "channel": "C1234567890",
 * "date": "1392163200",
 * "change_type": "enabled",
 * "scope": "incoming-webhook"
 * }
 *
 * If the log entry is an RSS feed, the log will also contain [rssFeed] (with a value of true),
 * [rssFeedChangeType], [rssFeedTitle] and [rssFeedUrl].
 */
data class TeamIntegrationLog(

        @SerializedName("service_id")
        val serviceId: String,

        @SerializedName("service_type")
        val serviceType: String,

        @SerializedName("user_id")
        val userId: String,

        @SerializedName("user_name")
        val userName: String,

        @SerializedName("channel")
        val channel: String,

        @SerializedName("date")
        val date: String,

        /**
         * added, removed, enabled, disabled, updated
         */
        @SerializedName("change_type")
        val changeType: String,

        @SerializedName("scope")
        val scope: String,

        @SerializedName("rss_feed")
        val rssFeed: Boolean?,

        @SerializedName("rss_feed_change_type")
        val rssFeedChangeType: String?,

        @SerializedName("rss_feed_title")
        val rssFeedTitle: String?,

        @SerializedName("rss_feed_url")
        val rssFeedUrl: String?

)