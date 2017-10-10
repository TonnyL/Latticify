package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 */
interface ChannelsDataSource {

    fun archive(channelId: String): Observable<ResponseWrapper>

    fun create(name: String,
               validate: Boolean = true): Observable<ChannelWrapper>

    fun history(channelId: String,
                count: Int = Api.LIMIT,
                inclusive: String = "0",
                latest: String = "0",
                oldest: String = "0",
                unreads: String = "0"): Observable<MessagesWrapper>

    fun info(channelId: String,
             includeLocale: Boolean = true): Observable<ChannelWrapper>

    fun invite(channelId: String,
               userId: String): Observable<ChannelWrapper>

    fun join(channelName: String,
             validate: Boolean = true): Observable<ChannelWrapper>

    fun kick(channelId: String,
             userId: String): Observable<ResponseWrapper>

    fun leave(channelId: String): Observable<ResponseWrapper>

    fun list(cursor: String = "",
             excludeArchived: Boolean = false,
             excludeMembers: Boolean = false,
             limit: Int = Api.LIMIT): Observable<ChannelsWrapper>

    fun mark(channelId: String,
             ts: String): Observable<ResponseWrapper>

    fun rename(channelId: String,
               name: String,
               validate: Boolean = true): Observable<ChannelWrapper>

    fun replies(channelId: String,
                threadTs: String): Observable<MessagesWrapper>

    fun setPurpose(channelId: String,
                   purpose: String): Observable<SetPurposeResultWrapper>

    fun setTopic(channelId: String,
                 topic: String): Observable<SetTopicWrapper>

    fun unarchive(channelId: String): Observable<ResponseWrapper>

}