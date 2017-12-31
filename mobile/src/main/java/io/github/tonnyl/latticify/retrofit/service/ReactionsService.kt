package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 31/12/2017.
 */
interface ReactionsService {

    /**
     * Adds a reaction to an item.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param name Required. Reaction (emoji) name. e.g. thumbsup.
     * @param channelId  Optional. Channel where the message to add reaction to was posted. e.g. C1234567890.
     * @param fileId Optional. File to add reaction to. e.g. F1234567890.
     * @param fileCommentId Optional. File comment to add reaction to. e.g. Fc1234567890.
     * @param timestamp Optional. Timestamp of the message to add reaction to. e.g. 1234567890.123456.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("reactions.add")
    @FormUrlEncoded
    fun add(@Field("token") token: String,
            @Field("name") name: String,
            @Field("channel") channelId: String,
            @Field("file") fileId: String,
            @Field("file_comment") fileCommentId: String,
            @Field("timestamp") timestamp: Long): Observable<ResponseWrapper>

    /**
     * Gets reactions for an item.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Optional. Channel where the message to get reactions for was posted. e.g. C1234567890.
     * @param fileId Optional. File to get reactions for. e.g. F1234567890.
     * @param fileCommentId Optional. File comment to get reactions for. e.g. Fc1234567890.
     * @param full Optional. If true always return the complete reaction list.
     * @param timestamp Optional. Timestamp of the message to get reactions for. e.g. 1234567890.123456.
     *
     * @return
     */
    @POST("reactions.get")
    @FormUrlEncoded
    fun get(@Field("token") token: String,
            @Field("channel") channelId: String = "",
            @Field("file") fileId: String = "",
            @Field("file_comment") fileCommentId: String = "",
            @Field("full") full: Boolean = true,
            @Field("timestamp") timestamp: Long = 0)

    /**
     * Lists reactions made by a user.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param count Optional, default=[Api.LIMIT]. Number of items to return per page.
     * @param full Optional. If true always return the complete reaction list.
     * @param page Optional, default=1. Page number of results to return.
     * @param userId Optional. Show reactions made by this user. Defaults to the authed user. e.g. W1234567890.
     *
     * @return
     */
    @POST("reactions.list")
    @FormUrlEncoded
    fun list(@Field("token") token: String,
             @Field("count") count: Int = Api.LIMIT,
             @Field("full") full: Boolean = true,
             @Field("page") page: Int = 1,
             @Field("user") userId: String = "")

    /**
     * Removes a reaction from an item.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param name    Required. Reaction (emoji) name. e.g. thumbsup.
     * @param channelId Optional. Channel where the message to remove reaction from was posted. e.g. C1234567890.
     * @param fileId Optional. File to remove reaction from. e.g. F1234567890.
     * @param fileCommentId Optional. File comment to remove reaction from. e.g Fc1234567890.
     * @param timestamp Optional. Timestamp of the message to remove reaction from. e.g. 1234567890.123456.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("reactions.remove")
    @FormUrlEncoded
    fun remove(@Field("token") token: String,
               @Field("name") name: String,
               @Field("channel") channelId: String,
               @Field("file") fileId: String,
               @Field("file_comment") fileCommentId: String,
               @Field("timestamp") timestamp: Long): Observable<ResponseWrapper>

}