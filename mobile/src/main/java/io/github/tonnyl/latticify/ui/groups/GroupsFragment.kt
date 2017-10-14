package io.github.tonnyl.latticify.ui.groups

import io.github.tonnyl.latticify.mvp.ListFragment

/**
 * Created by lizhaotailang on 12/10/2017.
 */
class GroupsFragment : ListFragment() {

    override var ignoreScrollChange: Boolean = true

    companion object {
        @JvmStatic
        fun newInstance() = GroupsFragment()
    }

}