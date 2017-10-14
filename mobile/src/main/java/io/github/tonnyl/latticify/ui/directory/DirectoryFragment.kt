package io.github.tonnyl.latticify.ui.directory

import io.github.tonnyl.latticify.mvp.ListFragment

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class DirectoryFragment : ListFragment() {

    override var ignoreScrollChange: Boolean = true

    companion object {
        @JvmStatic
        fun newInstance() = DirectoryFragment()
    }

}