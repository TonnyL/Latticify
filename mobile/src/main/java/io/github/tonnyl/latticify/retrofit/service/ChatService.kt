package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by lizhaotailang on 06/10/2017.
 */
interface ChatService {

    /**
     * Deletes a message from a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel containing the message to be deleted. e.g. C1234567890.
     * @param ts Required. Timestamp of the message to be deleted. e.g. 1405894322.002768.
     * @param asUser Optional. Pass true to delete the message as the authenticated user. Bot users in this context are considered authed users.
     *
     * @return If successful, the command returns a [ChatMessageWrapper] object.
     */
    @POST("chat.delete")
    @FormUrlEncoded
    fun delete(@Field("token") token: String,
               @Field("channel") channelId: String,
               @Field("ts") ts: String,
               @Field("as_user") asUser: Boolean = true): Observable<ChatMessageWrapper>

    /**
     * Retrieve a permalink URL for a specific extant message
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. The ID of the conversation or channel containing the message.
     * @param messageTs Required. A message's ts value, uniquely identifying it within a channel.
     *
     * @return If successful, the command returns a [MessagePermalinkWrapper] object.
     */
    @GET("chat.getPermalink")
    fun getPermalink(@Query("token") token: String,
                     @Query("channel") channelId: String,
                     @Query("message_ts") messageTs: String): Observable<MessagePermalinkWrapper>

    /**
     * Share a me message into a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to send message to. Can be a public channel, private group or IM channel.
     * Can be an encoded ID, or a name. e.g. C1234567890.
     * @param text Required. Text of the message to send. e.g. Hello World.
     *
     * @return If successful, the command returns a [ChatMessageWrapper] object.
     */
    @POST("chat.meMessage")
    @FormUrlEncoded
    fun meMessage(@Field("token") token: String,
                  @Field("channel") channelId: String,
                  @Field("text") text: String): Observable<ChatMessageWrapper>

    /**
     * Sends an ephemeral message to a user in a channel.
     * This method posts an ephemeral message, which is visible only to the assigned user in a specific public channel,
     * private channel, or private conversation.
     *
     * Ephemeral message delivery is not guaranteed — the user must be currently active in Slack and a member of the specified channel.
     * By nature, ephemeral messages do not persist across reloads, desktop and mobile apps, or sessions.
     *
     * Use ephemeral messages to send users context-sensitive messages, relevant to the channel they're detectably participating in.
     * Avoid sending unexpected or unsolicited ephemeral messages.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel, private group, or IM channel to send message to. Can be an encoded ID, or a name. e.g. C1234567890.
     * @param text Required. Text of the message to send.  This field is usually required, unless you're providing only attachments instead. e.g. Hello world.
     * @param user Required. id of the user who will receive the ephemeral message. The user should be in the channel specified by the channel argument. e.g. U0BPQUNTA.
     * @param asUser Optional. Pass true to post the message as the authenticated bot. Defaults to false.
     * @param attachments Optional. A JSON-based array of structured attachments, presented as a URL-encoded string.
     * e.g. [{"pretext": "pre-hello", "text": "text-world"}].
     * @param linkNames Optional. Find and link channel names and usernames.
     * @param parse Optional. Change how messages are treated. Defaults to none.
     *
     * @return If successful, the command returns an [EphemeralChatMessageWrapper] object.
     */
    @POST("chat.postEphemeral")
    @FormUrlEncoded
    fun postEphemeral(@Field("token") token: String,
                      @Field("channel") channelId: String,
                      @Field("text") text: String,
                      @Field("user") user: String,
                      @Field("as_user") asUser: Boolean = false,
                      @Field("attachments") attachments: String = "",
                      @Field("link_names") linkNames: Boolean = true,
                      @Field("parse") parse: String = "none"): Observable<EphemeralChatMessageWrapper>

    /**
     * Sends a message to a public channel, private channel, or direct message/IM channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel, private group, or IM channel to send message to. Can be an encoded ID, or a name. e.g. C1234567890.
     * @param text Required. Text of the message to send. This field is usually required, unless you're providing only attachments instead. e.g. Hello World.
     * @param asUser Optional. Pass true to post the message as the authenticated user, instead of as a bot. Defaults to false.
     * @param attachments Optional. A JSON-based array of structured attachments, presented as a URL-encoded string. e.g. [{"pretext": "pre-hello", "text": "text-world"}].
     * @param iconEmoji Optional. Emoji to use as the icon for this message. Overrides icon_url. Must be used in conjunction with as_user set to false,
     * otherwise ignored. e.g. :chart_with_upwards_trend:.
     * @param iconUrl Optional. URL to an image to use as the icon for this message. Must be used in conjunction with as_user set to false, otherwise ignored.
     * e.g. http://lorempixel.com/48/48.
     * @param linkNames Optional. Find and link channel names and usernames.
     * @param parse Optional. Change how messages are treated. Defaults to none.
     * @param replyBroadcast Optional. Used in conjunction with thread_ts and indicates whether reply should be made visible to everyone in the channel or conversation.
     * Defaults to false.
     * @param threadTs Optional. Provide another message's ts value to make this message a reply. Avoid using a reply's ts value; use its parent instead.
     * @param unfurlLinks Optional. Pass true to enable unfurling of primarily text-based content.
     * @param unfurlMedia Optional. Pass false to disable unfurling of media content.
     * @param username Optional. Set your bot's user name. Must be used in conjunction with as_user set to false, otherwise ignored. e.g. My Bot.
     *
     * @return If successful, the command returns a [PostMessageWrapper] object.
     * The response includes the "timestamp ID" (ts) and the channel-like thing where the message was posted.
     * It also includes the complete message object, as parsed by our servers.
     * This may differ from the provided arguments as our servers sanitize links, attachments, and other properties.
     * Your message may mutate.
     */
    @POST("chat.postMessage")
    @FormUrlEncoded
    fun postMessage(@Field("token") token: String,
                    @Field("channel") channelId: String,
                    @Field("text") text: String,
                    @Field("as_user") asUser: Boolean = true,
                    @Field("attachments") attachments: String = "",
                    @Field("icon_emoji") iconEmoji: String = "",
                    @Field("icon_url") iconUrl: String = "",
                    @Field("link_names") linkNames: Boolean = true,
                    @Field("parse") parse: String = "none",
                    @Field("reply_broadcast") replyBroadcast: Boolean = false,
                    @Field("thread_ts") threadTs: String = "",
                    @Field("unfurl_links") unfurlLinks: Boolean = true,
                    @Field("unfurl_media") unfurlMedia: Boolean = false,
                    @Field("username") username: String? = null): Observable<PostMessageWrapper>

    /**
     * Provide custom unfurl behavior for user-posted URLs
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel, private group, or IM channel to send message to. Can be an encoded ID, or a name. e.g. C1234567890.
     * @param ts Required. Timestamp of the message to add unfurl behavior to.
     * @param unfurls Required. URL-encoded JSON map with keys set to URLs featured in the the message, pointing to their unfurl message attachments.
     * @param userAuthMessage Optional. Provide a simply-formatted string to send as an ephemeral message to the user as invitation to authenticate further and enable full unfurling behavior.
     * @param userAuthRequired Optional, default=0. Set to true or 1 to indicate the user must install your Slack app to trigger unfurls for this domain.
     * @param userAuthUrl Optional. Send users to this custom URL where they will complete authentication in your app to fully trigger unfurling. Value should be properly URL-encoded.
     *
     * @return If successful, the command returns an [ResponseWrapper] object.
     */
    @POST("chat.unfurl")
    @FormUrlEncoded
    fun unfurl(@Field("token") token: String,
               @Field("channel") channelId: String,
               @Field("ts") ts: String,
               @Field("unfurls") unfurls: String,
               @Field("user_auth_message") userAuthMessage: String = "",
               @Field("user_auth_required") userAuthRequired: Int = 0,
               @Field("user_auth_url") userAuthUrl: String = ""): Observable<ResponseWrapper>

    /**
     * Updates a message in a channel. Though related to [postMessage], some parameters of [update] are handled differently.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel containing the message to be updated. e.g. C1234567890.
     * @param text Required. New text for the message. e.g. Hello World.
     * @param ts Required. Timestamp of the message to be updated. e.g. 1405894322.002768.
     * @param asUser Optional. Pass true to update the message as the authenticated user. Bot users in this context are considered authenticated users.
     * @param attachments Optional. A JSON-based array of structured attachments, presented as a URL-encoded string. e.g. [{"pretext": "pre-hello", "text": "text-world"}].
     * @param linkNames Optional. Find and link channel names and usernames. Defaults to none. This parameter should be used in conjunction with parse.
     * To set link_names to 1, specify a parse mode of full.
     * @param parse Optional. Change how messages are treated. Defaults to client, unlike chat.postMessage.
     *
     * @return If successful, the command returns a [ChatMessageWrapper] object.
     */
    @POST("chat.update")
    @FormUrlEncoded
    fun update(@Field("token") token: String,
               @Field("channel") channelId: String,
               @Field("text") text: String,
               @Field("ts") ts: String,
               @Field("as_user") asUser: Boolean = true,
               @Field("attachments") attachments: String = "",
               @Field("link_names") linkNames: Boolean = true,
               @Field("parse") parse: String = ""): Observable<ChatMessageWrapper>

}