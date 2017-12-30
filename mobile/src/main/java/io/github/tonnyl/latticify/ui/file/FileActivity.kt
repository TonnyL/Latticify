package io.github.tonnyl.latticify.ui.file

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.File
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 29/12/2017.
 */
class FileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragment = FileFragment.newInstance()

        FilePresenter(fragment, intent.getParcelableExtra(FilePresenter.KEY_EXTRA_FILE) as File)

        supportFragmentManager?.beginTransaction()
                ?.add(R.id.container, fragment, FileFragment::class.java.simpleName)
                ?.commit()

    }

}