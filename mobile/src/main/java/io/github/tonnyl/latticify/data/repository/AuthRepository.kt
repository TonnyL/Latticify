package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.AuthRevokeWrapper
import io.github.tonnyl.latticify.data.AuthTestWrapper
import io.github.tonnyl.latticify.data.datasource.AuthDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.AuthService
import io.github.tonnyl.latticify.util.AccessTokenManager
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 10/10/2017.
 */
class AuthRepository : AuthDataSource {

    private val mAuthService = RetrofitClient.createService(AuthService::class.java, AccessTokenManager.getAccessToken())
    private val mToken = AccessTokenManager.getAccessToken().accessToken

    override fun revoke(test: Int): Observable<AuthRevokeWrapper> =
            mAuthService.revoke(mToken, test)

    override fun test(): Observable<AuthTestWrapper> =
            mAuthService.test(mToken)

}