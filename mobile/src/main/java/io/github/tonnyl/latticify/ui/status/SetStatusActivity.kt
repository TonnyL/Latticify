package io.github.tonnyl.latticify.ui.status

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class SetStatusActivity : AppCompatActivity() {

    private lateinit var mSetStatusFragment: SetStatusFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mSetStatusFragment = SetStatusFragment.newInstance()

        SetStatusPresenter(mSetStatusFragment)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mSetStatusFragment, SetStatusFragment::class.java.simpleName)
                .commit()

    }

}