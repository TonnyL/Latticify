package io.github.tonnyl.latticify.ui.channel.notifications

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 14/10/2017.
 */
class NotificationsActivity : AppCompatActivity() {

    private lateinit var mNotificationsFragment: NotificationsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mNotificationsFragment = NotificationsFragment.newInstance()

        NotificationsPresenter(mNotificationsFragment)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mNotificationsFragment, NotificationsFragment::class.java.simpleName)
                .commit()
    }

}