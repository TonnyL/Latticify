package io.github.tonnyl.latticify.ui.auth

import android.content.Intent
import io.github.tonnyl.latticify.data.AccessToken
import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 19/09/2017.
 */
interface AuthContract {

    interface View : BaseView<Presenter> {

        fun setProcessingIndicator(isProcessing: Boolean)

        fun showMessage(message: String)

        fun handleAuthCallback(intent: Intent?)

        fun mockStoreAccessToken(accessToken: AccessToken)

    }

    interface Presenter : BasePresenter {

        fun requestAccessToken(code: String)

    }

}