package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.AccessToken
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 23/09/2017.
 */
interface AccessTokenDataSource {

    fun getAccessToken(code: String): Observable<AccessToken>

}