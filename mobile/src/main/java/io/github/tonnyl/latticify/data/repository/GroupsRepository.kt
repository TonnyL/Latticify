package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.data.datasource.GroupsDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.GroupsService
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 09/10/2017.
 */
object GroupsRepository : GroupsDataSource {

    private val mGroupsService: GroupsService = RetrofitClient.createService(GroupsService::class.java)

    private val mToken = RetrofitClient.mToken

    override fun archive(channelId: String): Observable<ResponseWrapper> =
            mGroupsService.archive(mToken, channelId)

    override fun create(name: String,
                        validate: Boolean): Observable<GroupWrapper> =
            mGroupsService.create(mToken,
                    name,
                    validate)

    override fun createChild(channelId: String): Observable<GroupWrapper> =
            mGroupsService.createChild(mToken, channelId)

    override fun history(channelId: String,
                         count: Int,
                         inclusive: String,
                         latest: String,
                         oldest: String,
                         unreads: String): Observable<MessagesWrapper> =
            mGroupsService.history(mToken,
                    channelId,
                    count,
                    inclusive,
                    latest,
                    oldest,
                    unreads)

    override fun info(channelId: String,
                      includeLocale: Boolean): Observable<GroupWrapper> =
            mGroupsService.info(mToken,
                    channelId,
                    includeLocale)

    override fun invite(channelId: String,
                        userId: String): Observable<GroupWrapper> =
            mGroupsService.invite(mToken,
                    channelId,
                    userId)

    override fun kick(channelId: String,
                      userId: String): Observable<ResponseWrapper> =
            mGroupsService.kick(mToken,
                    channelId,
                    userId)

    override fun leave(channelId: String): Observable<ResponseWrapper> =
            mGroupsService.leave(mToken,
                    channelId)

    override fun list(cursor: String,
                      excludeArchived: Boolean,
                      excludeMembers: Boolean,
                      limit: Int): Observable<GroupsWrapper> =
            mGroupsService.list(mToken,
                    cursor,
                    excludeArchived,
                    excludeMembers,
                    limit)

    override fun mark(channelId: String,
                      ts: String): Observable<ResponseWrapper> =
            mGroupsService.mark(mToken,
                    channelId,
                    ts)

    override fun open(channelId: String): Observable<ChannelOpenWrapper> =
            mGroupsService.open(mToken,
                    channelId)

    override fun rename(channelId: String,
                        name: String,
                        validate: Boolean): Observable<GroupWrapper> =
            mGroupsService.rename(mToken,
                    channelId,
                    name)

    override fun replies(channelId: String,
                         threadTs: String): Observable<MessagesWrapper> =
            mGroupsService.replies(mToken,
                    channelId,
                    threadTs)

    override fun setPurpose(channelId: String,
                            purpose: String): Observable<SetPurposeResultWrapper> =
            mGroupsService.setPurpose(mToken,
                    channelId,
                    purpose)

    override fun setTopic(channelId: String,
                          topic: String): Observable<SetTopicWrapper> =
            mGroupsService.setTopic(mToken,
                    channelId,
                    topic)

    override fun unarchive(channelId: String): Observable<ResponseWrapper> =
            mGroupsService.unarchive(mToken,
                    channelId)

}