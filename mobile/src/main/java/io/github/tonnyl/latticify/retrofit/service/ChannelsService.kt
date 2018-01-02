package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 06/10/2017.
 */
interface ChannelsService {

    /**
     * Archives a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to archive. e.g. C1234567890
     *
     * @return If successful, the command returns a [ResponseWrapper] object
     */
    @POST("channels.archive")
    @FormUrlEncoded
    fun archive(@Field("token") token: String,
                @Field("channel") channelId: String): Observable<ResponseWrapper>

    /**
     * Creates a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param name Required. Name of channel to create. e.g. mychannel.
     * Channel names can only contain lowercase letters, numbers, hyphens, and underscores, and must be 21 characters or less.
     * @param validate Optional. Whether to return errors on invalid channel name instead of modifying it to meet the specified criteria.
     *
     * @return If successful, the command returns a [ChannelWrapper] object.
     */
    @POST("channels.create")
    @FormUrlEncoded
    fun create(@Field("token") token: String,
               @Field("name") name: String,
               @Field("validate") validate: Boolean = true): Observable<ChannelWrapper>

    /**
     * Fetches history of messages and events from a channel.
     * The messages array contains up to 100 messages between latest and oldest.
     * If there were more than 100 messages between those two points, then has_more will be true.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to fetch history for. e.g. C1234567890.
     * @param count Optional, default=100. Number of messages to return, between 1 and 1000.
     * @param inclusive Optional, default=0. Include messages with latest or oldest timestamp in results.
     * @param latest Optional, default=0. End of time range of messages to include in results. e.g. 1234567890.123456.
     * @param oldest Optional, default=0. Start of time range of messages to include in results. e.g. 1234567890.123456.
     * @param unreads Optional, default=0. Include unread_count_display in the output?
     *
     * @return If successful, the command returns a [MessagesWrapper] object.
     */
    @POST("channels.history")
    @FormUrlEncoded
    fun history(@Field("token") token: String,
                @Field("channel") channelId: String,
                @Field("count") count: Int = Api.LIMIT,
                @Field("inclusive") inclusive: String = "0",
                @Field("latest") latest: String = "0",
                @Field("oldest") oldest: String = "0",
                @Field("unreads") unreads: String = "0"): Observable<MessagesWrapper>

    /**
     * Gets information about a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to get info on. e.g. C1234567890.
     * @param includeLocale Optional. Set this to true to receive the locale for this channel. Defaults to false.
     *
     * @return If successful, the command returns a [ChannelWrapper] object.
     */
    @POST("channels.info")
    @FormUrlEncoded
    fun info(@Field("token") token: String,
             @Field("channel") channelId: String,
             @Field("include_locale") includeLocale: Boolean = true): Observable<ChannelWrapper>

    /**
     * Invites a user to a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to invite user to. e.g. C1234567890.
     * @param userId Required. User to invite to channel. e.g. W1234567890.
     *
     * @return If successful, the command returns a [ChannelWrapper] object.
     */
    @POST("channels.invite")
    @FormUrlEncoded
    fun invite(@Field("token") token: String,
               @Field("channel") channelId: String,
               @Field("user") userId: String): Observable<ChannelWrapper>

    /**
     * Joins a channel, creating it if needed.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelName Required. Name of channel to join. e.g. #general.
     * @param validate Optional. Whether to return errors on invalid channel name instead of modifying it to meet the specified criteria.
     *
     * @return If successful, the command returns a [ChannelWrapper] object.
     */
    @POST("channels.join")
    @FormUrlEncoded
    fun join(@Field("token") token: String,
             @Field("name") channelName: String,
             @Field("validate") validate: Boolean = true): Observable<ChannelWrapper>

    /**
     * Removes a user from a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to remove user from.
     * @param userId Required. User to remove from channel.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("channels.kick")
    @FormUrlEncoded
    fun kick(@Field("token") token: String,
             @Field("channel") channelId: String,
             @Field("user") userId: String): Observable<ResponseWrapper>

    /**
     * Leaves a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to leave. e.g. C1234567890.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("channels.leave")
    @FormUrlEncoded
    fun leave(@Field("token") token: String,
              @Field("channel") channelId: String): Observable<ResponseWrapper>

    /**
     * Lists all channels in a Slack team.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param cursor Optional. Paginate through collections of data by setting the cursor parameter to a next_cursor attribute
     * returned by a previous request's response_metadata([ResponseMetaData]). Default value fetches the first "page" of the collection.
     * e.g. dXNlcjpVMDYxTkZUVDI=.
     * @param excludeArchived Optional, default=false. Exclude archived channels from the list.
     * @param excludeMembers Optional, default=false. Exclude the members collection from each channel.
     * @param limit Optional, default=0. The maximum number of items to return. Fewer than the requested number of items may be returned,
     * even if the end of the users list hasn't been reached.
     *
     * @return If successful, the command returns a list of limited channel objects ([ChannelsWrapper]).
     */
    @POST("channels.list")
    @FormUrlEncoded
    fun list(@Field("token") token: String,
             @Field("cursor") cursor: String = "",
             @Field("exclude_archived") excludeArchived: Boolean = false,
             @Field("exclude_members") excludeMembers: Boolean = false,
             @Field("limit") limit: Int = Api.LIMIT): Observable<ChannelsWrapper>

    /**
     * Sets the read cursor in a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to set reading cursor in. e.g. C1234567890.
     * @param ts Required. Timestamp of the most recently seen message.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("channels.mark")
    @FormUrlEncoded
    fun mark(@Field("token") token: String,
             @Field("channel") channelId: String,
             @Field("ts") ts: String): Observable<ResponseWrapper>

    /**
     * Renames a channel.
     * Channel names can only contain lowercase letters, numbers, hyphens, and underscores,
     * and must be 21 characters or less.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to rename. e.g. C1234567890.
     * @param name Required. New name for channel.
     * @param validate Optional. Whether to return errors on invalid channel name instead of
     * modifying it to meet the specified criteria.
     *
     * @return If successful, the command returns a [ChannelWrapper] object.
     */
    @POST("channels.rename")
    @FormUrlEncoded
    fun rename(@Field("token") token: String,
               @Field("channel") channelId: String,
               @Field("name") name: String,
               @Field("validate") validate: Boolean = true): Observable<ChannelWrapper>


    /**
     * Retrieve a thread of messages posted to a channel.
     * The [channelId] and [threadTs] arguments are always required.
     * [threadTs] must be the timestamp of an existing message with 0 or more replies.
     * If there are no replies then just the single message referenced by [threadTs]
     * will be returned - it is just an ordinary message.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to fetch thread from. e.g. C1234567890.
     * @param threadTs Required. Unique identifier of a thread's parent message. e.g. 1234567890.123456.
     *
     * @return If successful, the command returns a [MessagesWrapper] object.
     */
    @POST("channels.replies")
    @FormUrlEncoded
    fun replies(@Field("token") token: String,
                @Field("channel") channelId: String,
                @Field("thread_ts") threadTs: String): Observable<MessagesWrapper>

    /**
     * Sets the purpose for a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to set the purpose of. e.g. C1234567890.
     * @param purpose Required. The new purpose. e.g. My Purpose.
     *
     * @return If successful, the command returns a [SetPurposeResultWrapper] object.
     */
    @POST("channels.setPurpose")
    @FormUrlEncoded
    fun setPurpose(@Field("token") token: String,
                   @Field("channel") channelId: String,
                   @Field("purpose") purpose: String): Observable<SetPurposeResultWrapper>

    /**
     * Sets the topic for a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to set the topic of. e.g. C1234567890.
     * @param topic Required. The new topic. e.g. My Topic.
     *
     * @return If successful, the command returns a [SetTopicWrapper] object.
     */
    @POST("channels.setTopic")
    @FormUrlEncoded
    fun setTopic(@Field("token") token: String,
                 @Field("channel") channelId: String,
                 @Field("topic") topic: String): Observable<SetTopicWrapper>

    /**
     * Unarchives a channel.
     *
     * @param channelId Required. Channel to unarchive. e.g. C1234567890.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("channels.unarchive")
    @FormUrlEncoded
    fun unarchive(@Field("token") token: String,
                  @Field("channel") channelId: String): Observable<ResponseWrapper>

}