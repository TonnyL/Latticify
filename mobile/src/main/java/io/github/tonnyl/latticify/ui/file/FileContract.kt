package io.github.tonnyl.latticify.ui.file

import io.github.tonnyl.latticify.data.File
import io.github.tonnyl.latticify.data.FileWrapper
import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 29/12/2017.
 */
interface FileContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun showFetchDataError()

        fun showFileData(file: File)

        fun showContent(wrapper: FileWrapper)

    }

    interface Presenter : BasePresenter {

        fun addReaction()

        fun comment(comment: String)

        fun star()

        fun share()

        fun copyLink()

        fun delete()

        fun openInBrowser()

    }

}