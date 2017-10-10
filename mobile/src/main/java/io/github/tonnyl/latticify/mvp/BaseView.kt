package io.github.tonnyl.latticify.mvp

/**
 * Created by lizhaotailang on 19/09/2017.
 */
interface BaseView<in T> {

    fun setPresenter(presenter: T)

}