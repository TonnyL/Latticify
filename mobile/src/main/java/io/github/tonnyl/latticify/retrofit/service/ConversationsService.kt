package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 24/09/2017.
 */
interface ConversationsService {

    /**
     * Archives a conversation.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Id of conversation to archive. e.g. C1234567890
     *
     * @return If successful, the command returns a [ResponseWrapper] object
     */
    @POST("conversations.archive")
    @FormUrlEncoded
    fun archive(@Field("token") token: String,
                @Field("channel") channelId: String): Observable<ResponseWrapper>

    /**
     * Closes a direct message or multi-person direct message.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Conversation to close. e.g. G1234567890.
     *
     * @return If the channel was already closed the response will include no_op and already_closed properties.
     * It means Slack did nothing really.
     */
    @POST("conversations.close")
    @FormUrlEncoded
    fun close(@Field("token") token: String,
              @Field("channel") channelId: String): Observable<ChannelCloseWrapper>

    /**
     * Initiates a public or private channel-based conversation.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param name Required. Name of channel to create. e.g. mychannel.
     * Channel names can only contain lowercase letters, numbers, hyphens, and underscores, and must be 21 characters or less.
     * @param isPrivate Optional. Create a private channel instead of a public one.
     *
     * @return If successful, the command returns a [ChannelWrapper] object.
     */
    @POST("conversations.create")
    @FormUrlEncoded
    fun create(@Field("token") token: String,
               @Field("name") name: String,
               @Field("is_private") isPrivate: Boolean = false): Observable<ChannelWrapper>

    /**
     * Fetches a conversation's history of messages and events.
     * The messages array contains up to 100 messages between latest and oldest.
     * If there were more than 100 messages between those two points, then has_more will be true.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to fetch history for. e.g. C1234567890.
     * @param cursor Optional. Paginate through collections of data by setting the cursor parameter to a next_cursor attribute
     * returned by a previous request's response_metadata. Default value fetches the first "page" of the collection.
     * @param latest Optional, default=0. End of time range of messages to include in results. e.g. 1234567890.123456.
     * @param limit Optional, default=20. The maximum number of items to return. Fewer than the requested number of items may be returned,
     * even if the end of the users list hasn't been reached.
     * @param oldest Optional, default=0. Start of time range of messages to include in results. e.g. 1234567890.123456.
     *
     * @return If successful, the command returns a [MessagesWrapper] object.
     */
    @POST("conversations.history")
    @FormUrlEncoded
    fun history(@Field("token") token: String,
                @Field("channel") channelId: String,
                @Field("cursor") cursor: String = "",
                @Field("latest") latest: String = "0",
                @Field("limit") limit: Int = 20,
                @Field("oldest") oldest: String = "0"): Observable<MessagesWrapper>

    /**
     * Retrieve information about a conversation.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to get info on. e.g. C1234567890.
     * @param includeLocale Optional. Set this to true to receive the locale for this channel. Defaults to true.
     *
     * @return If successful, the command returns a [ChannelWrapper] object.
     */
    @POST("conversations.info")
    @FormUrlEncoded
    fun info(@Field("token") token: String,
             @Field("channel") channelId: String,
             @Field("include_locale") includeLocale: Boolean = true): Observable<ChannelWrapper>

    /**
     * Invites a user to a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to invite user to. e.g. C1234567890.
     * @param usersId Required. A comma separated list of user IDs. Up to 30 users may be listed.
     * e.g. W1234567890,U2345678901,U3456789012.
     *
     * @return If successful, the command returns a [ChannelWrapper] object.
     */
    @POST("conversations.invite")
    @FormUrlEncoded
    fun invite(@Field("token") token: String,
               @Field("channel") channelId: String,
               @Field("users") usersId: List<String>): Observable<ChannelWrapper>

    /**
     * Joins an existing conversation.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelName Required. ID of conversation to join. e.g. C1234567890.
     *
     * @return If successful, the command returns a [ChannelWrapper] object.
     */
    @POST("conversations.join")
    @FormUrlEncoded
    fun join(@Field("token") token: String,
             @Field("name") channelName: String): Observable<ChannelWrapper>

    /**
     * Removes a user from a conversations.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Id of conversations to remove user from.
     * @param userId Required. User ID to be removed.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("conversations.kick")
    @FormUrlEncoded
    fun kick(@Field("token") token: String,
             @Field("channel") channelId: String,
             @Field("user") userId: String): Observable<ResponseWrapper>

    /**
     * Leaves a conversation.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Conversation to leave. e.g. C1234567890.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("conversations.leave")
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
     * @param limit Optional, default=0. The maximum number of items to return. Fewer than the requested number of items may be returned,
     * even if the end of the users list hasn't been reached.
     * @param types Optional, default=public_channel. Mix and match channel types by providing a comma-separated list of any combination of
     * public_channel, private_channel, mpim, im.
     *
     * @return If successful, the command returns a list of limited channel objects ([ChannelsWrapper]).
     */
    @POST("conversations.list")
    @FormUrlEncoded
    fun list(@Field("token") token: String,
             @Field("cursor") cursor: String = "",
             @Field("exclude_archived") excludeArchived: Boolean = true,
             @Field("limit") limit: Int = Api.LIMIT,
             @Field("types") types: String): Observable<ChannelsWrapper>

    /**
     * Retrieve members of a conversation.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. ID of the conversation to retrieve members for. e.g. C1234567890.
     * @param cursor Optional. Paginate through collections of data by setting the cursor parameter to a next_cursor attribute
     * returned by a previous request's response_metadata. Default value fetches the first "page" of the collection.
     * @param limit Optional, default=20.The maximum number of items to return. Fewer than the requested number of items may be returned,
     * even if the end of the users list hasn't been reached.
     *
     * @return If successful, the command returns a [MembersWrapper] object.
     */
    @POST("conversations.members")
    @FormUrlEncoded
    fun members(@Field("token") token: String,
                @Field("channel") channelId: String,
                @Field("cursor") cursor: String,
                @Field("limit") limit: Int = Api.LIMIT): Observable<MembersWrapper>

    /**
     * Opens or resumes a direct message or multi-person direct message.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Resume a conversation by supplying an im or mpim's ID. Or provide the users field instead.
     * @param returnIm Optional. Boolean, indicates you want the full IM channel definition in the response.
     * Response structure is altered by providing return_im parameter. When set to false, the default, just a conversation's ID is returned.
     * When set to true, the entire conversation object is returned.
     * @param usersId Optional. Comma separated lists of users. If only one user is included, this creates a 1:1 DM.
     * The ordering of the users is preserved whenever a multi-person direct message is returned. Supply a channel when not supplying users.
     *
     * @return If the private channel was already open the response will include no_op and already_open properties.
     */
    @POST("conversations.open")
    @FormUrlEncoded
    fun open(@Field("token") token: String,
             @Field("channel") channelId: String,
             @Field("return_im") returnIm: Boolean = false,
             @Field("users") usersId: List<String>): Observable<ChannelWrapper>

    /**
     * Renames a conversation.
     * Channel names can only contain lowercase letters, numbers, hyphens, and underscores,
     * and must be 21 characters or less.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. ID of conversation to rename. e.g. C1234567890.
     * @param name Required. New name for conversation.
     *
     * @return If successful, the command returns a [ChannelWrapper] object.
     */
    @POST("conversations.rename")
    @FormUrlEncoded
    fun rename(@Field("token") token: String,
               @Field("channel") channelId: String,
               @Field("name") name: String): Observable<ChannelWrapper>

    /**
     * Retrieve a thread of messages posted to a conversation.
     * The [channelId] and [ts] arguments are always required.
     * [ts] must be the timestamp of an existing message with 0 or more replies.
     * If there are no replies then just the single message referenced by [threadTs]
     * will be returned - it is just an ordinary message.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to fetch thread from. e.g. C1234567890.
     * @param ts Required. Unique identifier of a thread's parent message. e.g. 1234567890.123456.
     * @param cursor Optional. Paginate through collections of data by setting the cursor parameter
     * to a next_cursor attribute returned by a previous request's response_metadata.
     * Default value fetches the first "page" of the collection.
     * @param limit Optional, default=20. The maximum number of items to return. Fewer than the requested number of items may be returned,
     * even if the end of the users list hasn't been reached.
     *
     * @return If successful, the command returns a [MessagesWrapper] object.
     */
    @POST("conversations.replies")
    @FormUrlEncoded
    fun replies(@Field("token") token: String,
                @Field("channel") channelId: String,
                @Field("ts") ts: String,
                @Field("cursor") cursor: String = "",
                @Field("limit") limit: Int = Api.LIMIT): Observable<MessagesWrapper>

    /**
     * Sets the purpose for a conversation.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Conversation to set the purpose of. e.g. C1234567890.
     * @param purpose Required. The new purpose. e.g. My Purpose.
     *
     * @return If successful, the command returns a [SetPurposeResultWrapper] object.
     */
    @POST("conversations.setPurpose")
    @FormUrlEncoded
    fun setPurpose(@Field("token") token: String,
                   @Field("channel") channelId: String,
                   @Field("purpose") purpose: String): Observable<SetPurposeResultWrapper>

    /**
     * Sets the topic for a conversation.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. The new topic string. Does not support formatting or linkification. of. e.g. C1234567890.
     * @param topic Required. The new topic. e.g. My Topic.
     *
     * @return If successful, the command returns a [SetTopicWrapper] object.
     */
    @POST("conversations.setTopic")
    @FormUrlEncoded
    fun setTopic(@Field("token") token: String,
                 @Field("channel") channelId: String,
                 @Field("topic") topic: String): Observable<SetTopicWrapper>

    /**
     * Unarchives a conversation.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Conversation to unarchive. e.g. C1234567890.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("conversations.unarchive")
    @FormUrlEncoded
    fun unarchive(@Field("token") token: String,
                  @Field("channel") channelId: String): Observable<ResponseWrapper>
}