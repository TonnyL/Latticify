package io.github.tonnyl.latticify.ui.file

import io.github.tonnyl.latticify.data.File
import io.github.tonnyl.latticify.data.repository.FilesRepository
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
        @JvmField
        val KEY_EXTRA_FILE = "KEY_EXTRA_FILE"
        @JvmField
        val KEY_EXTRA_FILE_ID = "KEY_EXTRA_FILE_ID"
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
        }
        fetchFileData()
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
                    } else {
                        mView.showFetchDataError()
                    }
                }, { error ->
                    error.printStackTrace()
                    mView.showFetchDataError()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun addReaction() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun comment(comment: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun star() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun share() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun copyLink() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openInBrowser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}