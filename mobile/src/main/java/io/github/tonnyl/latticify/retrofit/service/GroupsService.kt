package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 09/10/2017.
 */
interface GroupsService {

    /**
     * Archives a private channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to archive. e.g. C1234567890
     *
     * @return If successful, the command returns a [ResponseWrapper] object
     */
    @POST("groups.archive")
    @FormUrlEncoded
    fun archive(@Field("token") token: String,
                @Field("channel") channelId: String): Observable<ResponseWrapper>

    /**
     * Creates a private channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param name Required. Name of channel to create. e.g. mychannel.
     * Channel names can only contain lowercase letters, numbers, hyphens, and underscores, and must be 21 characters or less.
     * @param validate Optional. Whether to return errors on invalid channel name instead of modifying it to meet the specified criteria.
     *
     * @return If successful, the command returns a [GroupWrapper] object.
     */
    @POST("groups.create")
    @FormUrlEncoded
    fun create(@Field("token") token: String,
               @Field("name") name: String,
               @Field("validate") validate: Boolean = true): Observable<GroupWrapper>

    /**
     * Clones and archives a private channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to clone and archive. e.g. G1234567890.
     *
     * @return If successful, the command returns the new group object ([io.github.tonnyl.latticify.data.Channel]).
     */
    @POST("groups.createChild")
    @FormUrlEncoded
    fun createChild(@Field("token") token: String,
                    @Field("channel") channelId: String): Observable<GroupWrapper>

    /**
     * Fetches history of messages and events from a private channel.
     * The messages array contains up to 100 messages between latest and oldest.
     * If there were more than 100 messages between those two points, then has_more will be true.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to fetch history for. e.g. C1234567890.
     * @param count Optional, default=100. Number of messages to return, between 1 and 1000.
     * @param inclusive Optional, default=0. Include messages with latest or oldest timestamp in results.
     * @param latest Optional, default=0. End of time range of messages to include in results. e.g. 1234567890.123456.
     * @param oldest Optional, default=0. Start of time range of messages to include in results. e.g. 1234567890.123456.
     * @param unreads Optional, default=0. Include unread_count_display in the output?
     *
     * @return If successful, the command returns a [MessagesWrapper] object.
     */
    @POST("groups.history")
    @FormUrlEncoded
    fun history(@Field("token") token: String,
                @Field("channel") channelId: String,
                @Field("count") count: Int = Api.LIMIT,
                @Field("inclusive") inclusive: String = "0",
                @Field("latest") latest: String = "0",
                @Field("oldest") oldest: String = "0",
                @Field("unreads") unreads: String = "0"): Observable<MessagesWrapper>

    /**
     * Gets information about a private channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to get info on. e.g. C1234567890.
     * @param includeLocale Optional. Set this to true to receive the locale for this channel. Defaults to false.
     *
     * @return If successful, the command returns a [GroupWrapper] object.
     */
    @POST("groups.info")
    @FormUrlEncoded
    fun info(@Field("token") token: String,
             @Field("channel") channelId: String,
             @Field("include_locale") includeLocale: Boolean = true): Observable<GroupWrapper>

    /**
     * Invites a user to a private channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to invite user to. e.g. C1234567890.
     * @param userId Required. User to invite to channel. e.g. W1234567890.
     *
     * @return If successful, the command returns a [GroupWrapper] object.
     */
    @POST("groups.invite")
    @FormUrlEncoded
    fun invite(@Field("token") token: String,
               @Field("channel") channelId: String,
               @Field("user") userId: String): Observable<GroupWrapper>

    /**
     * Removes a user from a private channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to remove user from.
     * @param userId Required. User to remove from channel.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("groups.kick")
    @FormUrlEncoded
    fun kick(@Field("token") token: String,
             @Field("channel") channelId: String,
             @Field("user") userId: String): Observable<ResponseWrapper>

    /**
     * Leaves a private channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to leave. e.g. C1234567890.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("groups.leave")
    @FormUrlEncoded
    fun leave(@Field("token") token: String,
              @Field("channel") channelId: String): Observable<ResponseWrapper>

    /**
     * Lists private channels that the calling user has access to.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param cursor Optional. Paginate through collections of data by setting the cursor parameter to a next_cursor attribute
     * returned by a previous request's response_metadata([io.github.tonnyl.latticify.data.ResponseMetaData]). Default value fetches the first "page" of the collection.
     * e.g. dXNlcjpVMDYxTkZUVDI=.
     * @param excludeArchived Optional, default=false. Exclude archived channels from the list.
     * @param excludeMembers Optional, default=false. Exclude the members collection from each channel.
     * @param limit Optional, default=0. The maximum number of items to return. Fewer than the requested number of items may be returned,
     * even if the end of the users list hasn't been reached.
     *
     * @return If successful, the command returns a list of group objects ([GroupsWrapper]) (also known as "private channel objects").
     */
    @POST("groups.list")
    @FormUrlEncoded
    fun list(@Field("token") token: String,
             @Field("cursor") cursor: String = "",
             @Field("exclude_archived") excludeArchived: Boolean = false,
             @Field("exclude_members") excludeMembers: Boolean = false,
             @Field("limit") limit: Int = Api.LIMIT): Observable<GroupsWrapper>

    /**
     * Sets the read cursor in a private channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to set reading cursor in. e.g. C1234567890.
     * @param ts Required. Timestamp of the most recently seen message.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("groups.mark")
    @FormUrlEncoded
    fun mark(@Field("token") token: String,
             @Field("channel") channelId: String,
             @Field("ts") ts: String): Observable<ResponseWrapper>

    /**
     * Opens a private channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to open.
     *
     * @return If the private channel was already open the response will include no_op and already_open properties.
     */
    @POST("groups.open")
    @FormUrlEncoded
    fun open(@Field("token") token: String,
             @Field("channel") channelId: String): Observable<ChannelOpenWrapper>

    /**
     * Renames a private channel.
     * Channel names can only contain lowercase letters, numbers, hyphens, and underscores,
     * and must be 21 characters or less.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to rename. e.g. C1234567890.
     * @param name Required. New name for channel.
     * @param validate Optional. Whether to return errors on invalid channel name instead of
     * modifying it to meet the specified criteria.
     *
     * @return If successful, the command returns a [GroupWrapper] object.
     */
    @POST("groups.rename")
    @FormUrlEncoded
    fun rename(@Field("token") token: String,
               @Field("channel") channelId: String,
               @Field("name") name: String,
               @Field("validate") validate: Boolean = true): Observable<GroupWrapper>

    /**
     * Retrieve a thread of messages posted to a private channel.
     * The [channelId] and [threadTs] arguments are always required.
     * [threadTs] must be the timestamp of an existing message with 0 or more replies.
     * If there are no replies then just the single message referenced by [threadTs]
     * will be returned - it is just an ordinary message.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to fetch thread from. e.g. C1234567890.
     * @param threadTs Required. Unique identifier of a thread's parent message. e.g. 1234567890.123456.
     *
     * @return If successful, the command returns a [MessagesWrapper] object.
     */
    @POST("groups.replies")
    @FormUrlEncoded
    fun replies(@Field("token") token: String,
                @Field("channel") channelId: String,
                @Field("thread_ts") threadTs: String): Observable<MessagesWrapper>

    /**
     * Sets the purpose for a private channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to set the purpose of. e.g. C1234567890.
     * @param purpose Required. The new purpose. e.g. My Purpose.
     *
     * @return If successful, the command returns a [SetPurposeResultWrapper] object.
     */
    @POST("groups.setPurpose")
    @FormUrlEncoded
    fun setPurpose(@Field("token") token: String,
                   @Field("channel") channelId: String,
                   @Field("purpose") purpose: String): Observable<SetPurposeResultWrapper>

    /**
     * Sets the topic for a private channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to set the topic of. e.g. C1234567890.
     * @param topic Required. The new topic. e.g. My Topic.
     *
     * @return If successful, the command returns a [SetTopicWrapper] object.
     */
    @POST("groups.setTopic")
    @FormUrlEncoded
    fun setTopic(@Field("token") token: String,
                 @Field("channel") channelId: String,
                 @Field("topic") topic: String): Observable<SetTopicWrapper>

    /**
     * Unarchives a private channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Private channel to unarchive. e.g. C1234567890.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("groups.unarchive")
    @FormUrlEncoded
    fun unarchive(@Field("token") token: String,
                  @Field("channel") channelId: String): Observable<ResponseWrapper>


}