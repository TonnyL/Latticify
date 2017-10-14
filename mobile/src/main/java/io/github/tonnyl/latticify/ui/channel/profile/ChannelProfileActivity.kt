package io.github.tonnyl.latticify.ui.channel.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 12/10/2017.
 */
class ChannelProfileActivity : AppCompatActivity() {

    private lateinit var mChannelProfileFragment: ChannelProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mChannelProfileFragment = ChannelProfileFragment.newInstance()

        intent.getStringExtra(ChannelProfilePresenter.KEY_EXTRA_CHANNEL_ID)?.let {
            ChannelProfilePresenter(mChannelProfileFragment, it)
        } ?: run {
            ChannelProfilePresenter(mChannelProfileFragment, intent.getParcelableExtra<Channel>(ChannelProfilePresenter.KEY_EXTRA_CHANNEL))
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mChannelProfileFragment, ChannelProfileFragment::class.java.simpleName)
                .commit()

    }

}