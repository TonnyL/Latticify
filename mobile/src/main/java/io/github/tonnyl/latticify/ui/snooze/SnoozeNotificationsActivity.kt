package io.github.tonnyl.latticify.ui.snooze

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class SnoozeNotificationsActivity : AppCompatActivity() {

    private lateinit var mSnoozeNotificationsFragment: SnoozeNotificationsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        mSnoozeNotificationsFragment = SnoozeNotificationsFragment.newInstance()

        SnoozeNotificationsPresenter(mSnoozeNotificationsFragment)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mSnoozeNotificationsFragment, SnoozeNotificationsFragment::class.java.simpleName)
                .commit()

    }

}