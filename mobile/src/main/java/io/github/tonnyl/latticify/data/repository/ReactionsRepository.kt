package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.data.datasource.ReactionsDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.ReactionsService
import io.github.tonnyl.latticify.util.AccessTokenManager
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 31/12/2017.
 */
class ReactionsRepository : ReactionsDataSource {

    private val mReactionsService = RetrofitClient.createService(ReactionsService::class.java, AccessTokenManager.getAccessToken())
    private val mToken = AccessTokenManager.getAccessToken().accessToken

    override fun add(token: String, name: String, channelId: String, fileId: String, fileCommentId: String, timestamp: Long): Observable<ResponseWrapper> {
        return mReactionsService.add(mToken, name, channelId, fileId, fileCommentId, timestamp)
    }

    override fun remove(token: String, name: String, channelId: String, fileId: String, fileCommentId: String, timestamp: Long): Observable<ResponseWrapper> {
        return mReactionsService.remove(mToken, name, channelId, fileId, fileCommentId, timestamp)
    }

}