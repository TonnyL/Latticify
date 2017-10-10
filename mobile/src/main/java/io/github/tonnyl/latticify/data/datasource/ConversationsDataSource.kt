package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 09/10/2017.
 */
interface ConversationsDataSource {

    fun archive(channelId: String): Observable<ResponseWrapper>

    fun close(channelId: String): Observable<ChannelCloseWrapper>

    fun create(name: String,
               isPrivate: Boolean = false): Observable<ChannelWrapper>

    fun history(channelId: String,
                cursor: String = "",
                latest: String = "0",
                limit: Int = 20,
                oldest: String = "0"): Observable<MessagesWrapper>

    fun info(channelId: String,
             includeLocale: Boolean = true): Observable<ChannelWrapper>

    fun invite(channelId: String,
               usersId: List<String>): Observable<ChannelWrapper>

    fun join(channelName: String): Observable<ChannelWrapper>

    fun kick(channelId: String,
             userId: String): Observable<ResponseWrapper>

    fun leave(channelId: String): Observable<ResponseWrapper>

    fun list(cursor: String = "",
             excludeArchived: Boolean = false,
             limit: Int = Api.LIMIT,
             types: String): Observable<ChannelsWrapper>

    fun members(channelId: String,
                cursor: String,
                limit: Int = Api.LIMIT): Observable<MembersWrapper>

    fun open(channelId: String,
             returnIm: Boolean = false,
             usersId: List<String>): Observable<ChannelWrapper>

    fun rename(channelId: String,
               name: String): Observable<ChannelWrapper>

    fun replies(channelId: String,
                ts: String,
                cursor: String = "",
                limit: Int = Api.LIMIT): Observable<MessagesWrapper>

    fun setPurpose(channelId: String,
                   purpose: String): Observable<SetPurposeResultWrapper>

    fun setTopic(channelId: String,
                 topic: String): Observable<SetTopicWrapper>

    fun unarchive(channelId: String): Observable<ResponseWrapper>

}