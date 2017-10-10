package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 09/10/2017.
 */
interface GroupsDataSource {

    fun archive(channelId: String): Observable<ResponseWrapper>

    fun create(name: String,
               validate: Boolean = true): Observable<GroupWrapper>

    fun createChild(channelId: String): Observable<GroupWrapper>

    fun history(channelId: String,
                count: Int = Api.LIMIT,
                inclusive: String = "0",
                latest: String = "0",
                oldest: String = "0",
                unreads: String = "0"): Observable<MessagesWrapper>

    fun info(channelId: String,
             includeLocale: Boolean = true): Observable<GroupWrapper>

    fun invite(channelId: String,
               userId: String): Observable<GroupWrapper>

    fun kick(channelId: String,
             userId: String): Observable<ResponseWrapper>

    fun leave(channelId: String): Observable<ResponseWrapper>

    fun list(cursor: String = "",
             excludeArchived: Boolean = false,
             excludeMembers: Boolean = false,
             limit: Int = Api.LIMIT): Observable<GroupsWrapper>

    fun mark(channelId: String,
             ts: String): Observable<ResponseWrapper>

    fun open(channelId: String): Observable<ChannelOpenWrapper>

    fun rename(channelId: String,
               name: String,
               validate: Boolean = true): Observable<GroupWrapper>

    fun replies(channelId: String,
                threadTs: String): Observable<MessagesWrapper>

    fun setPurpose(channelId: String,
                   purpose: String): Observable<SetPurposeResultWrapper>

    fun setTopic(channelId: String,
                 topic: String): Observable<SetTopicWrapper>

    fun unarchive(channelId: String): Observable<ResponseWrapper>

}