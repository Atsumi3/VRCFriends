package jp.bizen.vrcfriends.android.presentation.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import jp.bizen.vrcfriends.android.extensions.lazyWithArgs

class YesNoDialogFragment : DialogFragment() {
    private val title: String by lazyWithArgs(EXTRA_TITLE)
    private val message: String by lazyWithArgs(EXTRA_MESSAGE)
    private val requestCode: Int by lazyWithArgs(EXTRA_REQUEST_CODE)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val presentationTitle = if (title.isEmpty()) null else title
        return AlertDialog.Builder(requireContext())
            .setTitle(presentationTitle)
            .setMessage(message)
            .setPositiveButton(android.R.string.yes) { _, _ ->
                if (activity is DialogCallback) {
                    (activity as DialogCallback).result(requestCode, Activity.RESULT_OK)
                }
                dismiss()
            }
            .setNegativeButton(android.R.string.no) { _, _ ->
                if (activity is DialogCallback) {
                    (activity as DialogCallback).result(requestCode, Activity.RESULT_CANCELED)
                }
                dismiss()
            }
            .create()
    }

    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_MESSAGE = "message"
        private const val EXTRA_REQUEST_CODE = "request_code"

        fun show(
            fragmentManager: FragmentManager,
            title: String = "",
            message: String,
            requestCode: Int
        ) {
            val dialog = YesNoDialogFragment()
            dialog.arguments = Bundle().apply {
                putString(EXTRA_TITLE, title)
                putString(EXTRA_MESSAGE, message)
                putInt(EXTRA_REQUEST_CODE, requestCode)
            }
            dialog.show(fragmentManager, dialog.javaClass.simpleName)
        }
    }
}