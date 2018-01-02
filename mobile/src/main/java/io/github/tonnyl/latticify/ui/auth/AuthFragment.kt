package io.github.tonnyl.latticify.ui.auth

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
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
import io.github.tonnyl.latticify.ui.MainActivity
import io.github.tonnyl.latticify.ui.search.SearchActivity
import io.github.tonnyl.latticify.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_auth.*

/**
 * Created by lizhaotailang on 19/09/2017.
 */
class AuthFragment : Fragment(), AuthContract.View {

    private lateinit var mPresenter: AuthContract.Presenter

    private lateinit var mAccountManager: AccountManager

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

        mAccountManager = AccountManager.get(context)

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
        Observable.create<List<String>> {
            val teamNames = AccountManager.get(context).getAccountsByType(Authenticator.KEY_ACCOUNT_TYPE)
                    .map {
                        it.name
                    }
            it.onNext(teamNames)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.contains(accessToken.teamName)) {
                        Toast.makeText(context, "This account already exists on your device", Toast.LENGTH_SHORT).show()

                        activity?.finish()
                    } else {
                        enableShortcuts()

                        val account = Account(accessToken.teamName, Authenticator.KEY_ACCOUNT_TYPE)
                        mAccountManager.addAccountExplicitly(account, "",
                                Bundle().apply {
                                    putParcelable(Authenticator.KEY_ACCESS_TOKEN, accessToken)
                                })
                        mAccountManager.setAuthToken(account, Authenticator.KEY_AUTH_TYPE, accessToken.accessToken)

                        activity?.let {
                            Log.d("auth", "" + it.intent.action)
                            if (it.intent.action == AuthActivity.ACTION_FROM_MAIN) {
                                startActivity(Intent(it, MainActivity::class.java))
                            }
                            it.finish()
                        }
                    }
                }, {

                })
    }

    // Enable the App Shortcuts for devices running Android 7.1 and above.
    // See [https://developer.android.com/guide/topics/ui/shortcuts.html].
    private fun enableShortcuts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val searchShortcut = ShortcutInfo.Builder(context, Constants.SHORTCUT_ID_SEARCH)
                    .apply {
                        setShortLabel(getString(R.string.search))
                        setLongLabel(getString(R.string.search))
                        setIcon(Icon.createWithResource(context, R.drawable.ic_shortcut_search))
                        setIntent(Intent(context?.applicationContext, SearchActivity::class.java)
                                .apply {
                                    action = Constants.INTENT_ACTION_SEARCH
                                    addCategory(ShortcutInfo.SHORTCUT_CATEGORY_CONVERSATION)
                                })
                    }.build()

            val snoozeShortcut = ShortcutInfo.Builder(context, Constants.SHORTCUT_ID_SNOOZE)
                    .apply {
                        setShortLabel(getString(R.string.snooze))
                        setLongLabel(getString(R.string.snooze))
                        setIcon(Icon.createWithResource(context, R.drawable.ic_shortcut_notifications_off))
                        setIntent(Intent(context?.applicationContext, SearchActivity::class.java)
                                .apply {
                                    action = Constants.INTENT_ACTION_SNOOZE
                                    addCategory(ShortcutInfo.SHORTCUT_CATEGORY_CONVERSATION)
                                })
                    }.build()

            activity?.getSystemService(ShortcutManager::class.java)?.dynamicShortcuts = mutableListOf<ShortcutInfo>(searchShortcut, snoozeShortcut)
        }
    }

}