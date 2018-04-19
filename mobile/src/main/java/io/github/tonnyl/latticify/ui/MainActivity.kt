package io.github.tonnyl.latticify.ui

import android.accounts.AccountManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.airbnb.deeplinkdispatch.DeepLink
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.repository.TeamRepository
import io.github.tonnyl.latticify.data.repository.UserPoolRepository
import io.github.tonnyl.latticify.data.repository.UsersRepository
import io.github.tonnyl.latticify.glide.GlideLoader
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.service.WebSocketService
import io.github.tonnyl.latticify.ui.about.AboutActivity
import io.github.tonnyl.latticify.ui.auth.AuthActivity
import io.github.tonnyl.latticify.ui.auth.Authenticator
import io.github.tonnyl.latticify.ui.channels.ChannelsFragment
import io.github.tonnyl.latticify.ui.channels.ChannelsPresenter
import io.github.tonnyl.latticify.ui.channels.add.AddChannelActivity
import io.github.tonnyl.latticify.ui.directory.DirectoryFragment
import io.github.tonnyl.latticify.ui.directory.DirectoryPresenter
import io.github.tonnyl.latticify.ui.groups.GroupsFragment
import io.github.tonnyl.latticify.ui.groups.GroupsPresenter
import io.github.tonnyl.latticify.ui.ims.IMsFragment
import io.github.tonnyl.latticify.ui.ims.IMsPresenter
import io.github.tonnyl.latticify.ui.profile.ProfileActivity
import io.github.tonnyl.latticify.ui.profile.ProfilePresenter
import io.github.tonnyl.latticify.ui.search.SearchActivity
import io.github.tonnyl.latticify.ui.settings.SettingsActivity
import io.github.tonnyl.latticify.ui.snooze.SnoozeNotificationsActivity
import io.github.tonnyl.latticify.ui.starred.StarredItemsFragment
import io.github.tonnyl.latticify.ui.starred.StarredItemsPresenter
import io.github.tonnyl.latticify.ui.status.SetStatusActivity
import io.github.tonnyl.latticify.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

@DeepLink("slack://open", "slack://open?team={TEAM_ID}")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mChannelsFragment: ChannelsFragment
    private lateinit var mImsFragment: IMsFragment
    private lateinit var mGroupsFragment: GroupsFragment
    private lateinit var mStarredItemsFragment: StarredItemsFragment
    private lateinit var mDirectoryFragment: DirectoryFragment

    private val mFragments = mutableListOf<Fragment>()

    // Records the selected navigation menu item's id.
    private var mCheckedItemId = 0
    private val mCompositeDisposable = CompositeDisposable()
    private lateinit var mAccountManager: AccountManager
    private var mUserId: String? = null

    private val mHelloReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            toolbar.setTitle(R.string.app_name)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mAccountManager = AccountManager.get(this)

        getToken()

        getMyInfo()

        navView.getHeaderView(0).accountActionImageView.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }

        fab.setOnClickListener {
            when (mCheckedItemId) {
                R.id.nav_direct_messages -> {

                }
                R.id.nav_channels -> {
                    startActivity(Intent(this, AddChannelActivity::class.java))
                }
                R.id.nav_groups -> {

                }
                R.id.nav_directory -> {

                }
            }
        }

        UserPoolRepository.init(this@MainActivity)

    }


    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this@MainActivity).registerReceiver(mHelloReceiver, IntentFilter(Constants.FILTER_HELLO))
    }

    override fun onPause() {
        super.onPause()

        LocalBroadcastManager.getInstance(this@MainActivity).unregisterReceiver(mHelloReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()

        mCompositeDisposable.clear()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }
            R.id.action_set_status -> {
                startActivity(Intent(this, SetStatusActivity::class.java))
            }
            R.id.action_snooze -> {
                showSnoozeNotificationsDialog()
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_starred_items) {
            fab.hide()
        } else {
            fab.show()
        }
        when (item.itemId) {
            R.id.nav_profile -> {
                navigateToProfile()
            }
            R.id.nav_direct_messages -> {
                showFragmentAndHideRest(mImsFragment)
                toolbar.title = getString(R.string.nav_messages)
                mCheckedItemId = R.id.nav_direct_messages
            }
            R.id.nav_channels -> {
                showFragmentAndHideRest(mChannelsFragment)
                toolbar.title = getString(R.string.nav_channels)
                mCheckedItemId = R.id.nav_channels
            }
            R.id.nav_groups -> {
                showFragmentAndHideRest(mGroupsFragment)
                toolbar.title = getString(R.string.nav_groups)
                mCheckedItemId = R.id.nav_groups
            }
            R.id.nav_directory -> {
                showFragmentAndHideRest(mDirectoryFragment)
                toolbar.title = getString(R.string.directory)
                mCheckedItemId = R.id.nav_directory
            }
            R.id.nav_starred_items -> {
                showFragmentAndHideRest(mStarredItemsFragment)
                toolbar.title = getString(R.string.nav_starred)
                mCheckedItemId = R.id.nav_starred_items
            }
            R.id.nav_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.nav_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initViews() {
        toolbar.setTitle(R.string.connecting)

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        navView.setCheckedItem(R.id.nav_direct_messages)

        fab.isClickable = true

        mCheckedItemId = R.id.nav_direct_messages

        mChannelsFragment = ChannelsFragment.newInstance()
        mImsFragment = IMsFragment.newInstance()
        mDirectoryFragment = DirectoryFragment.newInstance()
        mStarredItemsFragment = StarredItemsFragment.newInstance()
        mGroupsFragment = GroupsFragment.newInstance()

        addFragments(mChannelsFragment,
                mImsFragment,
                mGroupsFragment,
                mDirectoryFragment,
                mStarredItemsFragment)

        ChannelsPresenter(mChannelsFragment)
        IMsPresenter(mImsFragment)
        GroupsPresenter(mGroupsFragment)
        DirectoryPresenter(mDirectoryFragment)
        StarredItemsPresenter(mStarredItemsFragment)

        showFragmentAndHideRest(mImsFragment)

    }

    private fun showFragmentAndHideRest(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .apply { mFragments.forEach { hide(it) } }
                .show(fragment)
                .commit()
    }

    private fun addFragments(vararg fragments: Fragment) {
        fragments.forEach {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, it, it::class.java.simpleName)
                    .commit()
            mFragments.add(it)
        }
    }

    private fun navigateToProfile() {
        mUserId?.let {
            startActivity(Intent(this, ProfileActivity::class.java).apply {
                putExtra(ProfilePresenter.KEY_EXTRA_USER_ID, it)
            })
        } ?: run {
            val disposable = UsersRepository.identity()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.ok) {
                            mUserId = it.user.id

                            startActivity(Intent(this, ProfileActivity::class.java).apply {
                                putExtra(ProfilePresenter.KEY_EXTRA_USER_ID, it.user.id)
                            })
                        }
                    }, {

                    })
            mCompositeDisposable.add(disposable)
        }
    }

    private fun getTeamInfo() {
        val disposable = TeamRepository.info()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        with(it.team) {
                            GlideLoader.loadAvatar(teamAvatarImageView, icon.image132)

                            teamNameTextView.text = name
                            teamUrlTextView.text = getString(R.string.team_url).format(domain)
                        }
                    }
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    private fun showSnoozeNotificationsDialog() {
        startActivity(Intent(this@MainActivity, SnoozeNotificationsActivity::class.java))
    }

    private fun getToken() {
        val disposable = Observable.create<String> {
            val accounts = mAccountManager.getAccountsByType(Authenticator.KEY_ACCOUNT_TYPE)

            if (accounts.isNotEmpty()) {
                val bundle = mAccountManager.getAuthToken(accounts[0], Authenticator.KEY_AUTH_TYPE, null, this@MainActivity, null, null).result
                val token = bundle.get(AccountManager.KEY_AUTHTOKEN).toString()

                it.onNext(token)
            } else {
                it.onNext("")
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ token ->
                    if (token.isNotEmpty()) {
                        RetrofitClient.mToken = token

                        initViews()

                        getTeamInfo()

                        startService(Intent(this@MainActivity, WebSocketService::class.java))

                    } else {
                        val i = Intent(this, AuthActivity::class.java)
                        i.action = AuthActivity.ACTION_FROM_MAIN
                        startActivity(i)

                        finish()
                    }
                })
        mCompositeDisposable.add(disposable)
    }

    private fun getMyInfo() {
        val disposable = UsersRepository.identity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mUserId = it.user.id
                    }
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

}
