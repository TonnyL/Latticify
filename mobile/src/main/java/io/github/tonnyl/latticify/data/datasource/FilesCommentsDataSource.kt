package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.FileCommentWrapper
import io.github.tonnyl.latticify.data.ResponseWrapper
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 30/12/2017.
 */
interface FilesCommentsDataSource {

    fun add(comment: String,
            fileId: String): Observable<FileCommentWrapper>

    fun delete(fileId: String,
               commentId: String): Observable<ResponseWrapper>

    fun edit(comment: String,
             fileId: String,
             commentId: String): Observable<FileCommentWrapper>

}