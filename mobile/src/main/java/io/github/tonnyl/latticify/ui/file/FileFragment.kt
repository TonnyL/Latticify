package io.github.tonnyl.latticify.ui.file

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.format.DateUtils
import android.view.*
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.File
import io.github.tonnyl.latticify.data.FileWrapper
import kotlinx.android.synthetic.main.fragment_file.*
import kotlinx.android.synthetic.main.item_message.*

/**
 * Created by lizhaotailang on 29/12/2017.
 */
class FileFragment : Fragment(), FileContract.View {

    private lateinit var mPresenter: FileContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance() = FileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPresenter.subscribe()

        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: FileContract.Presenter) {
        mPresenter = presenter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_file, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
            R.id.action_add_reaction -> {

            }
            R.id.action_comment -> {

            }
            R.id.action_star_file -> {

            }
            R.id.action_share_file -> {

            }
            R.id.action_copy_link -> {
                mPresenter.copyLink()
            }
            R.id.action_delete -> {
                showDeleteDialog()
            }
            R.id.action_open_in_browser -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setLoadingIndicator(loading: Boolean) {
        if (loading) {
            loading_animation_view.visibility = View.VISIBLE
            content_web_view.visibility = View.GONE
        } else {
            loading_animation_view.cancelAnimation()
            loading_animation_view.visibility = View.GONE
            content_web_view.visibility = View.VISIBLE
        }
    }

    override fun showFetchDataError() {

    }

    override fun showFileData(file: File) {
        user_name_text_view.text = file.username
        file_title_text_view.text = file.title

        file_info_text_view.text = DateUtils.formatDateTime(context, file.created * 1000,
                DateUtils.FORMAT_SHOW_YEAR or
                        DateUtils.FORMAT_SHOW_DATE or
                        DateUtils.FORMAT_SHOW_WEEKDAY or
                        DateUtils.FORMAT_SHOW_TIME)

    }

    override fun showContent(wrapper: FileWrapper) {
        val html = """
            <!DOCTYPE html>
            <html>
            <head>
            <style>
            ${wrapper.contentHighlightCss}
            </style>
            </head>
            <body>
            ${wrapper.contentHighlightHtml}
            </body>
            </html>
            """
        content_web_view.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
    }

    override fun finishActivity() {
        activity?.onBackPressed()
    }

    override fun showDeleteFileError() {
        Snackbar.make(username_text_view, getString(R.string.failed_to_delete_file), Snackbar.LENGTH_SHORT).show()
    }

    override fun copyLink(link: String) {
        val manager = context?.getSystemService(Context.CLIPBOARD_SERVICE)?.let {
            it as ClipboardManager
        } ?: run {
            return
        }
        val clipData = ClipData.newPlainText("text", link)
        manager.primaryClip = clipData

        Snackbar.make(username_text_view, R.string.copied, Snackbar.LENGTH_SHORT).show()
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(context ?: return)
                .setTitle(getString(R.string.dialog_delete_file_title))
                .setMessage(getString(R.string.dialog_delete_file_msg))
                .setNegativeButton(getString(R.string.button_cancel_text), { d, _ ->
                    d.dismiss()
                })
                .setPositiveButton(getString(R.string.button_ok_text), { d, _ ->
                    d.dismiss()

                    mPresenter.delete()
                })
                .create()
                .show()
    }

}