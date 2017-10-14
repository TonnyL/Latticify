package io.github.tonnyl.latticify.ui.status

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class SetStatusPresenter(view: SetStatusContract.View) : SetStatusContract.Presenter {

    private val mView = view

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }

}