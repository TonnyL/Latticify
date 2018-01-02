package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.RtmResponseMessageWrapper
import io.github.tonnyl.latticify.data.datasource.RtmDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.RtmService
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 10/10/2017.
 */
object RtmRepository : RtmDataSource {

    private val mRtmService = RetrofitClient.createService(RtmService::class.java)
    private val mToken = RetrofitClient.mToken

    override fun connect(batchPresenceAware: Int): Observable<RtmResponseMessageWrapper> =
            mRtmService.connect(mToken, batchPresenceAware)

}