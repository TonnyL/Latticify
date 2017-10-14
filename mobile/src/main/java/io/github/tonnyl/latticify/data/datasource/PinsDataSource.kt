package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.PinnedItemsWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 12/10/2017.
 */
interface PinsDataSource {

    fun add(channelId: String,
            fileId: String = "",
            fileComment: String = "",
            timestamp: String = ""): Observable<ResponseWrapper>

    fun list(channelId: String): Observable<PinnedItemsWrapper>

    fun remove(channelId: String,
               fileId: String = "",
               fileComment: String = "",
               timestamp: String = ""): Observable<ResponseWrapper>

}