package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.ResponseWrapper
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 31/12/2017.
 */
interface ReactionsDataSource {

    fun add(token: String,
            name: String,
            channelId: String,
            fileId: String,
            fileCommentId: String,
            timestamp: Long): Observable<ResponseWrapper>

    fun remove(token: String,
               name: String,
               channelId: String,
               fileId: String,
               fileCommentId: String,
               timestamp: Long): Observable<ResponseWrapper>

}