package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.FileListWrapper
import io.github.tonnyl.latticify.data.FileWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 30/12/2017.
 */
interface FilesService {

    /**
     * This method deletes a file from your team.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param fileId Required. ID of file to delete. e.g. F1234567890.
     *
     * @return If successful, the command returns a [ResponseWrapper] object.
     */
    @POST("files.delete")
    @FormUrlEncoded
    fun delete(@Field("token") token: String,
               @Field("file") fileId: String): Observable<ResponseWrapper>

    /**
     * Gets information about a team file.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param fileId Required. Specify a file by providing its ID. e.g. F2147483862.
     * @param count Optional, default=[Api.LIMIT]. Number of items to return per page.
     * @param page Optional, default=1. Page number of results to return.
     *
     * @return If successful, the command returns a [FileWrapper] object.
     */
    @POST("files.info")
    @FormUrlEncoded
    fun info(@Field("token") token: String,
             @Field("file") fileId: String,
             @Field("count") count: Int = Api.LIMIT,
             @Field("page") page: Int = 1): Observable<FileWrapper>

    /**
     * Lists & filters team files.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channelId Optional. Filter files appearing in a specific channel, indicated by its ID. e.g. C1234567890.
     * @param count Optional, default=[Api.LIMIT]. Number of items to return per page.
     * @param page Optional, default=1. Page number of results to return.
     * @param tsFrom Optional, default=0. Filter files created after this timestamp (inclusive). e.g. 123456789.
     * @param tsTo Optional, default=now. Filter files created before this timestamp (inclusive). e.g. 123456789.
     * @param types Optional, default=all. Filter files by type:
     *              all - All files
     *              spaces - Posts
     *              snippets - Snippets
     *              images - Image files
     *              gdocs - Google docs
     *              zips - Zip files
     *              pdfs - PDF files
     *              You can pass multiple values in the types argument, like types=spaces,snippets.
     *              The default value is all, which does not filter the list.
     * @param userId Optional. Filter files created by a single user. e.g. W1234567890.
     *
     * @return If successful, the command returns a [FileListWrapper] object.
     */
    @POST("files.list")
    @FormUrlEncoded
    fun list(@Field("token") token: String,
             @Field("channel") channelId: String = "",
             @Field("count") count: Int = Api.LIMIT,
             @Field("page") page: Int = 1,
             @Field("ts_from") tsFrom: Long = 0,
             @Field("ts_to") tsTo: Long = 0,
             @Field("types") types: String = "all",
             @Field("user") userId: String = ""): Observable<FileListWrapper>

    /**
     * Revokes public/external sharing access for a file.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param fileId Required. File to revoke. e.g. F1234567890.
     *
     * @return If successful, the command returns a [FileWrapper] object.
     */
    @POST("files.revokePublicURL")
    @FormUrlEncoded
    fun revokePublicURL(@Field("token") token: String,
                        @Field("file") fileId: String): Observable<FileWrapper>

    /**
     * Enables a file for public/external sharing.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param fileId Required. File to share. e.g. F1234567890.
     *
     * @return If successful, the command returns a [FileWrapper] object, including the permalink_public url.
     */
    @POST("files.sharedPublicURL")
    @FormUrlEncoded
    fun sharedPublicURL(@Field("token") token: String,
                        @Field("file") fileId: String): Observable<FileWrapper>

    /**
     * Uploads or creates a file.
     *
     * @param token Required. Authentication token bearing required scopes.
     * @param channels Optional. Comma-separated list of channel names or IDs where the file will be shared. e.g. C1234567890,C2345678901,C3456789012.
     * @param content Optional. File contents via a POST variable. If omitting this parameter, you must provide a file.
     * @param file Optional. File contents via multipart/form-data. If omitting this parameter, you must submit content.
     * @param filename Optional. Filename of file. e.g. foo.text.
     * @param fileType Optional. A file type identifier. e.g. php.
     * @param initialComment Optional. Initial comment to add to file. e.g. Best!
     * @param title Optional. Title of file. e.g. My File.
     *
     * @return If successful, the command returns a [FileWrapper] object.
     */
    // todo
    @POST("files.upload")
    @FormUrlEncoded
    fun upload(@Field("token") token: String,
               @Field("channels") channels: List<String> = listOf(),
               @Field("content") content: String = "",
               @Field("file") file: String = "",
               @Field("filename") filename: String = "",
               @Field("filetype") fileType: String = "",
               @Field("initial_comment") initialComment: String = "",
               @Field("title") title: String = ""): Observable<FileWrapper>

}