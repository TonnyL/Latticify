package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.UsersProfileWrapper
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 08/10/2017.
 */
interface UsersProfileDataSource {

    fun getUsersProfile(includeLabels: Boolean = true,
                        userId: String = ""): Observable<UsersProfileWrapper>

    fun setUsersProfile(name: String? = null,
                        profile: String? = null,
                        userId: String? = null,
                        value: String? = null): Observable<UsersProfileWrapper>

}