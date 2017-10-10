package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.RtmResponseMessageWrapper
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by lizhaotailang on 09/10/2017.
 */
interface RtmService {

    /**
     * Starts a Real Time Messaging session.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param batchPresenceAware Optional, default=false. Only deliver presence events when requested by subscription.
     *
     * @return If successful, the command returns a WebSocket Message Server URL and limited information about the team ([RtmResponseMessageWrapper]).
     */
    @GET("rtm.connect")
    fun connect(@Query("token") token: String,
                @Query("batch_presence_aware") batchPresenceAware: Int): Observable<RtmResponseMessageWrapper>

}