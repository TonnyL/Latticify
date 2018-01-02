package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.TeamIntegrationLogsWrapper
import io.github.tonnyl.latticify.data.TeamLogsWrapper
import io.github.tonnyl.latticify.data.TeamWrapper
import io.github.tonnyl.latticify.data.datasource.TeamDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.TeamService
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 08/10/2017.
 */
object TeamRepository : TeamDataSource {

    private val mTeamService = RetrofitClient.createService(TeamService::class.java)
    private val mToken = RetrofitClient.mToken

    override fun accessLogs(before: String,
                            count: Int,
                            page: Int): Observable<TeamLogsWrapper> =
            mTeamService.accessLogs(mToken, before, count, page)

    override fun info(): Observable<TeamWrapper> =
            mTeamService.info(mToken)

    override fun integrationLogs(appId: String, changeType: String, count: Int, page: Int, serviceId: String, userId: String): Observable<TeamIntegrationLogsWrapper> =
            mTeamService.integrationLogs(mToken, appId, changeType, count, page, serviceId, userId)

}