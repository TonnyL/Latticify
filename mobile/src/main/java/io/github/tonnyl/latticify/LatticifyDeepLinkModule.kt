package io.github.tonnyl.latticify

import com.airbnb.deeplinkdispatch.DeepLinkModule

/**
 * Created by lizhaotailang on 28/12/2017.
 *
 * The deep link module. For every class annotated [DeepLinkModule],
 * [DeepLinkDispatch](com.airbnb.deeplinkdispatch) will generate a `Loader` class,
 * which contains a registry of all the [DeepLinkModule] annotations.
 */
@DeepLinkModule
class LatticifyDeepLinkModule