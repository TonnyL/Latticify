package io.github.tonnyl.latticify.epoxy

import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.SearchedMessageMatch
import io.github.tonnyl.latticify.data.repository.UserPoolRepository
import io.github.tonnyl.latticify.glide.GlideLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 14/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_search_message)
abstract class SearchedMessageModel : EpoxyModelWithHolder<SearchedMessageModel.SearchMessageHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemClickListener: View.OnClickListener
    @EpoxyAttribute
    lateinit var message: SearchedMessageMatch

    override fun createNewHolder(): SearchMessageHolder = SearchMessageHolder()

    override fun bind(holder: SearchMessageHolder) {
        super.bind(holder)

        with(holder) {
            itemLayout?.setOnClickListener(itemClickListener)

            timeTextView?.text = DateUtils.getRelativeTimeSpanString(message.ts.substringBefore(".").toLong() * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
            messageContentTextView?.text = message.text

            UserPoolRepository.getUser(message.user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user ->
                        avatarImageView?.let {
                            GlideLoader.loadAvatar(it, user.profile.image192)
                        }

                        if (message.channel.name.startsWith("U")) {
                            messageToTextView?.text = if (user.profile.displayName.isNotEmpty()) user.profile.displayName else user.name
                        }

                        usernameTextView?.text = if (user.profile.displayName.isNotEmpty()) user.profile.displayName else user.name

                    }, {

                    })
        }
    }

    override fun unbind(holder: SearchMessageHolder) {
        super.unbind(holder)

        holder.itemLayout?.setOnClickListener(null)
    }

    class SearchMessageHolder : EpoxyHolder() {

        var itemLayout: View? = null
        var messageToTextView: TextView? = null
        var timeTextView: TextView? = null
        var avatarImageView: ImageView? = null
        var usernameTextView: TextView? = null
        var messageContentTextView: TextView? = null

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    itemLayout = findViewById(R.id.messageLayout)
                    messageToTextView = findViewById(R.id.messageToTextView)
                    timeTextView = findViewById(R.id.timeTextView)
                    avatarImageView = findViewById(R.id.avatar_image_view)
                    usernameTextView = findViewById(R.id.username_text_view)
                    messageContentTextView = findViewById(R.id.messageContentTextView)
                }
            }
        }
    }

}