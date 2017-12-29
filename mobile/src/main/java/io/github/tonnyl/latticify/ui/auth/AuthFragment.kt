package io.github.tonnyl.latticify.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.AccessToken
import io.github.tonnyl.latticify.retrofit.Api
import io.github.tonnyl.latticify.util.AccessTokenManager
import kotlinx.android.synthetic.main.fragment_auth.*

/**
 * Created by lizhaotailang on 19/09/2017.
 */
class AuthFragment : Fragment(), AuthContract.View {

    private lateinit var mPresenter: AuthContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance() = AuthFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.subscribe()

        getStartedButton.setOnClickListener {
            val dataString = """
                |${Api.SLACK_AUTHORIZE_URL}
                |?client_id=${Api.CLIENT_ID}
                |&redirect_uri=${Api.SLACK_AUTHORIZE_CALLBACK_URI}
                |&scope=${Api.SLACK_AUTHORIZE_SCOPE}
                """.trimMargin()
            startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(dataString) })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: AuthContract.Presenter) {
        mPresenter = presenter
    }

    override fun setProcessingIndicator(isProcessing: Boolean) {
        if (isProcessing) {
            progressBar.visibility = View.VISIBLE
            getStartedButton.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            getStartedButton.visibility = View.VISIBLE
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun handleAuthCallback(intent: Intent?) {
        if (intent != null
                && intent.data != null
                && intent.data.authority.isNotEmpty()
                && (Api.SLACK_AUTHORIZE_CALLBACK_URI_HOST == intent.data.authority)) {
            setProcessingIndicator(true)
            mPresenter.requestAccessToken(intent.data.getQueryParameter("code").apply { Log.d("code", "" + this) })
        }
    }

    override fun mockStoreAccessToken(accessToken: AccessToken) {
        context?.let {
            AccessTokenManager.setAccessToken(it, accessToken)
        }
    }
}