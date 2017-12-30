package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.FileCommentWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 30/12/2017.
 */
interface FilesCommentsService {

    /**
     * Add a comment to an existing file.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param comment Required. Text of the comment to add. e.g. Everyone should take a moment to read this file.
     * @param fileId Required. File to add a comment to. e.g. F1234467890.
     *
     * @return If successful, the response will include a [FileCommentWrapper] object.
     */
    @POST("files.comments.add")
    @FormUrlEncoded
    fun add(@Field("token") token: String,
            @Field("comment") comment: String,
            @Field("file") fileId: String): Observable<FileCommentWrapper>

    /**
     * Deletes an existing comment on a file.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param fileId Required. File to delete a comment from. e.g. F1234567890.
     * @param commentId Required. The comment to delete. e.g. Fc1234567890.
     *
     * @return If successful, the response will include a [ResponseWrapper] object.
     */
    @POST("files.comments.delete")
    @FormUrlEncoded
    fun delete(@Field("token") token: String,
               @Field("file") fileId: String,
               @Field("id") commentId: String): Observable<ResponseWrapper>

    /**
     * Edit an existing file comment.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param comment Required. Text of the comment to edit. e.g. Everyone should take a moment to read this file, seriously.
     * @param fileId Required. File containing the comment to edit. e.g. F1234567890.
     * @param commentId Required. The comment to edit. e.g. Fc1234567890.
     *
     * @return If successful, the response will include a [FileCommentWrapper] object.
     */
    @POST("files.comments.edit")
    @FormUrlEncoded
    fun edit(@Field("token") token: String,
             @Field("comment") comment: String,
             @Field("file") fileId: String,
             @Field("id") commentId: String): Observable<FileCommentWrapper>
}