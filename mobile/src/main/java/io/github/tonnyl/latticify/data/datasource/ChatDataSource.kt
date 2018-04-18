package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.*
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 06/10/2017.
 */
interface ChatDataSource {

    fun delete(channelId: String,
               ts: String,
               asUser: Boolean = true): Observable<ChatMessageWrapper>

    fun getPermalink(channelId: String,
                     messageTs: String): Observable<MessagePermalinkWrapper>

    fun meMessage(channelId: String,
                  text: String): Observable<ChatMessageWrapper>

    fun postEphemeral(channelId: String,
                      text: String,
                      user: String,
                      asUser: Boolean = false,
                      attachments: String = "",
                      linkNames: Boolean = true,
                      parse: String = "none"): Observable<EphemeralChatMessageWrapper>

    fun postMessage(channelId: String,
                    text: String,
                    asUser: Boolean = true,
                    attachments: String = "",
                    iconEmoji: String = "",
                    iconUrl: String = "",
                    linkNames: Boolean = true,
                    parse: String = "none",
                    replyBroadcast: Boolean = false,
                    threadTs: String = "",
                    unfurlLinks: Boolean = true,
                    unfurlMedia: Boolean = false,
                    username: String = ""): Observable<PostMessageWrapper>

    fun unfurl(channelId: String,
               ts: String,
               unfurls: String,
               userAuthMessage: String = "",
               userAuthRequired: Int = 0,
               userAuthUrl: String = ""): Observable<ResponseWrapper>

    fun update(channelId: String,
               text: String,
               ts: String,
               asUser: Boolean = true,
               attachments: String = "",
               linkNames: Boolean = true,
               parse: String = ""): Observable<ChatMessageWrapper>

}