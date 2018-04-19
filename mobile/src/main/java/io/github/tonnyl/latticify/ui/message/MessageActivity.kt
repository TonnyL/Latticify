package io.github.tonnyl.latticify.ui.message

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLink
import io.github.tonnyl.latticify.R
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 08/10/2017.
 */
@DeepLink("slack://user?team={TEAM_ID}&id={USER_ID}")
class MessageActivity : AppCompatActivity() {

    private lateinit var mMessageFragment: MessageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mMessageFragment = MessageFragment.newInstance()

        MessagePresenter(
                mMessageFragment,
                intent.getParcelableExtra(MessagePresenter.KEY_EXTRA_MESSAGE),
                intent.getParcelableExtra(MessagePresenter.KEY_EXTRA_CHANNEL)
        )

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mMessageFragment, MessageFragment::class.java.simpleName)
                .commit()

    }

}