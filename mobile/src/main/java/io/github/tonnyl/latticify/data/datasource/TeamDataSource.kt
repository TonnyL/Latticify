package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.TeamIntegrationLogsWrapper
import io.github.tonnyl.latticify.data.TeamLogsWrapper
import io.github.tonnyl.latticify.data.TeamWrapper
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 08/10/2017.
 */
interface TeamDataSource {

    fun accessLogs(before: String = "0",
                   count: Int = 20,
                   page: Int = 1): Observable<TeamLogsWrapper>

    fun info(): Observable<TeamWrapper>

    fun integrationLogs(appId: String = "",
                        changeType: String = "",
                        count: Int = Api.LIMIT,
                        page: Int = 1,
                        serviceId: String = "",
                        userId: String): Observable<TeamIntegrationLogsWrapper>

}