package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.AuthRevokeWrapper
import io.github.tonnyl.latticify.data.AuthTestWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 10/10/2017.
 */
interface AuthService {

    /**
     * Revokes a token.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param test Optional. Setting this parameter to 1 triggers a testing mode where the specified token will not actually be revoked.
     *
     * @return If successful, the command returns an [AuthRevokeWrapper] object.
     */
    @POST("auth.revoke")
    @FormUrlEncoded
    fun revoke(@Field("token") token: String,
               @Field("test") test: Int = 1): Observable<AuthRevokeWrapper>

    /**
     * Checks authentication & identity.
     *
     * @param token Required. Authentication token bearing required scopes.
     *
     * @return If successful, the command returns an [AuthTestWrapper] object.
     * When working against a team within an Enterprise Grid, you'll also find their enterprise_id here.
     */
    @POST("auth.test")
    @FormUrlEncoded
    fun test(@Field("token") token: String): Observable<AuthTestWrapper>

}