package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.ChatMessageWrapper
import io.github.tonnyl.latticify.data.EphemeralChatMessageWrapper
import io.github.tonnyl.latticify.data.PostMessageWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.data.datasource.ChatDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.ChatService
import io.reactivex.Observable

object ChatRepository : ChatDataSource {

    private val mChatService = RetrofitClient.createService(ChatService::class.java)
    private val mToken = RetrofitClient.mToken

    override fun delete(channelId: String, ts: String, asUser: Boolean): Observable<ChatMessageWrapper> {
        return mChatService.delete(mToken, channelId, ts, asUser)
    }

    override fun meMessage(channelId: String, text: String): Observable<ChatMessageWrapper> {
        return mChatService.meMessage(mToken, channelId, text)
    }

    override fun postEphemeral(channelId: String, text: String, user: String, asUser: Boolean, attachments: String, linkNames: Boolean, parse: String): Observable<EphemeralChatMessageWrapper> {
        return mChatService.postEphemeral(mToken, channelId, text, user, asUser, attachments, linkNames, parse)
    }

    override fun postMessage(channelId: String, text: String, asUser: Boolean, attachments: String, iconEmoji: String, iconUrl: String, linkNames: Boolean, parse: String, replyBroadcast: Boolean, threadTs: String, unfurlLinks: Boolean, unfurlMedia: Boolean, username: String): Observable<PostMessageWrapper> {
        return mChatService.postMessage(mToken, channelId, text, asUser, attachments, iconEmoji, iconUrl, linkNames, parse, replyBroadcast, threadTs, unfurlLinks, unfurlMedia, username)
    }

    override fun unfurl(channelId: String, ts: String, unfurls: String, userAuthMessage: String, userAuthRequired: Int, userAuthUrl: String): Observable<ResponseWrapper> {
        return mChatService.unfurl(mToken, channelId, ts, unfurls, userAuthMessage, userAuthRequired, userAuthUrl)
    }

    override fun update(channelId: String, text: String, ts: String, asUser: Boolean, attachments: String, linkNames: Boolean, parse: String): Observable<ChatMessageWrapper> {
        return mChatService.update(mToken, channelId, text, ts, asUser, attachments, linkNames, parse)
    }


}