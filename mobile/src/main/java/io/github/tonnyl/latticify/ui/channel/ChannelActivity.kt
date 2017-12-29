package io.github.tonnyl.latticify.ui.channel

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLink
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 06/10/2017.
 */
@DeepLink("slack://channel?team={TEAM_ID}&id={CHANNEL_ID}")
class ChannelActivity : AppCompatActivity() {

    private lateinit var mChannelFragment: ChannelFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mChannelFragment = ChannelFragment.newInstance()

        ChannelPresenter(mChannelFragment, intent.getParcelableExtra<Channel>(ChannelPresenter.KEY_EXTRA_CHANNEL))

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mChannelFragment, ChannelFragment::class.java.simpleName)
                .commit()
    }

}