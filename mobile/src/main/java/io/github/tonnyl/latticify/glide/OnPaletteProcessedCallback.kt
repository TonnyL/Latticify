package io.github.tonnyl.latticify.glide

import android.support.v7.graphics.Palette

/**
 * Created by lizhaotailang on 23/09/2017.
 */
interface OnPaletteProcessedCallback {

    /**
     * The [Palette] finishes its work successfully.
     */
    fun OnPaletteGenerated(palette: Palette?)

    /**
     * The [Palette] finished its work with a failure.
     */
    fun OnPaletteNotAvailable()

}