package com.sushi.Sushi.dialog

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import com.sushi.Sushi.MainActivity
import com.sushi.Sushi.MenuFragment
import com.sushi.Sushi.R
import com.sushi.Sushi.models.PromoModel

class CitiDialog {    companion object {
    fun openDialog(context: Context) {

        val dialog = Dialog(context, R.style.CustomDialogPromo)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_item_citi)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setLayout(
            ListPopupWindow.WRAP_CONTENT,
            ListPopupWindow.WRAP_CONTENT

        )

        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        val radioGroupCiti = dialog.findViewById(R.id.radio_group_citi) as RadioGroup
        val radioButtonCiti = dialog.findViewById(R.id.radio_button_citi) as RadioButton
        val radioButtonCiti2 = dialog.findViewById(R.id.radio_button_citi2) as RadioButton
        val radioButtonCiti3 = dialog.findViewById(R.id.radio_button_citi3) as RadioButton
        val mainActivity: MainActivity = MainActivity()
        val menuFragment: MenuFragment = MenuFragment()

        var citi = String()

        radioButtonCiti.isChecked = true

        radioGroupCiti.setOnCheckedChangeListener { group, checkedId ->

            View.OnClickListener { view ->

                when (radioGroupCiti.id) {

                    R.id.radio_button_citi -> {
                        citi = "Тюмень"
                    }
                    R.id.radio_button_citi2 -> {
                        citi = "50 км"
                    }
                    R.id.radio_button_citi3 -> {
                        citi = "Другой"
                    }
                    else -> {
                    }
                }
            }
        }

//        val pref = menuFragment.activity?.getPreferences(Context.MODE_PRIVATE)
//        val saveCiti: SharedPreferences.Editor = pref!!.edit()
//        saveCiti.putString("citi", citi).toString()
//        saveCiti.apply()

        val btnSave = dialog.findViewById(R.id.btn_save_dialog_siti) as Button
        btnSave.setOnClickListener {



            dialog.cancel()
        }

        dialog.show()
    }
}
}