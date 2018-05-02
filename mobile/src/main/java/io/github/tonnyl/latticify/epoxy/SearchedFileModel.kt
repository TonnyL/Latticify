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
import io.github.tonnyl.latticify.data.File
import io.github.tonnyl.latticify.data.repository.UserPoolRepository
import io.github.tonnyl.latticify.glide.GlideLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by lizhaotailang on 14/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_search_file)
abstract class SearchedFileModel : EpoxyModelWithHolder<SearchedFileModel.SearchFileHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemClickListener: View.OnClickListener
    @EpoxyAttribute
    lateinit var file: File

    override fun bind(holder: SearchFileHolder) {
        super.bind(holder)

        with(holder) {
            itemLayout?.setOnClickListener(itemClickListener)
            fileNameTextView?.text = file.name

            UserPoolRepository.getUser(file.user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user ->
                        avatarImageView?.let {
                            GlideLoader.loadAvatar(it, user.profile.image192)
                        }

                        fileDescriptionTextView?.text = fileDescriptionTextView?.context?.getString(R.string.search_file_description)
                                ?.format(user.profile.displayName, DateUtils.getRelativeTimeSpanString(file.created * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS))
                    }, {

                    })

        }
    }

    override fun unbind(holder: SearchFileHolder) {
        super.unbind(holder)

        holder.itemLayout?.setOnClickListener(null)
    }

    override fun createNewHolder(): SearchFileHolder = SearchFileHolder()

    class SearchFileHolder : EpoxyHolder() {

        var itemLayout: View? = null
        var avatarImageView: ImageView? = null
        var fileNameTextView: TextView? = null
        var fileDescriptionTextView: TextView? = null

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    itemLayout = findViewById(R.id.fileItemLayout)
                    avatarImageView = findViewById(R.id.avatar_image_view)
                    fileNameTextView = findViewById(R.id.fileNameTextView)
                    fileDescriptionTextView = findViewById(R.id.fileDescriptionTextView)
                }
            }
        }
    }

}