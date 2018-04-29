package io.github.tonnyl.latticify.ui.chat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 06/10/2017.
 */
class ChatActivity : AppCompatActivity() {

    private lateinit var mChannelFragment: ChatFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mChannelFragment = ChatFragment.newInstance()

        ChatPresenter(
                mChannelFragment,
                intent.getParcelableExtra<Channel>(ChatPresenter.KEY_EXTRA_CHANNEL),
                intent.getBooleanExtra(ChatPresenter.KEY_EXTRA_IS_IM, false)
        )

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mChannelFragment, ChatFragment::class.java.simpleName)
                .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mChannelFragment.onActivityResult(requestCode, resultCode, data)
    }

}