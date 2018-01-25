package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 08/10/2017.
 */
interface UsersDataSource {

    fun deletePhoto(): Observable<ResponseWrapper>

    fun getPresence(userId: String): Observable<UsersPresenceInfo>

    fun identity(): Observable<UserIdentityWrapper>

    fun info(userId: String,
             includeLocale: Boolean = true): Observable<UserWrapper>

    fun list(cursor: String = "",
             includeLocale: Boolean = true,
             limit: Int = Api.LIMIT,
             presence: Boolean = true): Observable<UserListWrapper>

    fun lookupByEmail(token: String,
                      email: String): Observable<UserWrapper>

    fun setActive(): Observable<ResponseWrapper>

    fun setPresence(presence: String): Observable<ResponseWrapper>


}