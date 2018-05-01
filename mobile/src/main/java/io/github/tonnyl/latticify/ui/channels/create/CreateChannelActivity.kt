package io.github.tonnyl.latticify.ui.channels.create

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class CreateChannelActivity : AppCompatActivity() {

    private lateinit var mAddChannelFragment: CreateChannelFragment

    companion object {
        const val EXTRA_CREATE_CHANNEL = "EXTRA_CREATE_CHANNEL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mAddChannelFragment = CreateChannelFragment.newInstance()

        CreateChannelPresenter(mAddChannelFragment,
                intent.getBooleanExtra(CreateChannelPresenter.KEY_CREATE_CHANNEL, true))

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mAddChannelFragment, CreateChannelFragment::class.java.simpleName)
                .commit()

    }

}