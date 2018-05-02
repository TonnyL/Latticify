package io.github.tonnyl.latticify.ui.file

import android.app.DownloadManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateUtils
import android.view.*
import android.widget.Toast
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.File
import io.github.tonnyl.latticify.data.FileWrapper
import io.github.tonnyl.latticify.data.repository.UserPoolRepository
import io.github.tonnyl.latticify.glide.GlideLoader
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.ui.profile.ProfileActivity
import io.github.tonnyl.latticify.ui.profile.ProfilePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_file.*

/**
 * Created by lizhaotailang on 29/12/2017.
 */
class FileFragment : Fragment(), FileContract.View {

    private lateinit var mPresenter: FileContract.Presenter
    private val mCompositeDisposable = CompositeDisposable()
    private var mFile: File? = null

    companion object {
        @JvmStatic
        fun newInstance() = FileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commentEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrBlank()) {
                    sendMessageImageView.clearColorFilter()
                } else {
                    context?.let {
                        sendMessageImageView.setColorFilter(it.getColor(R.color.colorPrimary))
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        sendMessageImageView.setOnClickListener {
            if (!commentEditText.text.isNullOrBlank()) {
                mPresenter.comment(commentEditText.text.toString())
            }
        }

        mPresenter.subscribe()

        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()

        mCompositeDisposable.clear()
    }

    override fun setPresenter(presenter: FileContract.Presenter) {
        mPresenter = presenter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_file, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        menu?.getItem(0)?.setTitle(if (mFile?.isStarred == false) R.string.star else R.string.unstar)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
            R.id.action_star_file -> {
                mPresenter.starUnstar()
            }
            R.id.action_copy_link -> {
                copyLink()
            }
            R.id.action_download -> {
                downloadFile()
            }
            R.id.action_delete -> {
                showDeleteDialog()
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
        mFile = file

        file_title_text_view.text = file.title

        val date = DateUtils.formatDateTime(context, file.created * 1000, DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_TIME)

        var size = file.size / 1024.0
        val sizeText: String
        if (size >= 1024) {
            size /= 1024
            sizeText = getString(R.string.mb).format(size)
        } else {
            sizeText = getString(R.string.kb).format(size)
        }

        file_info_text_view.text = getString(R.string.file_info, date, file.prettyType, sizeText, getString(if (file.isPublic) R.string.public_file else R.string.private_file))
    }

    override fun showContent(wrapper: FileWrapper) {
        if (wrapper.contentHtml == null && wrapper.contentHighlightHtml == null && wrapper.contentHighlightCss == null) {
            content_web_view.visibility = View.GONE
        } else {
            val html = """
            <!DOCTYPE html>
            <html>
            <head>
            <style>
            ${wrapper.contentHighlightCss}
            </style>
            </head>
            <body>
            ${wrapper.contentHtml ?: wrapper.contentHighlightHtml}
            </body>
            </html>
            """

            content_web_view.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
        }

        if (wrapper.file.mimeType == "image/jpeg"
                || wrapper.file.mimeType == "image/png"
                || wrapper.file.mimeType == "image/gif"
                || wrapper.file.mimeType == "image/bmp"
                || wrapper.file.mimeType == "image/webp") {
            fileImageView.visibility = View.VISIBLE

            GlideLoader.loadNormal(fileImageView, wrapper.file.urlPrivate)
        } else {
            fileImageView.visibility = View.GONE
        }

        val disposable = UserPoolRepository.getUser(wrapper.file.user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    user_name_text_view.text = user.profile.displayName

                    GlideLoader.loadAvatar(avatar, user.profile.image192)

                    userLayout.setOnClickListener {
                        startActivity(Intent(context, ProfileActivity::class.java).apply {
                            putExtra(ProfilePresenter.KEY_EXTRA_USER, user)
                        })
                    }
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    override fun finishActivity() {
        activity?.onBackPressed()
    }

    override fun showDeleteFileError() {
        Toast.makeText(context, getString(R.string.failed_to_delete_file), Toast.LENGTH_SHORT).show()
    }

    override fun showFileStarred(starred: Boolean) {
        Toast.makeText(context, if (starred) R.string.msg_starred else R.string.msg_unstarred, Toast.LENGTH_SHORT).show()

        mFile?.isStarred = starred
    }

    override fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showCommendAdded() {
        Toast.makeText(context, getString(R.string.comment_added), Toast.LENGTH_SHORT).show()

        commentEditText.setText("")
    }

    override fun showFileDeleted() {
        Toast.makeText(context, getString(R.string.file_deleted), Toast.LENGTH_SHORT).show()
    }

    private fun copyLink() {
        val manager = context?.getSystemService(Context.CLIPBOARD_SERVICE)?.let {
            it as ClipboardManager
        } ?: run {
            return
        }
        val clipData = ClipData.newPlainText("text", mFile?.permalinkPublic)
        manager.primaryClip = clipData

        Toast.makeText(context, R.string.copied, Toast.LENGTH_SHORT).show()
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

    private fun downloadFile() {
        mFile?.let {
            val request = DownloadManager.Request(Uri.parse(it.urlPrivateDownload))
            request.addRequestHeader("Authorization", "Bearer ${RetrofitClient.mToken}")
            request.setTitle(it.title)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setAllowedOverRoaming(false)
            request.setDescription(getString(R.string.download_desc).format(it.title))
            request.setVisibleInDownloadsUi(true)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, it.title)

            (context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
        }
    }

}