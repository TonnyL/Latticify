package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.PinnedItemsWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 09/10/2017.
 */
interface PinsService {

    /**
     * Pins an item to a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to pin the item in. e.g. C1234567890.
     * @param fileId Optional. File to pin. e.g. F1234567890.
     * @param fileComment Optional. File comment to pin. e.g. Fc1234567890.
     * @param timestamp Optional. Timestamp of the message to pin. e.g. 1234567890.123456.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     * After making this call the pin is saved to the database and a pin_added event is broadcast via the RTM API.
     */
    @POST("pins.add")
    @FormUrlEncoded
    fun add(@Field("token") token: String,
            @Field("channel") channelId: String,
            @Field("file") fileId: String = "",
            @Field("file_comment") fileComment: String = "",
            @Field("timestamp") timestamp: String = ""): Observable<ResponseWrapper>

    /**
     * Lists items pinned to a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel to get pinned items for. e.g. C1234567890.
     *
     * @return If successful, the command returns a [PinnedItemsWrapper] object.
     */
    @POST("pins.list")
    @FormUrlEncoded
    fun list(@Field("token") token: String,
             @Field("channel") channelId: String): Observable<PinnedItemsWrapper>

    /**
     * Un-pins an item from a channel.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Required. Channel where the item is pinned to. e.g. C1234567890.
     * @param fileId Optional. File to un-pin. e.g. F1234567890.
     * @param fileComment Optional. File comment to un-pin. e.g. Fc1234567890.
     * @param timestamp Optional. Timestamp of the message to un-pin. 1234567890.123456.
     */
    @POST("pins.remove")
    @FormUrlEncoded
    fun remove(@Field("token") token: String,
               @Field("channel") channelId: String,
               @Field("file") fileId: String = "",
               @Field("file_comment") fileComment: String = "",
               @Field("timestamp") timestamp: String = ""): Observable<ResponseWrapper>

}