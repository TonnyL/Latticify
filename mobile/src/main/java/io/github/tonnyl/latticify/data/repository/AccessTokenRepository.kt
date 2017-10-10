package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.AccessToken
import io.github.tonnyl.latticify.data.datasource.AccessTokenDataSource
import io.github.tonnyl.latticify.retrofit.service.AccessTokenService
import io.github.tonnyl.latticify.retrofit.Api
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 23/09/2017.
 */
object AccessTokenRepository : AccessTokenDataSource {

    override fun getAccessToken(code: String): Observable<AccessToken> {
        return RetrofitClient.createService(AccessTokenService::class.java, null)
                .getAccessToken(Api.CLIENT_ID,
                        Api.CLIENT_SECRET,
                        code,
                        Api.SLACK_AUTHORIZE_CALLBACK_URI)
    }

}