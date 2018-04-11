package io.github.tonnyl.latticify.ui.loading

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        val fragment = LoadingFragment.newInstance()

        LoadingPresenter(fragment)

        supportFragmentManager.beginTransaction()
                ?.replace(R.id.container, fragment, LoadingFragment::class.java.simpleName)
                ?.commit()

    }

}