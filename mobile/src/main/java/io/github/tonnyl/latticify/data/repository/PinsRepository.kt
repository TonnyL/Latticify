package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.PinnedItemsWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.data.datasource.PinsDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.PinsService
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 12/10/2017.
 */
class PinsRepository : PinsDataSource {

    private val mPinsService = RetrofitClient.createService(PinsService::class.java)
    private val mToken = RetrofitClient.mToken

    override fun add(channelId: String, fileId: String, fileComment: String, timestamp: String): Observable<ResponseWrapper> =
            mPinsService.add(mToken, channelId, fileComment, timestamp)

    override fun list(channelId: String): Observable<PinnedItemsWrapper> =
            mPinsService.list(mToken, channelId)

    override fun remove(channelId: String, fileId: String, fileComment: String, timestamp: String): Observable<ResponseWrapper> =
            mPinsService.remove(mToken, channelId, fileId, fileComment, timestamp)

}