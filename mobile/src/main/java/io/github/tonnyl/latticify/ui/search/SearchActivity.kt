package io.github.tonnyl.latticify.ui.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R

/**
 * Created by lizhaotailang on 24/09/2017.
 */
class SearchActivity : AppCompatActivity() {

    private lateinit var mSearchFragment: SearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        mSearchFragment = SearchFragment.newInstance()

        SearchPresenter(mSearchFragment)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mSearchFragment, SearchFragment::class.java.simpleName)
                .commit()

    }

}