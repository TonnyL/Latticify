package io.github.tonnyl.latticify.epoxy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.repository.UserPoolRepository
import io.github.tonnyl.latticify.glide.GlideLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@EpoxyModelClass(layout = R.layout.item_im)
abstract class IMModel : EpoxyModelWithHolder<IMModel.IMHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemOnClickListener: View.OnClickListener
    @EpoxyAttribute
    lateinit var channel: Channel

    override fun createNewHolder(): IMHolder = IMHolder()

    override fun bind(holder: IMHolder) {
        super.bind(holder)

        with(holder) {
            item?.setOnClickListener(itemOnClickListener)

            if (channel.isIm == true) {
                channel.user?.let {
                    UserPoolRepository.getUser(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ user ->
                                username?.text = if (user.profile.displayName.isNotEmpty()) user.profile.displayName else user.realName

                                avatar?.let {
                                    GlideLoader.loadAvatar(it, user.profile.image192)
                                }
                            }, {

                            })
                }

            }
        }
    }

    override fun unbind(holder: IMHolder) {
        super.unbind(holder)

        holder.item?.setOnClickListener(null)
    }

    class IMHolder : EpoxyHolder() {

        var item: View? = null
        var avatar: ImageView? = null
        var username: TextView? = null

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    item = findViewById(R.id.channelLayout)
                    avatar = findViewById(R.id.avatar)
                    username = findViewById(R.id.username_text_view)
                }
            }
        }

    }

}