package io.github.tonnyl.latticify.ui.chat

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
class ChatActivity : AppCompatActivity() {

    private lateinit var mChannelFragment: ChatFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mChannelFragment = ChatFragment.newInstance()

        ChatPresenter(mChannelFragment, intent.getParcelableExtra<Channel>(ChatPresenter.KEY_EXTRA_CHANNEL))

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mChannelFragment, ChatFragment::class.java.simpleName)
                .commit()
    }

}