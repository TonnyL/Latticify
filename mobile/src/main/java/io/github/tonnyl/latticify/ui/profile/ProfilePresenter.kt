package io.github.tonnyl.latticify.ui.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.data.repository.IMRepository
import io.github.tonnyl.latticify.data.repository.UsersRepository
import io.github.tonnyl.latticify.epoxy.ProfileItemModel_
import io.github.tonnyl.latticify.util.AccessTokenManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by lizhaotailang on 08/10/2017.
 */
class ProfilePresenter(private val mView: ProfileContract.View) : ProfileContract.Presenter {

    private var mUser: User? = null
    private var mUserId: String? = null
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    constructor(view: ProfileContract.View, userId: String) : this(view) {
        mUserId = userId
    }

    constructor(view: ProfileContract.View, user: User) : this(view) {
        mUser = user
        mUserId = mUser?.id
    }

    companion object {
        @JvmField
        val KEY_EXTRA_USER = "KEY_EXTRA_USER"
        @JvmField
        val KEY_EXTRA_USER_ID = "KEY_EXTRA_USER_ID"
    }

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        mUser?.let {
            mView.showTitle(it.profile.displayName)
            mView.showAvatar(it.profile.image512 ?: it.profile.image192)
            mView.showData(generateEpoxyModels(it))
        } ?: run {
            fetchData()
        }

        mView.setIsMe(mUserId == AccessTokenManager.getAccessToken().userId)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchData() {
        val disposable = UsersRepository.info(userId = mUserId ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mView.showTitle(it.user.profile.displayName)
                        mView.showAvatar(it.user.profile.image512 ?: it.user.profile.image192)
                        mView.showData(generateEpoxyModels(it.user))
                    }
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun generateEpoxyModels(user: User): Collection<EpoxyModel<*>> =
            with(user) {
                arrayListOf<ProfileItemModel_>().apply {
                    with(user.profile) {
                        add(ProfileItemModel_()
                                .descriptionId(R.string.real_name)
                                .title(realName)
                                .icon1ResId(R.drawable.ic_person_black_24dp)
                                .itemClickListener(View.OnClickListener {

                                }))

                        statusText?.let {
                            add(ProfileItemModel_()
                                    .descriptionId(R.string.status)
                                    .title("$statusEmoji $statusText")
                                    .icon1ResId(R.drawable.ic_work_black_24dp)
                                    .itemClickListener(View.OnClickListener {

                                    }))
                        }

                        email?.let {
                            add(ProfileItemModel_()
                                    .descriptionId(R.string.email)
                                    .title(email)
                                    .icon1ResId(R.drawable.ic_email_black_24dp)
                                    .itemClickListener(View.OnClickListener {
                                        try {
                                            it.context.startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email")))
                                        } catch (e: ActivityNotFoundException) {
                                            e.printStackTrace()
                                        }
                                    }))
                        }

                        phone?.let {
                            add(ProfileItemModel_()
                                    .descriptionId(R.string.phone)
                                    .title(it)
                                    .icon1ResId(R.drawable.ic_phone_black_24dp)
                                    .itemClickListener(View.OnClickListener {
                                        try {
                                            it.context.startActivity(Intent(Intent.ACTION_DIAL).apply { data = Uri.parse("tel:$phone") })
                                        } catch (e: ActivityNotFoundException) {
                                            e.printStackTrace()
                                        }
                                    }))
                        }

                        skype?.let {
                            add(ProfileItemModel_()
                                    .descriptionId(R.string.skype)
                                    .title(it)
                                    .icon1ResId(R.drawable.ic_skype_black_24dp)
                                    .itemClickListener(View.OnClickListener {
                                        try {
                                            it.context.startActivity(
                                                    Intent(Intent.ACTION_VIEW).apply { data = Uri.parse("skype:" + skype) })
                                        } catch (e: ActivityNotFoundException) {
                                            e.printStackTrace()
                                        }
                                    }))
                        }

                        locale?.let {
                            add(ProfileItemModel_()
                                    .descriptionId(R.string.locale)
                                    .title(it)
                                    .icon1ResId(R.drawable.ic_location_on_black_24dp)
                                    .itemClickListener(View.OnClickListener {

                                    }))
                        }

                        val hours = (TimeZone.getDefault().rawOffset / 1000 - Math.abs(tzOffset)) / 3600
                        val context = (mView as Fragment).context
                        add(ProfileItemModel_()
                                .descriptionId(R.string.timezone)
                                .title(when {
                                    hours > 0 -> {
                                        if (hours == 1L)
                                            context?.getString(R.string.timezone_behind_1_hour)?.format(tzLabel) ?: ""
                                        else
                                            context?.getString(R.string.timezone_behind_n_hours)?.format(hours, tzLabel) ?: ""
                                    }
                                    hours == 0L -> {
                                        (mView as Fragment).context?.getString(R.string.timezone_same)?.format(tzLabel) ?: ""
                                    }
                                    else -> {
                                        if (hours == -1L)
                                            context?.getString(R.string.timezone_ahead_1_hour)?.format(tzLabel) ?: ""
                                        else
                                            context?.getString(R.string.timezone_ahead_n_hours)?.format(-hours, tzLabel) ?: ""
                                    }
                                })
                                .icon1ResId(R.drawable.ic_access_time_black_24dp)
                                .itemClickListener(View.OnClickListener {

                                }))


                    }

                }
            }

    override fun openIm() {
        mUserId?.let {
            val disposable = IMRepository.open(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.ok) {
                            mView.gotoChannel(it.channel)
                        }
                    }, {
                        it.printStackTrace()
                    })
            mCompositeDisposable.add(disposable)
        }
    }

}