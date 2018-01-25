package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.DndInfoWrapper
import io.github.tonnyl.latticify.data.DndTeamInfoWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 14/10/2017.
 */
interface DndDataSource {

    fun endDnd(): Observable<ResponseWrapper>

    fun endSnooze(): Observable<ResponseWrapper>

    fun info(userId: String): Observable<DndInfoWrapper>

    fun setSnooze(numMinutes: Int): Observable<DndInfoWrapper>

    fun teamInfo(userIds: Array<String>): Observable<DndTeamInfoWrapper>

}