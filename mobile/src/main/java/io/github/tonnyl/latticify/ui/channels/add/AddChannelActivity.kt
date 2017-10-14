package io.github.tonnyl.latticify.ui.channels.add

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class AddChannelActivity : AppCompatActivity() {

    private lateinit var mAddChannelFragment: AddChannelFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mAddChannelFragment = AddChannelFragment.newInstance()

        AddChannelPresenter(mAddChannelFragment)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mAddChannelFragment, AddChannelFragment::class.java.simpleName)
                .commit()

    }

}