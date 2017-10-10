package io.github.tonnyl.latticify.ui.starred

import io.github.tonnyl.latticify.mvp.ListFragment

/**
 * Created by lizhaotailang on 09/10/2017.
 */
class StarredItemsFragment : ListFragment() {

    override var ignoreScrollChange: Boolean = false

    companion object {
        @JvmStatic
        fun newInstance(): StarredItemsFragment = StarredItemsFragment()
    }

}