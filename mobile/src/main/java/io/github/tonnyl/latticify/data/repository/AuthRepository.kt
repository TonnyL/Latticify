package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.AuthRevokeWrapper
import io.github.tonnyl.latticify.data.AuthTestWrapper
import io.github.tonnyl.latticify.data.datasource.AuthDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.AuthService
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 10/10/2017.
 */
class AuthRepository : AuthDataSource {

    private val mAuthService = RetrofitClient.createService(AuthService::class.java)
    private val mToken = RetrofitClient.mToken

    override fun revoke(test: Int): Observable<AuthRevokeWrapper> {
        return mAuthService.revoke(mToken, test)
    }

    override fun test(): Observable<AuthTestWrapper> {
        return mAuthService.test(mToken)
    }

}