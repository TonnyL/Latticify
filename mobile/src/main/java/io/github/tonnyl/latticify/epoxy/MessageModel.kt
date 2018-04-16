package io.github.tonnyl.latticify.epoxy

import android.support.v7.widget.AppCompatTextView
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
import io.github.tonnyl.latticify.glide.GlideLoader

/**
 * Created by lizhaotailang on 07/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_message)
abstract class MessageModel : EpoxyModelWithHolder<MessageModel.MessageHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemOnClickListener: View.OnClickListener
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemOnCreateContextMenuListener: View.OnCreateContextMenuListener
    @EpoxyAttribute
    lateinit var message: Message
    @EpoxyAttribute
    var replyMessage: Message? = null
    @EpoxyAttribute
    var directMessage: Boolean = false

    override fun createNewHolder(): MessageHolder = MessageHolder()

    override fun bind(holder: MessageHolder) {
        super.bind(holder)

        with(holder) {
            messageContentLayout.setOnClickListener(itemOnClickListener)
            messageContentLayout.setOnCreateContextMenuListener(itemOnCreateContextMenuListener)

            usernameTextView.text = message.user ?: message.username ?: ""
            messageContentTextView.text = message.text ?: message.attachments?.getOrNull(0)?.let { "${it.title}\n${it.text}" } ?: run { "" }

            message.botId?.let { botBadgeTextView.visibility = android.view.View.VISIBLE }

            val time = android.text.format.DateUtils.getRelativeTimeSpanString(message.ts.substringBefore(".").toLong() * 1000, java.lang.System.currentTimeMillis(), android.text.format.DateUtils.MINUTE_IN_MILLIS)
            if (message.edited == null && message.reactions == null) {
                msgExtraTextView.text = msgExtraTextView.context.getString(R.string.message_extra_info_0, time)
            } else if (message.edited != null && message.reactions == null) {
                msgExtraTextView.text = msgExtraTextView.context.getString(R.string.message_extra_info_1, time)
            } else if (message.edited == null && message.reactions != null) {
                val reactionCount = msgExtraTextView.context.resources.getQuantityString(R.plurals.reaction_count, message.reactions!!.size, message.reactions!!.size)
                msgExtraTextView.text = msgExtraTextView.context.getString(R.string.message_extra_info_2, reactionCount, time)
            } else {
                val reactionCount = msgExtraTextView.context.resources.getQuantityString(R.plurals.reaction_count, message.reactions!!.size, message.reactions!!.size)
                msgExtraTextView.text = msgExtraTextView.context.getString(R.string.message_extra_info_3, reactionCount, time)
            }

            message.file?.let { file ->
                if (file.mimeType == "image/png") {
                    imageMessageImageView.visibility = android.view.View.VISIBLE
                    GlideLoader.loadNormal(imageMessageImageView, "https://files.slack.com/files-tmb/T4WLRK1UL-FA730GDLJ-6961834134/screen_shot_2018-04-15_at_10.09.06_pm_360.png")
                }
            }
        }
    }

    class MessageHolder : EpoxyHolder() {

        lateinit var itemLayout: LinearLayout
        lateinit var avatarImageView: ImageView
        lateinit var usernameTextView: AppCompatTextView
        lateinit var messageContentLayout: LinearLayout
        lateinit var messageContentTextView: AppCompatTextView
        lateinit var msgExtraTextView: AppCompatTextView
        lateinit var imageMessageImageView: ImageView
        lateinit var botBadgeTextView: TextView
        lateinit var placeHolderView: View

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    itemLayout = findViewById(R.id.messageLayout)
                    avatarImageView = findViewById(R.id.avatar_image_view)
                    usernameTextView = findViewById(R.id.username_text_view)
                    messageContentLayout = findViewById(R.id.messageContentLayout)
                    messageContentTextView = findViewById(R.id.messageContentTextView)
                    msgExtraTextView = findViewById(R.id.msgExtraTextView)
                    imageMessageImageView = findViewById(R.id.imageMessageImageView)
                    botBadgeTextView = findViewById(R.id.botBadgeTextView)
                    placeHolderView = findViewById(R.id.placeHolderView)
                }
            }
        }

    }
}