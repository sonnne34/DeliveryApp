package com.sushi.Sushi.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.ListPopupWindow
import com.sushi.Sushi.R

class OptionsDialog {
    companion object {
        fun openDialog(context: Context) {

            val dialog = Dialog(context, R.style.CustomDialogOptions)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_item_menu_options)
            dialog.window?.setGravity(Gravity.START)
            dialog.window?.setLayout(
                ListPopupWindow.WRAP_CONTENT,
                ListPopupWindow.MATCH_PARENT

            )

            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)

            val btnClose = dialog.findViewById(R.id.btn_close_dialog_options) as Button
            btnClose.setOnClickListener {
                dialog.cancel()
            }

            dialog.show()
        }
    }
}