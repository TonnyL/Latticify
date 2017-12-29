package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.SearchFilesWrapper
import io.github.tonnyl.latticify.data.SearchMessagesWrapper
import io.github.tonnyl.latticify.data.SearchedAllWrapper
import io.github.tonnyl.latticify.data.datasource.SearchDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.SearchService
import io.github.tonnyl.latticify.util.AccessTokenManager
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 13/10/2017.
 */
class SearchRepository : SearchDataSource {

    private val mSearchService = RetrofitClient.createService(SearchService::class.java, AccessTokenManager.getAccessToken())
    private val mToken = AccessTokenManager.getAccessToken().accessToken

    override fun all(query: String, count: Int, highlight: Boolean, page: Int, sort: String, sortDir: String): Observable<SearchedAllWrapper> =
            mSearchService.all(mToken, query, count, highlight, page, sort, sortDir)

    override fun files(query: String, count: Int, highlight: Boolean, page: Int, sort: String, sortDir: String): Observable<SearchFilesWrapper> =
            mSearchService.files(mToken, query, count, highlight, page, sort, sortDir)

    override fun messages(query: String, count: Int, highlight: Boolean, page: Int, sort: String, sortDir: String): Observable<SearchMessagesWrapper> =
            mSearchService.messages(mToken, query, count, highlight, page, sort, sortDir)

}