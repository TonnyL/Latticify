package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.UsersProfileWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 08/10/2017.
 */
interface UsersProfileService {

    /**
     * Retrieves a user's profile information.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param includeLabels Optional, default=false. Include labels for each ID in custom profile fields.
     * @param userId Optional. User to retrieve profile info for. e.g. W1234567890.
     *
     * @return If successful, the command returns a [UsersProfileWrapper] object.
     */
    @POST("users.profile.get")
    @FormUrlEncoded
    fun getUsersProfile(@Field("token") token: String,
                        @Field("include_labels") includeLabels: Boolean = true,
                        @Field("user") userId: String): Observable<UsersProfileWrapper>

    /**
     * Set the profile information for a user, including name, email, current status, and other attributes.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param name Optional. Name of a single key to set. Usable only if profile is not passed. e.g. first_name.
     * @param profile Optional. Collection of key:value pairs presented as a URL-encoded JSON hash. e.g. { first_name: "John", ... }.
     * @param userId Optional. ID of user to change. This argument may only be specified by team admins on paid teams. e.g. W1234567890.
     * @param value Optional. Value to set a single key to. Usable only if profile is not passed. e.g. John
     *
     * @return If successful, the command returns a [UsersProfileWrapper] object.
     */
    @POST("users.profile.set")
    @FormUrlEncoded
    fun setUsersProfile(@Field("token") token: String,
                        @Field("name") name: String? = null,
                        @Field("profile") profile: String? = null,
                        @Field("user") userId: String? = null,
                        @Field("value") value: String? = null): Observable<UsersProfileWrapper>

}