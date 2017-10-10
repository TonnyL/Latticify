package io.github.tonnyl.latticify.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.repository.TeamRepository
import io.github.tonnyl.latticify.data.repository.UsersProfileRepository
import io.github.tonnyl.latticify.glide.GlideLoader
import io.github.tonnyl.latticify.ui.auth.AuthActivity
import io.github.tonnyl.latticify.ui.channels.ChannelsFragment
import io.github.tonnyl.latticify.ui.channels.ChannelsPresenter
import io.github.tonnyl.latticify.ui.ims.IMsFragment
import io.github.tonnyl.latticify.ui.ims.IMsPresenter
import io.github.tonnyl.latticify.ui.profile.ProfileActivity
import io.github.tonnyl.latticify.ui.profile.ProfilePresenter
import io.github.tonnyl.latticify.ui.starred.StarredItemsFragment
import io.github.tonnyl.latticify.ui.starred.StarredItemsPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mChannelsFragment: ChannelsFragment
    private lateinit var mImsFragment: IMsFragment
    private lateinit var mStarredItemsFragment: StarredItemsFragment

    private val mFragments = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()

        fab.setOnClickListener {

        }

        getMyInfo()

        getTeamInfo()

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity<AuthActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_direct_messages -> {
                showFragmentAndHideRest(mImsFragment)
                titleTextView.text = getString(R.string.messages)
            }
            R.id.nav_channels -> {
                showFragmentAndHideRest(mChannelsFragment)
                titleTextView.text = getString(R.string.channels)
            }
            R.id.nav_starred_items -> {
                showFragmentAndHideRest(mStarredItemsFragment)
                titleTextView.text = getString(R.string.starred)
            }
            R.id.nav_settings -> {

            }
            R.id.nav_about -> {

            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initViews() {
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        navView.setCheckedItem(R.id.nav_direct_messages)

        mChannelsFragment = ChannelsFragment.newInstance()
        mImsFragment = IMsFragment.newInstance()
        mStarredItemsFragment = StarredItemsFragment.newInstance()

        addFragments(mChannelsFragment, mImsFragment, mStarredItemsFragment)

        ChannelsPresenter(mChannelsFragment)
        IMsPresenter(mImsFragment)
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

    private fun getMyInfo() {
        UsersProfileRepository.getUsersProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ profile ->
                    if (profile.ok) {
                        GlideLoader.loadAvatar(userAvatarImageView, profile.userProfile.image192)
                    }

                    userAvatarImageView.setOnClickListener {
                        startActivity<ProfileActivity>(ProfilePresenter.KEY_EXTRA_USER_PROFILE to profile.userProfile)
                    }
                }, {

                })
    }

    private fun getTeamInfo() {
        TeamRepository.info()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        with(it.team) {
                            GlideLoader.loadAvatar(avatarImageView, icon.image132)

                            teamNameTextView.text = name
                            teamUrlTextView.text = getString(R.string.team_url).format(domain)
                        }
                    }
                }, {
                    it.printStackTrace()
                })

    }

}
