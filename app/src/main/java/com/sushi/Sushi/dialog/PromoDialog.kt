package com.sushi.Sushi.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.ListPopupWindow
import com.sushi.Sushi.R
import com.sushi.Sushi.models.PromoModel
import com.sushi.Sushi.service.LoadImage

class PromoDialog {    companion object {
    fun openDialog(context: Context, promoModel: PromoModel, position: Int, promoList: ArrayList<PromoModel>) {

        val dialog = Dialog(context, R.style.CustomDialogPromo)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_item_promo_description)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setLayout(
            ListPopupWindow.WRAP_CONTENT,
            ListPopupWindow.WRAP_CONTENT

        )

        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        promoList[position]
        val imgPromoDescript = dialog.findViewById(R.id.img_promo_description) as ImageView
        LoadImage().loadImagePromoDescription(imgPromoDescript, position, promoList)

        val btnClose = dialog.findViewById(R.id.btn_close_dialog_promo) as Button
        btnClose.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }
}
}