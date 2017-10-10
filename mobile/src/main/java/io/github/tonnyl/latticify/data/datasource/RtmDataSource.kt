package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.RtmResponseMessageWrapper
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 10/10/2017.
 */
interface RtmDataSource {

    fun connect(batchPresenceAware: Int): Observable<RtmResponseMessageWrapper>

}