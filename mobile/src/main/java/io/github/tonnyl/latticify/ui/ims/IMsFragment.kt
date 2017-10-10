package io.github.tonnyl.latticify.ui.ims

import io.github.tonnyl.latticify.mvp.ListFragment

/**
 * Created by lizhaotailang on 06/10/2017.
 */
class IMsFragment : ListFragment() {

    override var ignoreScrollChange: Boolean = false

    companion object {
        @JvmStatic
        fun newInstance(): IMsFragment = IMsFragment()
    }

}