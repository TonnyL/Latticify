package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.data.StarItemsWrapper
import io.github.tonnyl.latticify.data.datasource.StarredItemsDataSource
import io.github.tonnyl.latticify.retrofit.Api
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.StarredItemsService
import io.github.tonnyl.latticify.util.AccessTokenManager
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 09/10/2017.
 */
object StarredItemsRepository : StarredItemsDataSource {

    private val mStarredItemsService = RetrofitClient.createService(StarredItemsService::class.java, AccessTokenManager.getAccessToken())
    private val mToken = AccessTokenManager.getAccessToken().accessToken

    override fun add(channelId: String, file: String, fileCommentId: String, timestamp: String): Observable<ResponseWrapper> =
            mStarredItemsService.add(mToken, channelId, file, fileCommentId, timestamp)

    override fun list(count: Int, page: Int): Observable<StarItemsWrapper> =
            mStarredItemsService.list(mToken, count, page)

    override fun remove(channelId: String, fileId: String, fileCommentId: String, timestamp: String): Observable<ResponseWrapper> =
            mStarredItemsService.remove(mToken, channelId, fileId, fileCommentId, timestamp)

}