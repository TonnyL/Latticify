package io.github.tonnyl.latticify.ui.invite

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class InviteActivity : AppCompatActivity() {

    private lateinit var mInviteFragment: InviteFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mInviteFragment = InviteFragment.newInstance()

        InvitePresenter(mInviteFragment)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mInviteFragment, InviteFragment::class.java.simpleName)
                .commit()

    }

}