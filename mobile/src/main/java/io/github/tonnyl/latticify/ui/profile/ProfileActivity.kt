package io.github.tonnyl.latticify.ui.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.UsersProfile

/**
 * Created by lizhaotailang on 08/10/2017.
 */
class ProfileActivity : AppCompatActivity() {

    private lateinit var mProfileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        mProfileFragment = ProfileFragment.newInstance()

        intent.getParcelableExtra<UsersProfile>(ProfilePresenter.KEY_EXTRA_USER_PROFILE)?.let {
            ProfilePresenter(mProfileFragment, it)
        } ?: run {
            intent?.getStringExtra(ProfilePresenter.KEY_EXTRA_USER_ID)?.let {
                ProfilePresenter(mProfileFragment, it)
            } ?: run {
                ProfilePresenter(mProfileFragment)
            }
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mProfileFragment, ProfileFragment::class.java.simpleName)
                .commit()

    }

}