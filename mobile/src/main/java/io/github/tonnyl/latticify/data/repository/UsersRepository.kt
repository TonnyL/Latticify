package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.data.UserListWrapper
import io.github.tonnyl.latticify.data.UserWrapper
import io.github.tonnyl.latticify.data.UsersPresenceInfo
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

    override fun deletePhoto(): Observable<ResponseWrapper> =
            mUsersService.deletePhoto(mToken)

    override fun getPresence(userId: String): Observable<UsersPresenceInfo> =
            mUsersService.getPresence(mToken, userId)

    override fun info(userId: String, includeLocale: Boolean): Observable<UserWrapper> =
            mUsersService.info(mToken, userId, includeLocale)

    override fun list(cursor: String, includeLocale: Boolean, limit: Int, presence: Boolean): Observable<UserListWrapper> =
            mUsersService.list(mToken, cursor, includeLocale, limit, presence)

    override fun setActive(): Observable<ResponseWrapper> =
            mUsersService.setActive(mToken)

    override fun setPresence(presence: String): Observable<ResponseWrapper> =
            mUsersService.setPresence(mToken, presence)

}