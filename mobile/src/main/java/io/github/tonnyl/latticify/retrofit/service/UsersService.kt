package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.data.UserListWrapper
import io.github.tonnyl.latticify.data.UserWrapper
import io.github.tonnyl.latticify.data.UsersPresenceInfo
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 08/10/2017.
 */
interface UsersService {

    /**
     * Delete the user profile photo. It will clear whatever image is currently set.
     *
     * @param token Required. Authentication token bearing required scopes.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("users.deletePhoto")
    @FormUrlEncoded
    fun deletePhoto(@Field("token") token: String): Observable<ResponseWrapper>

    /**
     * Gets user presence information.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param userId Required. User to get presence info on. Defaults to the authed user. e.g. W1234567890.
     *
     * @return When requesting information for a different user, this method just returns the current presence (either active or away).
     * If you are requesting presence information for the authenticated user, this method returns the current presence,
     * along with details on how it was calculated
     */
    @POST("users.getPresence")
    @FormUrlEncoded
    fun getPresence(@Field("token") token: String,
                    @Field("user") userId: String): Observable<UsersPresenceInfo>

    /**
     * Gets information about a user.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param userId Required. User to get info on. e.g. W1234567890.
     * @param includeLocale Optional. Set this to true to receive the locale for this user. Defaults to false
     *
     * @return If successful, the command returns a [UserWrapper] object.
     */
    @POST("users.info")
    @FormUrlEncoded
    fun info(@Field("token") token: String,
             @Field("user") userId: String,
             @Field("include_locale") includeLocale: Boolean = false): Observable<UserWrapper>

    /**
     * Lists all users in a Slack team.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param cursor Optional. Paginate through collections of data by setting the cursor parameter to a [io.github.tonnyl.latticify.data.ResponseMetaData.nextCursor]
     * attribute returned by a previous request's [io.github.tonnyl.latticify.data.ResponseMetaData]. Default value fetches the first "page" of the collection.
     * @param includeLocale Optional. Set this to true to receive the locale for users. Defaults to false.
     * @param limit Optional, default=0. The maximum number of items to return. Fewer than the requested number of items may be returned,
     * even if the end of the users list hasn't been reached.
     * @param presence Optional. Whether to include presence data in the output. Setting this to false improves performance, especially with large teams.
     *
     * @return If successful, the command returns a list of [io.github.tonnyl.latticify.data.User] objects ([UserListWrapper]), in no particular order.
     */
    @POST("users.list")
    @FormUrlEncoded
    fun list(@Field("token") token: String,
             @Field("cursor") cursor: String,
             @Field("include_locale") includeLocale: Boolean = false,
             @Field("limit") limit: Int = Api.LIMIT,
             @Field("presence") presence: Boolean = true): Observable<UserListWrapper>

    /**
     * Marks a user as active.
     *
     * @param token Required. Authentication token bearing required scopes.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("users.setActive")
    @FormUrlEncoded
    fun setActive(@Field("token") token: String): Observable<ResponseWrapper>

    /**
     * Manually sets user presence.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param presence Required. Either auto or away.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("users.setPresence")
    @FormUrlEncoded
    fun setPresence(@Field("token") token: String,
                    @Field("presence") presence: String): Observable<ResponseWrapper>

}