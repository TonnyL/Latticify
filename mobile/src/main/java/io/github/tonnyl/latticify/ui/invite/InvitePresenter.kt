package io.github.tonnyl.latticify.ui.invite

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class InvitePresenter(view: InviteContract.View) : InviteContract.Presenter {

    private val mView = view

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }

}