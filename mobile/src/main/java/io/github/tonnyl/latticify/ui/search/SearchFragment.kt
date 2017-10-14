package io.github.tonnyl.latticify.ui.search

import io.github.tonnyl.latticify.mvp.ListFragment

/**
 * Created by lizhaotailang on 24/09/2017.
 */
class SearchFragment : ListFragment() {

    override var ignoreScrollChange: Boolean = true

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

}