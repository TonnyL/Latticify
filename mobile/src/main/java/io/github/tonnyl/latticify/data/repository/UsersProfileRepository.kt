package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.UsersProfileWrapper
import io.github.tonnyl.latticify.data.datasource.UsersProfileDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.UsersProfileService
import io.github.tonnyl.latticify.util.AccessTokenManager
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 09/10/2017.
 */
object UsersProfileRepository : UsersProfileDataSource {

    private val mUsersProfileService = RetrofitClient.createService(UsersProfileService::class.java, AccessTokenManager.getAccessToken())
    private val mToken = AccessTokenManager.getAccessToken().accessToken

    override fun getUsersProfile(includeLabels: Boolean, userId: String): Observable<UsersProfileWrapper> =
            mUsersProfileService.getUsersProfile(mToken, includeLabels, userId)

    override fun setUsersProfile(name: String, profile: String, userId: String, value: String): Observable<UsersProfileWrapper> =
            mUsersProfileService.setUsersProfile(mToken, name, profile, userId, value)

}