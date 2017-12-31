package io.github.tonnyl.latticify.ui.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R

/**
 * Created by lizhaotailang on 12/10/2017.
 *
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        val fragment = SettingsFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment, SettingsFragment::class.java.simpleName)
                .commit()

        SettingsPresenter(fragment)
    }

}