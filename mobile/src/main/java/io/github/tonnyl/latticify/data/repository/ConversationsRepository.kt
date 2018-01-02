package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.data.datasource.ConversationsDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.ConversationsService
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 09/10/2017.
 */
object ConversationsRepository : ConversationsDataSource {

    private val mConversationsService: ConversationsService = RetrofitClient.createService(ConversationsService::class.java)

    private val mToken = RetrofitClient.mToken

    override fun archive(channelId: String): Observable<ResponseWrapper> =
            mConversationsService.archive(mToken, channelId)

    override fun close(channelId: String): Observable<ChannelCloseWrapper> =
            mConversationsService.close(mToken, channelId)

    override fun create(name: String, isPrivate: Boolean): Observable<ChannelWrapper> =
            mConversationsService.create(mToken, name, isPrivate)

    override fun history(channelId: String, cursor: String, latest: String, limit: Int, oldest: String): Observable<MessagesWrapper> =
            mConversationsService.history(mToken, channelId, cursor, latest, limit, oldest)

    override fun info(channelId: String, includeLocale: Boolean): Observable<ChannelWrapper> =
            mConversationsService.info(mToken, channelId, includeLocale)

    override fun invite(channelId: String, usersId: List<String>): Observable<ChannelWrapper> =
            mConversationsService.invite(mToken, channelId, usersId)

    override fun join(channelName: String): Observable<ChannelWrapper> =
            mConversationsService.join(mToken, channelName)

    override fun kick(channelId: String, userId: String): Observable<ResponseWrapper> =
            mConversationsService.kick(mToken, channelId, userId)

    override fun leave(channelId: String): Observable<ResponseWrapper> =
            mConversationsService.leave(mToken, channelId)

    override fun list(cursor: String, excludeArchived: Boolean, limit: Int, types: String): Observable<ChannelsWrapper> =
            mConversationsService.list(mToken, cursor, excludeArchived, limit, types)

    override fun members(channelId: String, cursor: String, limit: Int): Observable<MembersWrapper> =
            mConversationsService.members(mToken, channelId, cursor, limit)

    override fun open(channelId: String, returnIm: Boolean, usersId: List<String>): Observable<ChannelWrapper> =
            mConversationsService.open(mToken, channelId, returnIm, usersId)

    override fun rename(channelId: String, name: String): Observable<ChannelWrapper> =
            mConversationsService.rename(mToken, channelId, name)

    override fun replies(channelId: String, ts: String, cursor: String, limit: Int): Observable<MessagesWrapper> =
            mConversationsService.replies(mToken, channelId, ts, cursor, limit)

    override fun setPurpose(channelId: String, purpose: String): Observable<SetPurposeResultWrapper> =
            mConversationsService.setPurpose(mToken, channelId, purpose)

    override fun setTopic(channelId: String, topic: String): Observable<SetTopicWrapper> =
            mConversationsService.setTopic(mToken, channelId, topic)

    override fun unarchive(channelId: String): Observable<ResponseWrapper> =
            mConversationsService.unarchive(mToken, channelId)

}