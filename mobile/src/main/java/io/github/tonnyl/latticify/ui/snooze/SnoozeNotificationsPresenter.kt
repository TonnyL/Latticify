package io.github.tonnyl.latticify.ui.snooze

import io.github.tonnyl.latticify.data.repository.DndRepository
import io.github.tonnyl.latticify.data.repository.UsersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class SnoozeNotificationsPresenter(view: SnoozeNotificationsContract.View) : SnoozeNotificationsContract.Presenter {

    private val mView = view
    private val mCompositeDisposable = CompositeDisposable()

    private val dndRepository = DndRepository()

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        val disposable = UsersRepository.identity()
                .flatMap {
                    dndRepository.info(it.user.id)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {

                    }
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun endSnooze() {
        val disposable = dndRepository.endSnooze()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    override fun setSnooze(minutes: Int) {
        val disposable = dndRepository.setSnooze(minutes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                })
        mCompositeDisposable.add(disposable)
    }

}