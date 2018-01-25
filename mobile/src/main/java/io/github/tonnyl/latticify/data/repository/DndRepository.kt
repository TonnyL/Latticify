package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.DndInfoWrapper
import io.github.tonnyl.latticify.data.DndTeamInfoWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.data.datasource.DndDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.DndService
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 14/10/2017.
 */
class DndRepository : DndDataSource {

    private val mDndService = RetrofitClient.createService(DndService::class.java)
    private val mToken = RetrofitClient.mToken

    override fun endDnd(): Observable<ResponseWrapper> {
        return mDndService.endDnd(mToken)
    }

    override fun endSnooze(): Observable<ResponseWrapper> {
        return mDndService.endSnooze(mToken)
    }

    override fun info(userId: String): Observable<DndInfoWrapper> {
        return mDndService.info(mToken, userId)
    }

    override fun setSnooze(numMinutes: Int): Observable<DndInfoWrapper> {
        return mDndService.setSnooze(mToken, numMinutes)
    }

    override fun teamInfo(userIds: Array<String>): Observable<DndTeamInfoWrapper> {
        return mDndService.teamInfo(mToken, userIds)
    }

}