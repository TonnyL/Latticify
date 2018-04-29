package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.FileListWrapper
import io.github.tonnyl.latticify.data.FileWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.retrofit.Api
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * Created by lizhaotailang on 30/12/2017.
 */
interface FilesDataSource {

    fun delete(fileId: String): Observable<ResponseWrapper>

    fun info(fileId: String,
             count: Int = Api.LIMIT,
             page: Int = 1): Observable<FileWrapper>

    fun list(channelId: String = "",
             count: Int = Api.LIMIT,
             page: Int = 1,
             tsFrom: Long = 0,
             tsTo: Long = 0,
             types: String = "all",
             userId: String = ""): Observable<FileListWrapper>

    fun revokePublicURL(fileId: String): Observable<FileWrapper>

    fun sharedPublicURL(fileId: String): Observable<FileWrapper>

    fun upload(channels: RequestBody,
               content: RequestBody?,
               file: MultipartBody.Part? = null,
               filename: RequestBody,
               fileType: RequestBody,
               initialComment: RequestBody,
               title: RequestBody): Observable<FileWrapper>

}