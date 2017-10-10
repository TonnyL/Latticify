package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.ReminderWrapper
import io.github.tonnyl.latticify.data.RemindersWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 10/10/2017.
 */
interface RemindersService {

    /**
     * Creates a reminder.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param text Required. The content of the reminder. e.g. eat a banana.
     * @param time Required. When this reminder should happen: the Unix timestamp (up to five years from now),
     * the number of seconds until the reminder (if within 24 hours), or a natural language description (Ex. "in 15 minutes," or "every Thursday").
     * @param userId Optional. The user who will receive the reminder. If no user is specified, the reminder will go to user who created it. e.g. U18888888.
     *
     * @return If successful, the command returns a [ReminderWrapper] object.
     */
    @POST("reminders.add")
    @FormUrlEncoded
    fun add(@Field("token") token: String,
            @Field("text") text: String,
            @Field("time") time: Long,
            @Field("user") userId: String): Observable<ReminderWrapper>

    /**
     * Marks a reminder as complete.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param reminderId Required. The ID of the reminder to be marked as complete. e.g. Rm12345678.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("reminders.complete")
    @FormUrlEncoded
    fun complete(@Field("token") token: String,
                 @Field("reminder") reminderId: String): Observable<ResponseWrapper>

    /**
     * Deletes a reminder.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param reminderId Required. The ID of the reminder. e.g. Rm12345678.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("reminders.delete")
    @FormUrlEncoded
    fun delete(@Field("token") token: String,
               @Field("reminder") reminderId: String): Observable<ResponseWrapper>

    /**
     * Gets information about a reminder.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param reminderId Required. The ID of the reminder. e.g. Rm12345678.
     *
     * @return If successful, the command returns a [ReminderWrapper] object.
     */
    @POST("reminders.info")
    @FormUrlEncoded
    fun info(@Field("token") token: String,
             @Field("reminder") reminderId: String): Observable<ReminderWrapper>

    /**
     * Lists all reminders created by or for a given user.
     *
     * @param token Required. Authentication token bearing required scopes.
     *
     * @return If successful, the command returns a [RemindersWrapper] object.
     */
    @POST("reminders.list")
    @FormUrlEncoded
    fun list(@Field("token") token: String): Observable<RemindersWrapper>

}