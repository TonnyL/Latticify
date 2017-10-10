package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.AuthRevokeWrapper
import io.github.tonnyl.latticify.data.AuthTestWrapper
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 10/10/2017.
 */
interface AuthDataSource {

    fun revoke(test: Int = 1): Observable<AuthRevokeWrapper>

    fun test(): Observable<AuthTestWrapper>

}