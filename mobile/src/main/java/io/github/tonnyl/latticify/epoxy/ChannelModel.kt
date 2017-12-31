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

/**
 * Created by lizhaotailang on 24/09/2017.
 */
@EpoxyModelClass(layout = R.layout.item_channel_im)
abstract class ChannelModel : EpoxyModelWithHolder<ChannelModel.ChannelHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemOnClickListener: View.OnClickListener
    @EpoxyAttribute
    lateinit var channel: Channel

    override fun createNewHolder(): ChannelHolder = ChannelHolder()

    override fun bind(holder: ChannelHolder?) {
        super.bind(holder)

        holder?.let {
            with(it) {
                item?.setOnClickListener(itemOnClickListener)
                username?.text = channel.name

                if (channel.latest?.type == "message") {
                    summary?.text = summary?.context?.getString(R.string.channel_summary)?.format(channel.latest?.user, channel.latest?.text ?: channel.latest?.attachments?.getOrNull(0)?.title ?: "")
                } else {
                    summary?.text = channel.purpose?.value ?: channel.topic?.value
                }

                avatar?.setImageResource(when {
                    channel.isPrivate == true -> {
                        R.drawable.ic_lock_black_24dp
                    }
                    channel.isChannel == true -> {
                        R.drawable.ic_hashtag_black_24dp
                    }
                    else -> {
                        R.drawable.ic_message_black_24dp
                    }
                })

                if (channel.isPrivate == true) {
                    avatar?.setImageResource(R.drawable.ic_lock_black_24dp)
                }

                if (channel.isIm == true) {
                    username?.text = channel.user
                }

                channel.unreadCountDisplay?.let {
                    if (it != 0) {
                        unreadCount?.visibility = View.VISIBLE
                        unreadCount?.text = channel.unreadCount?.toString()
                    }
                }
            }
        }
    }

    class ChannelHolder : EpoxyHolder() {

        var item: View? = null
        var avatar: ImageView? = null
        var username: TextView? = null
        var time: TextView? = null
        var summary: TextView? = null
        var unreadCount: TextView? = null

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    item = findViewById(R.id.channelLayout)
                    avatar = findViewById(R.id.avatar)
                    username = findViewById(R.id.username_text_view)
                    time = findViewById(R.id.timeTextView)
                    summary = findViewById(R.id.messageSummaryTextView)
                    unreadCount = findViewById(R.id.unreadCountTextView)
                }
            }
        }

    }

}