package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.data.datasource.IMDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.IMService
import io.github.tonnyl.latticify.util.AccessTokenManager
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 09/10/2017.
 */
object IMRepository : IMDataSource {

    private val mIMService = RetrofitClient.createService(IMService::class.java, AccessTokenManager.getAccessToken())
    private val mToken = AccessTokenManager.getAccessToken().accessToken

    override fun close(channelId: String): Observable<ChannelCloseWrapper> =
            mIMService.close(mToken, channelId)

    override fun history(channelId: String, count: Int, inclusive: Int, latest: String, oldest: String, unreads: Boolean): Observable<MessagesWrapper> =
            mIMService.history(mToken, channelId, count, inclusive, latest, oldest, unreads)

    override fun list(cursor: String, limit: Int): Observable<ChannelsWrapper> =
            mIMService.list(mToken, cursor, limit)

    override fun mark(channelId: String, ts: String): Observable<ResponseWrapper> =
            mIMService.mark(mToken, channelId, ts)

    override fun open(userId: String, includeLocale: Boolean, returnIm: Boolean): Observable<ChannelWrapper> =
            mIMService.open(mToken, userId, includeLocale, returnIm)

    override fun replies(channelId: String, threadTs: String): Observable<ImRepliesWrapper> =
            mIMService.replies(mToken, channelId, threadTs)

}