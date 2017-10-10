package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 09/10/2017.
 */
interface IMDataSource {

    fun close(channelId: String): Observable<ChannelCloseWrapper>

    fun history(channelId: String,
                count: Int = Api.LIMIT,
                inclusive: Int = 0,
                latest: String = "",
                oldest: String = "",
                unreads: Boolean = true): Observable<MessagesWrapper>

    fun list(cursor: String = "",
             limit: Int = Api.LIMIT): Observable<ChannelsWrapper>

    fun mark(channelId: String,
             ts: String): Observable<ResponseWrapper>

    fun open(userId: String,
             includeLocale: Boolean = true,
             returnIm: Boolean = true): Observable<ChannelWrapper>

    fun replies(channelId: String,
                threadTs: String): Observable<ImRepliesWrapper>

}