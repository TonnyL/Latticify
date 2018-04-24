package io.github.tonnyl.latticify.epoxy

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.File


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
            fileDescriptionTextView?.text = fileDescriptionTextView?.context?.getString(io.github.tonnyl.latticify.R.string.search_file_description)
                    ?.format(if (file.username.isNotEmpty()) file.user else file.user, android.text.format.DateUtils.getRelativeTimeSpanString(file.created * 1000, java.lang.System.currentTimeMillis(), android.text.format.DateUtils.MINUTE_IN_MILLIS))
        }
    }

    override fun unbind(holder: SearchFileHolder) {
        super.unbind(holder)

        holder.itemLayout?.setOnClickListener(null)
    }

    override fun createNewHolder(): SearchFileHolder = SearchFileHolder()

    class SearchFileHolder : EpoxyHolder() {

        var itemLayout: View? = null
        var fileNameTextView: TextView? = null
        var fileDescriptionTextView: TextView? = null

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    itemLayout = findViewById(R.id.fileItemLayout)
                    fileNameTextView = findViewById(R.id.fileNameTextView)
                    fileDescriptionTextView = findViewById(R.id.fileDescriptionTextView)
                }
            }
        }
    }

}