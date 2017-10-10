package io.github.tonnyl.latticify.ui.channels

import io.github.tonnyl.latticify.mvp.ListFragment

/**
 * Created by lizhaotailang on 24/09/2017.
 */
class ChannelsFragment : ListFragment() {

    override var ignoreScrollChange: Boolean = false

    companion object {
        @JvmStatic
        fun newInstance(): ChannelsFragment = ChannelsFragment()
    }

}