package com.sushi.Sushi.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.ListPopupWindow
import android.widget.TextView
import com.sushi.Sushi.R
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.singleton.BasketSingleton

class CountDialog {
    companion object {
        fun openDialog(context: Context, fileMenu: MenuModelcatMenu) {

            var menuFile = fileMenu
            Log.d ("dialog ", " Menufile = " + menuFile?.Items?.Name)

            val dialog = Dialog(context, R.style.CustomDialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_item_menu)
            dialog.window?.setGravity(Gravity.BOTTOM)
            dialog.window?.setLayout(
                    ListPopupWindow.MATCH_PARENT,
                    ListPopupWindow.WRAP_CONTENT)

            val description = dialog.findViewById(R.id.textViewGoodsDescriptionDialog) as TextView
            description.setText(menuFile?.Items?.Description)
            val name = dialog.findViewById(R.id.names) as TextView
            name.setText(menuFile?.Items?.Name)
            val cost = dialog.findViewById(R.id.cost) as TextView
            cost.setText(menuFile?.Items?.Cost.toString())

            val one: Long = 1
            var count = dialog.findViewById(R.id.count) as TextView
            count.setText(one.toString())



            val plus = dialog.findViewById(R.id.image_plus) as Button
            plus.setOnClickListener(){
                var zz = count.text
                var pz = Integer.valueOf(zz.toString())
                pz++
                count.text = Integer.toString(pz) // преобразовываем в строку и возвращаем в обьект "count"

                var sum = dialog.findViewById(R.id.cost) as TextView
                var priceDialog = menuFile?.Items?.Cost?.toInt()

                val count44 = count.text
                val countFF = Integer.valueOf(count44.toString())
                val sums = (countFF * priceDialog!!)
                sum.setText(sums.toString())





            }


            val minus = dialog.findViewById(R.id.image_minus) as Button
            minus.setOnClickListener(){
                val zz = count.text // получем содержимое обьекта

                Log.d("Count", "Minus = $zz")
                var pz = Integer.valueOf(zz.toString()) // преобразовываем в число

                if (pz >= 1) { //если pz больше или равно 1
                    pz-- // убавляем 1
                } else {
                    pz = 0 //если pz равно 0 то возвращаем значение 0//
                }
                count.text =
                        Integer.toString(pz) // преобразовываем в строку и возвращаем в обьект "count"
                var sum = dialog.findViewById(R.id.cost) as TextView
                var priceDialog = menuFile?.Items?.Cost?.toInt()
                val count44 = count.text
                val countTwo = Integer.valueOf(count44.toString())
                var sums = (countTwo * priceDialog!!)
                sum.setText(sums.toString())

            }

            val add = dialog.findViewById(R.id.btn_add) as Button
            add.setOnClickListener() {
                val zz = count.text // получем содержимое обьекта
                Log.d ("CCC", "FFF = " + zz)

                var pz = Integer.valueOf(zz.toString()) // преобразовываем в число



                menuFile.Items?.CountDialog = pz.toLong()
                BasketSingleton.addBasket(menuFile)
                BasketSingleton.showBasket()
                BasketSingleton.notifyTwo()

                dialog.cancel()


            }
            val cancel = dialog.findViewById(R.id.cancel) as Button
            cancel.setOnClickListener() {
                dialog.cancel()
            }


            dialog.show()

        }


    }
}