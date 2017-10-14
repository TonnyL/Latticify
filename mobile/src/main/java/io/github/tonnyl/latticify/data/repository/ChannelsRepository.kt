package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.data.datasource.ChannelsDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.ChannelsService
import io.github.tonnyl.latticify.util.AccessTokenManager
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 06/10/2017.
 */
object ChannelsRepository : ChannelsDataSource {

    private val mChannelsService = RetrofitClient.createService(ChannelsService::class.java, AccessTokenManager.getAccessToken())
    private val mToken = AccessTokenManager.getAccessToken().accessToken

    override fun archive(channelId: String): Observable<ResponseWrapper> =
            mChannelsService.archive(mToken, channelId)

    override fun create(name: String, validate: Boolean): Observable<ChannelWrapper> =
            mChannelsService.create(mToken, name, validate)

    override fun history(channelId: String, count: Int, inclusive: String, latest: String, oldest: String, unreads: String): Observable<MessagesWrapper> =
            mChannelsService.history(mToken, channelId, count, inclusive, latest, oldest, unreads)

    override fun info(channelId: String, includeLocale: Boolean): Observable<ChannelWrapper> =
            mChannelsService.info(mToken, channelId, includeLocale)

    override fun invite(channelId: String, userId: String): Observable<ChannelWrapper> =
            mChannelsService.invite(mToken, channelId, userId)

    override fun join(channelName: String, validate: Boolean): Observable<ChannelWrapper> =
            mChannelsService.join(mToken, channelName, validate)

    override fun kick(channelId: String, userId: String): Observable<ResponseWrapper> =
            mChannelsService.kick(mToken, channelId, userId)

    override fun leave(channelId: String): Observable<ResponseWrapper> =
            mChannelsService.leave(mToken, channelId)

    override fun list(cursor: String, excludeArchived: Boolean, excludeMembers: Boolean, limit: Int): Observable<ChannelsWrapper> =
            mChannelsService.list(mToken, cursor, excludeArchived, excludeMembers, limit)

    override fun mark(channelId: String, ts: String): Observable<ResponseWrapper> =
            mChannelsService.mark(mToken, channelId, ts)

    override fun rename(channelId: String, name: String, validate: Boolean): Observable<ChannelWrapper> =
            mChannelsService.rename(mToken, channelId, name, validate)

    override fun replies(channelId: String, threadTs: String): Observable<MessagesWrapper> =
            mChannelsService.replies(mToken, channelId, threadTs)

    override fun setPurpose(channelId: String, purpose: String): Observable<SetPurposeResultWrapper> =
            mChannelsService.setPurpose(mToken, channelId, purpose)

    override fun setTopic(channelId: String, topic: String): Observable<SetTopicWrapper> =
            mChannelsService.setTopic(mToken, channelId, topic)

    override fun unarchive(channelId: String): Observable<ResponseWrapper> =
            mChannelsService.unarchive(mToken, channelId)

}