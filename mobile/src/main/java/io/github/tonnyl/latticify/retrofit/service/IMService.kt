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
interface IMService {

    /**
     * Closes a direct message channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Direct message channel to close. e.g. D1234567890.
     *
     * @return If successful, the command returns a [ChannelCloseWrapper] object.
     */
    @POST("im.close")
    @FormUrlEncoded
    fun close(@Field("token") token: String,
              @Field("channel") channelId: String): Observable<ChannelCloseWrapper>

    /**
     * Fetches history of messages and events from direct message channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Direct message channel to fetch history for. e.g. D1234567890.
     * @param count Optional, default=100. Number of messages to return, between 1 and 1000.
     * @param inclusive Optional, default=0. Include messages with latest or oldest timestamp in results.
     * @param latest Optional, default=now. End of time range of messages to include in results. e.g. 1234567890.123456.
     * @param oldest Optional, default=0. Start of time range of messages to include in results. e.g. 1234567890.123456.
     * @param unreads Optional, default=0. Include unread_count_display in the output?
     *
     * @return If successful, the command returns a [MessagesWrapper] object.
     */
    @POST("im.history")
    @FormUrlEncoded
    fun history(@Field("token") token: String,
                @Field("channel") channelId: String,
                @Field("count") count: Int = Api.LIMIT,
                @Field("inclusive") inclusive: Int = 0,
                @Field("latest") latest: String = "",
                @Field("oldest") oldest: String = "",
                @Field("unreads") unreads: Boolean = true): Observable<MessagesWrapper>

    /**
     * Lists direct message channels for the calling user.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param cursor Optional. Paginate through collections of data by setting the cursor parameter to a next_cursor attribute
     * returned by a previous request's response_metadata. Default value fetches the first "page" of the collection.
     * @param limit Optional. The maximum number of items to return. Fewer than the requested number of items may be returned,
     * even if the end of the users list hasn't been reached.
     *
     * @return If successful, the command returns a [ChannelsWrapper] object.
     */
    @POST("im.list")
    @FormUrlEncoded
    fun list(@Field("token") token: String,
             @Field("cursor") cursor: String = "",
             @Field("limit") limit: Int = Api.LIMIT): Observable<ChannelsWrapper>

    /**
     * Sets the read cursor in a direct message channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Direct message channel to set reading cursor in. e.g. D1234567890.
     * @param ts Required. Timestamp of the most recently seen message. e.g. 1234567890.123456.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("im.mark")
    @FormUrlEncoded
    fun mark(@Field("token") token: String,
             @Field("channel") channelId: String,
             @Field("ts") ts: String): Observable<ResponseWrapper>

    /**
     * Opens a direct message channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param userId Required. User to open a direct message channel with. e.g. W1234567890.
     * @param includeLocale Optional. Set this to true to receive the locale for this im. Defaults to false.
     * @param returnIm Optional. Boolean, indicates you want the full IM channel definition in the response.
     *
     * @return If [returnIm] sets true and successful, the command returns a [ChannelWrapper] object.
     */
    @POST("im.open")
    @FormUrlEncoded
    fun open(@Field("token") token: String,
             @Field("user") userId: String,
             @Field("include_locale") includeLocale: Boolean = true,
             @Field("return_im") returnIm: Boolean = true): Observable<ChannelWrapper>

    /**
     * Retrieve a thread of messages posted to a direct message conversation.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Direct message channel to fetch thread from. e.g. C1234567890.
     * @param threadTs Required. Unique identifier of a thread's parent message. e.g. 1234567890.123456.
     *
     * @return If successful, the command returns an [ImRepliesWrapper] object.
     */
    @POST("im.replies")
    @FormUrlEncoded
    fun replies(@Field("token") token: String,
                @Field("channel") channelId: String,
                @Field("thread_ts") threadTs: String): Observable<ImRepliesWrapper>

}