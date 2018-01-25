package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.data.datasource.UsersDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.UsersService
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 11/10/2017.
 */
object UsersRepository : UsersDataSource {

    private val mUsersService = RetrofitClient.createService(UsersService::class.java)
    private val mToken = RetrofitClient.mToken

    override fun deletePhoto(): Observable<ResponseWrapper> {
        return mUsersService.deletePhoto(mToken)
    }

    override fun getPresence(userId: String): Observable<UsersPresenceInfo> {
        return mUsersService.getPresence(mToken, userId)
    }

    override fun identity(): Observable<UserIdentityWrapper> {
        return mUsersService.identity(mToken)
    }

    override fun info(userId: String, includeLocale: Boolean): Observable<UserWrapper> {
        return mUsersService.info(mToken, userId, includeLocale)
    }

    override fun list(cursor: String, includeLocale: Boolean, limit: Int, presence: Boolean): Observable<UserListWrapper> {
        return mUsersService.list(mToken, cursor, includeLocale, limit, presence)
    }

    override fun lookupByEmail(token: String, email: String): Observable<UserWrapper> {
        return mUsersService.lookupByEmail(mToken, email)
    }

    override fun setActive(): Observable<ResponseWrapper> {
        return mUsersService.setActive(mToken)
    }

    override fun setPresence(presence: String): Observable<ResponseWrapper> {
        return mUsersService.setPresence(mToken, presence)
    }

}