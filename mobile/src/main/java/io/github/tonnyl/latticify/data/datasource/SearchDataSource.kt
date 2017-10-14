package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.SearchAllWrapper
import io.github.tonnyl.latticify.data.SearchFilesWrapper
import io.github.tonnyl.latticify.data.SearchMessagesWrapper
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 13/10/2017.
 */
interface SearchDataSource {

    fun all(query: String,
            count: Int = Api.LIMIT,
            highlight: Boolean = true,
            page: Int = 1,
            sort: String = "score",
            sortDir: String = "desc"): Observable<SearchAllWrapper>

    fun files(query: String,
              count: Int = Api.LIMIT,
              highlight: Boolean = true,
              page: Int = 1,
              sort: String = "score",
              sortDir: String = "desc"): Observable<SearchFilesWrapper>

    fun messages(query: String,
                 count: Int = Api.LIMIT,
                 highlight: Boolean = true,
                 page: Int = 1,
                 sort: String = "score",
                 sortDir: String = "desc"): Observable<SearchMessagesWrapper>

}