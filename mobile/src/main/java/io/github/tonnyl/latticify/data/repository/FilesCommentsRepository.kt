package io.github.tonnyl.latticify.data.repository

import io.github.tonnyl.latticify.data.FileCommentWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.github.tonnyl.latticify.data.datasource.FilesCommentsDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.FilesCommentsService
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 30/12/2017.
 */
class FilesCommentsRepository : FilesCommentsDataSource {

    private val mFilesCommentsService = RetrofitClient.createService(FilesCommentsService::class.java)
    private val mToken = RetrofitClient.mToken

    override fun add(comment: String, fileId: String): Observable<FileCommentWrapper> {
        return mFilesCommentsService.add(mToken, comment, fileId)
    }

    override fun delete(fileId: String, commentId: String): Observable<ResponseWrapper> {
        return mFilesCommentsService.delete(mToken, fileId, commentId)
    }

    override fun edit(comment: String, fileId: String, commentId: String): Observable<FileCommentWrapper> {
        return mFilesCommentsService.edit(mToken, comment, fileId, commentId)
    }
}