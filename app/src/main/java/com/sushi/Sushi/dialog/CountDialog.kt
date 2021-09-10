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
            LoadImage().loadImageDish(menuFile, imgDish)

            //если новая цена есть (здесь костыль, так как пустое значение приходит не пустым)))
            if (newCostt?.toDouble()!! < 10000) {
                //то берем значение из модели
                newCost.text = "$newCostt р."
                //и делаем скидку видимой
                newCost.visibility = View.VISIBLE
                imgLine.visibility = View.VISIBLE
                //записываем обе цены для рассчётов
                simCost = menuFile.Items?.Cost!!.toLong()
                simNewCost = newCostt.toLong()
            }else{
                // либо записываем только обычную цену для рассчётов, новая цена = 0
                    simNewCost = 0
                    simCost = menuFile.Items?.Cost!!
                    cost.text = simCost.toString()
            }

            //если блюдо уже есть в корзине то достаём количество и перемножаем
            if (file != null){
                val somDialogCost: Long = file.Items?.CountDialog!!
                val zimSum = simCost * somDialogCost
                val zimSumNewCost = simNewCost * somDialogCost
                cost.text = zimSum.toString()
                newCost.text = zimSumNewCost.toString() + " р."
                count.text = file.Items?.CountDialog.toString()

                dialog.show()

             //если блюда в корзине еще нет
            }else{
                val one: Long = 1
                count.text = one.toString()
                dialog.show()

            }

            // Рассчёты
            //плюс
            val plus = dialog.findViewById(R.id.image_plus) as Button
            plus.setOnClickListener(){
                var zzCount = count.text
                var pzCount = Integer.valueOf(zzCount.toString())

                //считаем количество
                pzCount++
                count.text = pzCount.toString() // преобразовываем в строку и возвращаем в обьект "count"

                //считаем сумму
                val sums = (pzCount * priceDialog!!)
                cost.text = sums.toString()

                if (newCostt?.toDouble()!! < 10000) {
                    val sumsNewCost = (pzCount * priceDialogNewCost!!)
                    newCost.text = "$sumsNewCost р."
                }
            }

            //минус
            val minus = dialog.findViewById(R.id.image_minus) as Button
            minus.setOnClickListener(){
                val zzCount = count.text // получем содержимое обьекта
                var pzCount = Integer.valueOf(zzCount.toString()) // преобразовываем в число

                if (pzCount >= 1) { //если pz больше или равно 1
                    pzCount-- // убавляем 1
                } else {
                    pzCount = 0 //если pz равно 0 то возвращаем значение 0//
                }
                count.text = pzCount.toString() // преобразовываем в строку и возвращаем в обьект "count"

                val countTwo = Integer.valueOf(pzCount.toString())
                val sums = (countTwo * priceDialog!!)
                cost.text = sums.toString()

                if (newCostt?.toDouble()!! < 10000) {
                    val sumsNewCost = (countTwo * priceDialogNewCost!!)
                    newCost.text = "$sumsNewCost р."
                }

            }

            //добавить
            val add = dialog.findViewById(R.id.btn_add) as Button
            add.setOnClickListener() {
                val zz = count.text // получем содержимое обьекта
                Log.d("CCC", "FFF = " + zz)

                var pz = Integer.valueOf(zz.toString()) // преобразовываем в число

                menuFile.Items?.CountDialog = pz.toLong()
                if (pz != 0) {
                    BasketSingleton.addBasket(menuFile)
                    BasketSingleton.showBasket()
                    BasketSingleton.notifyTwo()
                }
                else {
                    if (file != null) {
                        BasketSingleton.delPos(position)
                        BasketSingleton.notifyTwo()
                    }
                }

                dialog.cancel()

                //закрыть
            }
            val cancel = dialog.findViewById(R.id.cancel) as Button
            cancel.setOnClickListener() {
                dialog.cancel()
            }
        }
    }

}