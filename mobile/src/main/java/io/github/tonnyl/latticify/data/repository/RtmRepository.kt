package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.RtmResponseMessageWrapper
import io.github.tonnyl.latticify.data.datasource.RtmDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.RtmService
import io.github.tonnyl.latticify.util.AccessTokenManager
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 10/10/2017.
 */
class RtmRepository : RtmDataSource {

    private val mRtmService = RetrofitClient.createService(RtmService::class.java, AccessTokenManager.getAccessToken())
    private val mToken = AccessTokenManager.getAccessToken().accessToken

    override fun connect(batchPresenceAware: Int): Observable<RtmResponseMessageWrapper> =
            mRtmService.connect(mToken, batchPresenceAware)

}