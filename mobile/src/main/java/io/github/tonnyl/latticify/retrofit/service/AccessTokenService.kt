package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.AccessToken
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 23/09/2017.
 */
interface AccessTokenService {

    @POST("oauth.access")
    @FormUrlEncoded
    fun getAccessToken(@Field("client_id") clientId: String,
                       @Field("client_secret") clientSecret: String,
                       @Field("code") code: String,
                       @Field("redirect_uri") redirectUri: String): Observable<AccessToken>

}