package io.github.tonnyl.latticify.epoxy

import android.support.v7.widget.AppCompatTextView
import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.data.repository.UserPoolRepository
import io.github.tonnyl.latticify.glide.GlideLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 07/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_message)
abstract class MessageModel : EpoxyModelWithHolder<MessageModel.MessageHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemOnClickListener: View.OnClickListener
    @EpoxyAttribute
    lateinit var message: Message

    private val mCompositeDisposable = CompositeDisposable()

    override fun createNewHolder(): MessageHolder = MessageHolder()

    override fun bind(holder: MessageHolder) {
        super.bind(holder)

        with(holder) {
            messageContentLayout?.setOnClickListener(itemOnClickListener)
            avatarImageView?.setOnClickListener(itemOnClickListener)

            messageContentTextView?.text = message.text ?: message.attachments?.getOrNull(0)?.let { "${it.title}\n${it.text}" } ?: ""

            if (message.displayAsBot == true && message.botId != null) {
                botBadgeTextView?.visibility = android.view.View.VISIBLE
            }

            val time = DateUtils.getRelativeTimeSpanString(message.ts.substringBefore(".").toLong() * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
            if (message.edited == null) {
                msgExtraTextView?.text = msgExtraTextView?.context?.getString(R.string.message_extra_info_0, time)
            } else {
                msgExtraTextView?.text = msgExtraTextView?.context?.getString(R.string.message_extra_info_1, time)
            }

            message.icons?.let { icons ->
                usernameTextView?.text = message.username

                avatarImageView?.let {
                    GlideLoader.loadAvatar(it, icons.image72)
                }
            } ?: run {
                (message.user ?: message.comment?.user)?.let {
                    val disposable = UserPoolRepository.getUser(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                it?.let { user ->
                                    usernameTextView?.text = user.profile.displayName

                                    avatarImageView?.let {
                                        GlideLoader.loadAvatar(it, user.profile.image192)
                                    }
                                }
                            }, {

                            })
                    mCompositeDisposable.add(disposable)
                }
            }
        }
    }

    override fun unbind(holder: MessageHolder) {
        super.unbind(holder)
        holder.messageContentLayout?.setOnClickListener(null)
        holder.avatarImageView?.setOnClickListener(null)

        mCompositeDisposable.clear()
    }

    class MessageHolder : EpoxyHolder() {

        var itemLayout: LinearLayout? = null
        var avatarImageView: ImageView? = null
        var usernameTextView: AppCompatTextView? = null
        var messageContentLayout: LinearLayout? = null
        var messageContentTextView: AppCompatTextView? = null
        var msgExtraTextView: AppCompatTextView? = null
        var botBadgeTextView: TextView? = null

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    itemLayout = findViewById(R.id.messageLayout)
                    avatarImageView = findViewById(R.id.avatar_image_view)
                    usernameTextView = findViewById(R.id.username_text_view)
                    messageContentLayout = findViewById(R.id.messageContentLayout)
                    messageContentTextView = findViewById(R.id.messageContentTextView)
                    msgExtraTextView = findViewById(R.id.msgExtraTextView)
                    botBadgeTextView = findViewById(R.id.botBadgeTextView)
                }
            }
        }

    }

}