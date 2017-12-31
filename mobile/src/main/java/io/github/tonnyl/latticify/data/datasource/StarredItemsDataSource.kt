package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.data.StarItemsWrapper
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 09/10/2017.
 */
interface StarredItemsDataSource {

    fun add(channelId: String = "",
            file: String = "",
            fileCommentId: String = "",
            timestamp: String = ""): Observable<ResponseWrapper>

    fun list(count: Int = Api.LIMIT,
             page: Int = 1): Observable<StarItemsWrapper>

    fun remove(channelId: String = "",
               fileId: String = "",
               fileCommentId: String = "",
               timestamp: String = ""): Observable<ResponseWrapper>

}