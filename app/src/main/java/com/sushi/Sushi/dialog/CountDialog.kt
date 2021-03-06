package com.sushi.Sushi.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.ListPopupWindow
import android.widget.TextView
import com.sushi.Sushi.R
import com.sushi.Sushi.adapters.MenuAdapter
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.service.LoadImage
import com.sushi.Sushi.singleton.BasketSingleton

class CountDialog {
    companion object {
        fun openDialog(context: Context, fileMenu: MenuModelcatMenu, position: Int) {


            var menuFile = fileMenu
            Log.d("PPPP ", " Menufile = " + menuFile.Items?.Name)

            val dialog = Dialog(context, R.style.CustomDialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_item_menu)
            dialog.window?.setGravity(Gravity.BOTTOM)
            dialog.window?.setLayout(
                ListPopupWindow.MATCH_PARENT,
                ListPopupWindow.WRAP_CONTENT
            )

            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)

            val description = dialog.findViewById(R.id.textViewGoodsDescriptionDialog) as TextView
            val name = dialog.findViewById(R.id.names) as TextView
            val cost = dialog.findViewById(R.id.cost) as TextView
            val newCost = dialog.findViewById(R.id.txt_roll_price_new_cost) as TextView
            val imgLine = dialog.findViewById(R.id.img_roll_prise) as ImageView
            val count = dialog.findViewById(R.id.count) as TextView
            val imgDish = dialog.findViewById(R.id.imageViewPictureDishDialog) as ImageView
            val file = BasketSingleton.proverkaNaNalichie(fileMenu)
            val newCostt = menuFile.Items?.NewCost
            var priceDialog = menuFile.Items?.Cost?.toInt()
            var priceDialogNewCost = menuFile.Items?.NewCost?.toInt()
            var simCost: Long = Long.MAX_VALUE
            var simNewCost: Long = Long.MAX_VALUE

            description.text = menuFile.Items?.Description
            name.text = menuFile.Items?.Name
            cost.text = menuFile.Items?.Cost.toString()
            newCost.visibility = View.GONE
            imgLine.visibility = View.GONE
            LoadImage().loadImageDish(context, menuFile, imgDish)

            //???????? ?????????? ???????? ???????? (?????????? ??????????????, ?????? ?????? ???????????? ???????????????? ???????????????? ???? ????????????)))
            if (newCostt?.toDouble()!! < 10000) {
                //???? ?????????? ???????????????? ???? ????????????
                newCost.text = "$newCostt ??."
                //?? ???????????? ???????????? ??????????????
                newCost.visibility = View.VISIBLE
                imgLine.visibility = View.VISIBLE
                //???????????????????? ?????? ???????? ?????? ??????????????????
                simCost = menuFile.Items?.Cost!!.toLong()
                simNewCost = newCostt.toLong()
            }else{
                // ???????? ???????????????????? ???????????? ?????????????? ???????? ?????? ??????????????????, ?????????? ???????? = 0
                    simNewCost = 0
                    simCost = menuFile.Items?.Cost!!
                    cost.text = simCost.toString()
            }

            //???????? ?????????? ?????? ???????? ?? ?????????????? ???? ?????????????? ???????????????????? ?? ??????????????????????
            if (file != null){
                val somDialogCost: Long = file.Items?.CountDialog!!
                val zimSum = simCost * somDialogCost
                val zimSumNewCost = simNewCost * somDialogCost
                cost.text = zimSum.toString()
                newCost.text = zimSumNewCost.toString() + " ??."
                count.text = file.Items?.CountDialog.toString()

                dialog.show()

             //???????? ?????????? ?? ?????????????? ?????? ??????
            }else{
                val one: Long = 1
                count.text = one.toString()
                dialog.show()

            }

            // ????????????????
            //????????
            val plus = dialog.findViewById(R.id.image_plus) as Button
            plus.setOnClickListener(){
                var zzCount = count.text
                var pzCount = Integer.valueOf(zzCount.toString())

                //?????????????? ????????????????????
                pzCount++
                count.text = pzCount.toString() // ?????????????????????????????? ?? ???????????? ?? ???????????????????? ?? ???????????? "count"

                //?????????????? ??????????
                val sums = (pzCount * priceDialog!!)
                cost.text = sums.toString()

                if (newCostt?.toDouble()!! < 10000) {
                    val sumsNewCost = (pzCount * priceDialogNewCost!!)
                    newCost.text = "$sumsNewCost ??."
                }
            }

            //??????????
            val minus = dialog.findViewById(R.id.image_minus) as Button
            minus.setOnClickListener(){
                val zzCount = count.text // ?????????????? ???????????????????? ??????????????
                var pzCount = Integer.valueOf(zzCount.toString()) // ?????????????????????????????? ?? ??????????

                if (pzCount >= 1) { //???????? pz ???????????? ?????? ?????????? 1
                    pzCount-- // ???????????????? 1
                } else {
                    pzCount = 0 //???????? pz ?????????? 0 ???? ???????????????????? ???????????????? 0//
                }
                count.text = pzCount.toString() // ?????????????????????????????? ?? ???????????? ?? ???????????????????? ?? ???????????? "count"

                val countTwo = Integer.valueOf(pzCount.toString())
                val sums = (countTwo * priceDialog!!)
                cost.text = sums.toString()

                if (newCostt?.toDouble()!! < 10000) {
                    val sumsNewCost = (countTwo * priceDialogNewCost!!)
                    newCost.text = "$sumsNewCost ??."
                }

            }

            //????????????????
            val add = dialog.findViewById(R.id.btn_add) as Button
            add.setOnClickListener() {
                val zz = count.text // ?????????????? ???????????????????? ??????????????
                Log.d("CCC", "FFF = " + zz)

                var pz = Integer.valueOf(zz.toString()) // ?????????????????????????????? ?? ??????????

                menuFile.Items?.CountDialog = pz.toLong()

                //???????? ???????????????????? ???? ?????????? 0 ???? ?????????????????? ?? ??????????????
                if (pz != 0) {
                    BasketSingleton.addBasket(menuFile)
                    BasketSingleton.showBasket()
                    BasketSingleton.notifyTwo()
                    //???????? ???????????????????? ?????????? 0 ????
                } else {
//                    ???? ???????? ?????????????? ???????? ?? ?????????????? ???? ?????????????? ?????? ??????????????
                    if (file != null) {
//                        BasketSingleton.delPos(position)
                        BasketSingleton.delPosBasket(file)
                        BasketSingleton.notifyTwo()
                        //???????? ?????????????? ???? ???????????????????? ???? ?????????????????? ???????????? ?????? ??????????????????
                    }
                }
                dialog.cancel()
            }
            //??????????????
            val cancel = dialog.findViewById(R.id.cancel) as Button
            cancel.setOnClickListener() {
                dialog.cancel()
            }
        }
    }

}