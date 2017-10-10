package io.github.tonnyl.latticify.ui.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.UsersProfile
import io.github.tonnyl.latticify.data.repository.UsersProfileRepository
import io.github.tonnyl.latticify.epoxy.ProfileItemModel_
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.email

/**
 * Created by lizhaotailang on 08/10/2017.
 */
class ProfilePresenter(view: ProfileContract.View) : ProfileContract.Presenter {

    private val mView = view

    private var mUserProfile: UsersProfile? = null
    private var mUserId: String? = null
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    constructor(view: ProfileContract.View, userId: String) : this(view) {
        mUserId = userId
    }

    constructor(view: ProfileContract.View, usersProfile: UsersProfile) : this(view) {
        mUserProfile = usersProfile
    }

    companion object {
        @JvmField
        val KEY_EXTRA_USER_PROFILE = "KEY_EXTRA_USER_PROFILE"
        @JvmField
        val KEY_EXTRA_USER_ID = "KEY_EXTRA_USER_ID"
    }

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        mUserProfile?.let {
            mView.showTitle(it.realName)
            mView.showAvatar(it.image512)
            mView.showData(generateEpoxyModels(it))
        }

        fetchData()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchData() {
        val disposable = UsersProfileRepository.getUsersProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mView.showTitle(it.userProfile.realName)
                        mView.showAvatar(it.userProfile.image512)
                        mView.showData(generateEpoxyModels(it.userProfile))
                    }
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun generateEpoxyModels(usersProfile: UsersProfile): Collection<EpoxyModel<*>> =
            with(usersProfile) {
                arrayListOf<ProfileItemModel_>()
                        .apply {
                            with(usersProfile) {
                                add(ProfileItemModel_()
                                        .itemPair(Pair(R.string.display_name, usersProfile.displayName))
                                        .icon1ResId(R.drawable.ic_person_black_24dp)
                                        .itemClickListener(View.OnClickListener {

                                        }))

                                if (statusText.isNotEmpty()) {
                                    add(ProfileItemModel_()
                                            .itemPair(Pair(R.string.status, "$statusEmoji $statusText"))
                                            .icon1ResId(R.drawable.ic_work_black_24dp)
                                            .itemClickListener(View.OnClickListener {

                                            }))
                                }

                                add(ProfileItemModel_()
                                        .itemPair(Pair(R.string.email, usersProfile.email))
                                        .icon1ResId(R.drawable.ic_email_black_24dp)
                                        .itemClickListener(View.OnClickListener {
                                            it.context.email(usersProfile.email)
                                        }))

                                phone?.let {
                                    add(ProfileItemModel_()
                                            .itemPair(Pair(R.string.phone, it))
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
                                            .itemPair(Pair(R.string.skype, it))
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
                            }

                        }
            }

}