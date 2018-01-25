package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.DndInfoWrapper
import io.github.tonnyl.latticify.data.DndTeamInfoWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by lizhaotailang on 14/10/2017.
 */
interface DndService {

    /**
     * 	Ends the current user's Do Not Disturb session immediately.
     *
     * @param token Required. Authentication token bearing required scopes.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("dnd.endDnd")
    @FormUrlEncoded
    fun endDnd(@Field("token") token: String): Observable<ResponseWrapper>

    /**
     * Ends the current user's snooze mode immediately.
     *
     * @param token Required. Authentication token bearing required scopes.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("dnd.endSnooze")
    @FormUrlEncoded
    fun endSnooze(@Field("token") token: String): Observable<ResponseWrapper>

    /**
     * Retrieves a user's current Do Not Disturb status.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param userId Optional. User to fetch status for (defaults to current user).
     *
     * @return If successful, the command returns a [DndInfoWrapper] object.
     */
    @GET("dnd.info")
    fun info(@Query("token") token: String,
             @Query("user") userId: String): Observable<DndInfoWrapper>

    /**
     * Turns on Do Not Disturb mode for the current user, or changes its duration.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param numMinutes Required. Number of minutes, from now, to snooze until. e.g. 60.
     *
     * @return If successful, the command returns a [DndInfoWrapper] object.
     * The [DndInfoWrapper.snoozeRemaining] field is expressed in seconds. If your request presents a [numMinutes] value of 1,
     * the response's [DndInfoWrapper.snoozeRemaining] will be 60.
     */
    @GET("dnd.setSnooze")
    fun setSnooze(@Query("token") token: String,
                  @Query("num_minutes") numMinutes: Int): Observable<DndInfoWrapper>

    /**
     * Retrieves the Do Not Disturb status for users on a team.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param users Optional. Comma-separated list of users to fetch Do Not Disturb status for. e.g. U1234,W4567.
     *
     * @return If successful, the command returns a [DndTeamInfoWrapper] object.
     */
    @GET("dnd.teamInfo")
    fun teamInfo(@Query("token") token: String,
                 @Query("users") userIds: Array<String>): Observable<DndTeamInfoWrapper>

}