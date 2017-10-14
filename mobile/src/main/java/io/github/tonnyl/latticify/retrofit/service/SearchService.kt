package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.SearchAllWrapper
import io.github.tonnyl.latticify.data.SearchFilesWrapper
import io.github.tonnyl.latticify.data.SearchMessagesWrapper
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 13/10/2017.
 */
interface SearchService {

    /**
     * Searches for messages and files matching a query.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param query Required. Search query. May contains booleans, etc. e.g. pickleface.
     * @param count Optional, default=20. Number of items to return per page. e.g. 20.
     * @param highlight Optional. Pass a value of true to enable query highlight markers (see below).
     * @param page    Optional, default=1. Page number of results to return.
     * @param sort Optional, default=score. Return matches sorted by either score or timestamp.
     * @param sortDir Optional, default=desc. Change sort direction to ascending (asc) or descending (desc).
     *
     * @return If successful, the command returns matches broken down by their type of content, similar
     * to the facebook/gmail auto-completed search widgets.
     */
    @POST("search.all")
    @FormUrlEncoded
    fun all(@Field("token") token: String,
            @Field("query") query: String,
            @Field("count") count: Int = Api.LIMIT,
            @Field("highlight") highlight: Boolean = true,
            @Field("page") page: Int = 1,
            @Field("sort") sort: String = "score",
            @Field("sort_dir") sortDir: String = "desc"): Observable<SearchAllWrapper>

    /**
     * Searches for files matching a query.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param query Required. Search query. May contains booleans, etc. e.g. pickleface.
     * @param count Optional, default=20. Number of items to return per page. e.g. 20.
     * @param highlight Optional. Pass a value of true to enable query highlight markers (see below).
     * @param page    Optional, default=1. Page number of results to return.
     * @param sort Optional, default=score. Return matches sorted by either score or timestamp.
     * @param sortDir Optional, default=desc. Change sort direction to ascending (asc) or descending (desc).
     *
     * @return If successful, the command returns a [SearchFilesWrapper] object.
     */
    @POST("search.files")
    @FormUrlEncoded
    fun files(@Field("token") token: String,
              @Field("query") query: String,
              @Field("count") count: Int = Api.LIMIT,
              @Field("highlight") highlight: Boolean = true,
              @Field("page") page: Int = 1,
              @Field("sort") sort: String = "score",
              @Field("sort_dir") sortDir: String = "desc"): Observable<SearchFilesWrapper>

    /**
     * Searches for messages matching a query.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param query Required. Search query. May contains booleans, etc. e.g. pickleface.
     * @param count Optional, default=20. Number of items to return per page. e.g. 20.
     * @param highlight Optional. Pass a value of true to enable query highlight markers (see below).
     * @param page    Optional, default=1. Page number of results to return.
     * @param sort Optional, default=score. Return matches sorted by either score or timestamp.
     * @param sortDir Optional, default=desc. Change sort direction to ascending (asc) or descending (desc).
     *
     * @return If successful, the command returns a [SearchMessagesWrapper] object.
     */
    @POST("search.messages")
    @FormUrlEncoded
    fun messages(@Field("token") token: String,
                 @Field("query") query: String,
                 @Field("count") count: Int = Api.LIMIT,
                 @Field("highlight") highlight: Boolean = true,
                 @Field("page") page: Int = 1,
                 @Field("sort") sort: String = "score",
                 @Field("sort_dir") sortDir: String = "desc"): Observable<SearchMessagesWrapper>
}