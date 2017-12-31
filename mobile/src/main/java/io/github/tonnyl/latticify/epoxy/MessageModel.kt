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

    override fun bind(holder: MessageHolder?) {
        super.bind(holder)

        holder?.let {
            with(it) {
                messageContentLayout.setOnClickListener(itemOnClickListener)
                messageContentLayout.setOnCreateContextMenuListener(itemOnCreateContextMenuListener)

                usernameTextView.text = message.user ?: message.username ?: ""
                messageContentTextView.text = message.text ?: message.attachments?.getOrNull(0)?.let { "${it.title}\n${it.text}" } ?: run { "" }

                message.botId?.let { botBadgeTextView.visibility = View.VISIBLE }

                timeTextView.text = "${message.reactions?.let { if (it.size == 1) "1 reaction • " else "${it.size} reactions • " } ?: ""}${DateUtils.getRelativeTimeSpanString(message.ts.substringBefore(".").toLong() * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)}"

                message.file?.let { file ->
                    if (file.mimeType == "image/jpeg") {
                        imageMessageImageView.visibility = View.VISIBLE
                        GlideLoader.loadNormal(imageMessageImageView, file.thumb1024 ?: file.thumb720 ?: file.thumb360)
                    }
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
        lateinit var timeTextView: AppCompatTextView
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
                    timeTextView = findViewById(R.id.timeTextView)
                    imageMessageImageView = findViewById(R.id.imageMessageImageView)
                    botBadgeTextView = findViewById(R.id.botBadgeTextView)
                    placeHolderView = findViewById(R.id.placeHolderView)
                }
            }
        }

    }
}