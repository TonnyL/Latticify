package io.github.tonnyl.latticify.mvp


/**
 * Created by lizhaotailang on 19/09/2017.
 */
abstract class ListPresenter(val mView: ListContract.View) : ListContract.Presenter {

    init {
        mView.setPresenter(this)
    }

}