package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.DndInfoWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.data.datasource.DndDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.DndService
import io.github.tonnyl.latticify.util.AccessTokenManager
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 14/10/2017.
 */
class DndRespository : DndDataSource {

    private val mDndService = RetrofitClient.createService(DndService::class.java, AccessTokenManager.getAccessToken())
    private val mToken = AccessTokenManager.getAccessToken().accessToken

    override fun endDnd(): Observable<ResponseWrapper> =
            mDndService.endDnd(mToken)

    override fun endSnooze(): Observable<ResponseWrapper> =
            mDndService.endSnooze(mToken)

    override fun info(userId: String): Observable<DndInfoWrapper> =
            mDndService.info(mToken, userId)

    override fun setSnooze(numMinutes: Int): Observable<DndInfoWrapper> =
            mDndService.setSnooze(mToken, numMinutes)

}