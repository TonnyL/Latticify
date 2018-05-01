package io.github.tonnyl.latticify.ui.channel.members

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R
import kotlinx.android.synthetic.main.activity_common.*

class ChannelMembersActivity : AppCompatActivity() {

    private lateinit var mChannelMembersFragment: ChannelMembersFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mChannelMembersFragment = ChannelMembersFragment.newInstance()

        ChannelMembersPresenter(mChannelMembersFragment, intent.getStringExtra(ChannelMembersPresenter.KEY_EXTRA_CHANNEL_ID))

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mChannelMembersFragment, ChannelMembersFragment::class.java.simpleName)
                .commit()
    }

}