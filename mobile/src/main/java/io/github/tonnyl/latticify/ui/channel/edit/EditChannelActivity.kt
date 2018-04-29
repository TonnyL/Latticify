package io.github.tonnyl.latticify.ui.channel.edit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 14/10/2017.
 */
class EditChannelActivity : AppCompatActivity() {

    private lateinit var mEditChannelFragment: EditChannelFragment

    companion object {
        const val EXTRA_RESULT_UPDATE = "EXTRA_RESULT_UPDATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mEditChannelFragment = EditChannelFragment.newInstance()

        EditChannelPresenter(mEditChannelFragment, intent.getParcelableExtra(EditChannelPresenter.KEY_EXTRA_CHANNEL))

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mEditChannelFragment, EditChannelFragment::class.java.simpleName)
                .commit()
    }

}