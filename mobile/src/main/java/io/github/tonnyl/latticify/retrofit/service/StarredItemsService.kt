package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.data.StarItemsWrapper
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 08/10/2017.
 */
interface StarredItemsService {

    /**
     * Adds a star to an item.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Optional. Channel to add star to, or channel where the message to add star to was posted
     * (used with [timestamp]). e.g. C1234567890.
     * @param file Optional. File to add star to. e.g. F1234567890.
     * @param fileCommentId Optional. File comment to add star to. e.g. Fc1234567890.
     * @param timestamp Optional. Timestamp of the message to add star to. e.g. 1234567890.123456.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("stars.add")
    @FormUrlEncoded
    fun add(@Field("token") token: String,
            @Field("channel") channelId: String = "",
            @Field("file") file: String = "",
            @Field("file_comment") fileCommentId: String = "",
            @Field("timestamp") timestamp: String): Observable<ResponseWrapper>

    /**
     * Lists the items starred by the authed user.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param count Optional, default=100. Number of items to return per page.
     * @param page Optional, default=1. Page number of results to return.
     *
     * @return If successful, the command returns a list of starred items followed by pagination information.
     */
    @POST("stars.list")
    @FormUrlEncoded
    fun list(@Field("token") token: String,
             @Field("count") count: Int = Api.LIMIT,
             @Field("page") page: Int): Observable<StarItemsWrapper>

    /**
     * Removes a star from an item.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Optional. [io.github.tonnyl.latticify.data.Channel] to remove star from,
     * or [io.github.tonnyl.latticify.data.Channel] where the message to remove star from was posted (used with [timestamp]). e.g. C1234567890.
     * @param fileId Optional. File to remove star from. e.g. F1234567890.
     * @param fileCommentId Optional. File comment to remove star from. e.g. Fc1234567890.
     * @param timestamp Optional. Timestamp of the message to remove star from. e.g. 1234567890.123456.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("stars.remove")
    @FormUrlEncoded
    fun remove(@Field("token") token: String,
               @Field("channel") channelId: String,
               @Field("file") fileId: String,
               @Field("file_comment") fileCommentId: String,
               @Field("timestamp") timestamp: String): Observable<ResponseWrapper>
}