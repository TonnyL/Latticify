package io.github.tonnyl.latticify.ui.file

import io.github.tonnyl.latticify.data.File
import io.github.tonnyl.latticify.data.repository.FilesCommentsRepository
import io.github.tonnyl.latticify.data.repository.FilesRepository
import io.github.tonnyl.latticify.data.repository.StarredItemsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 29/12/2017.
 */
class FilePresenter(
        private val mView: FileContract.View,
        private val mFileId: String
) : FileContract.Presenter {

    private val mCompositeDisposable = CompositeDisposable()
    private var mFile: File? = null
    private val mFilesRepository = FilesRepository()

    companion object {
        const val KEY_EXTRA_FILE = "KEY_EXTRA_FILE"
        const val KEY_EXTRA_FILE_ID = "KEY_EXTRA_FILE_ID"
    }

    init {
        mView.setPresenter(this)
    }

    constructor(mView: FileContract.View, file: File) : this(mView, file.id) {
        mFile = file
    }

    override fun subscribe() {
        mFile?.let {
            mView.showFileData(it)
        } ?: run {
            fetchFileData()
        }
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    private fun fetchFileData() {
        mView.setLoadingIndicator(true)

        val disposable = mFilesRepository.info(mFileId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ fileWrapper ->
                    mView.setLoadingIndicator(false)

                    if (fileWrapper.ok) {
                        mView.showContent(fileWrapper)

                        mView.showFileData(fileWrapper.file)

                        mFile = fileWrapper.file
                    } else {
                        mView.showFetchDataError()
                    }
                }, { error ->
                    error.printStackTrace()
                    mView.showFetchDataError()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun comment(comment: String) {
        val disposable = FilesCommentsRepository()
                .add(comment, mFileId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mView.showCommendAdded()
                    }
                }, {
                    it.printStackTrace()
                    it.message?.let {
                        mView.showMessage(it)
                    }
                })
        mCompositeDisposable.add(disposable)
    }

    override fun starUnstar() {
        val disposable = if (mFile?.isStarred == true) {
            StarredItemsRepository.remove("", mFileId, "", "")
        } else {
            StarredItemsRepository.add("", mFileId, "", "")
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mFile?.isStarred = mFile?.isStarred != true
                        mFile?.let {
                            mView.showFileStarred(it.isStarred)
                        }
                    } else {
                        it.error?.let {
                            mView.showMessage(it)
                        }
                    }
                }, {
                    it.printStackTrace()
                    it.message?.let {
                        mView.showMessage(it)
                    }
                })
        mCompositeDisposable.add(disposable)
    }

    override fun delete() {
        val disposable = mFilesRepository.delete(mFileId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseWrapper ->
                    if (responseWrapper.ok) {
                        mView.showFileDeleted()

                        mView.finishActivity()
                    } else {
                        mView.showDeleteFileError()
                    }
                }, { error ->
                    error.printStackTrace()
                    mView.showDeleteFileError()
                })
        mCompositeDisposable.add(disposable)
    }

}