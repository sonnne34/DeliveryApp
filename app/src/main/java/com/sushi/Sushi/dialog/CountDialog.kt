package com.sushi.Sushi.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Gravity
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
        fun openDialog(context: Context, fileMenu: MenuModelcatMenu) {


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
            val count = dialog.findViewById(R.id.count) as TextView
            val imgDish = dialog.findViewById(R.id.imageViewPictureDishDialog) as ImageView

            val file = BasketSingleton.proverkaNaNalichie(fileMenu)

            Log.d("verka", "11111= " + file)

            if (file != null){

                description.text = file.Items?.Description
                name.text = file.Items?.Name

                LoadImage().loadImageDish(file, imgDish)

//                val storageTwo = FirebaseStorage.getInstance()
//
//                val storageRefTwo = storageTwo.getReferenceFromUrl(file?.Items?.Picture!!)
//


//                storageRefTwo.downloadUrl.addOnSuccessListener { uri ->
//                    Picasso.get().load(uri).fit().centerCrop()
//                        .into(imgDish)
//                }.addOnFailureListener {
//                }
//


//                val ONE_MEGABYTE = (2000 * 4000).toLong()
//                storageRefTwo.getBytes(ONE_MEGABYTE).addOnSuccessListener{
//                    val bm = BitmapFactory.decodeByteArray(it, 0, it.size)
//                    val dm = DisplayMetrics()
//                    imgDish.setImageBitmap(bm)
//                }


                val sim: Long = file.Items?.Cost!!
                val som: Long = file.Items?.CountDialog!!
                val zim = sim * som

                cost.text = zim.toString()

                count.text = file.Items?.CountDialog.toString()
                dialog.show();

                Log.d("verka", "2222222= " + file)


            }else{
                description.text = menuFile.Items?.Description
                name.text = menuFile.Items?.Name
                cost.text = menuFile.Items?.Cost.toString()

                LoadImage().loadImageDish(menuFile, imgDish)

//                val storageTwo = FirebaseStorage.getInstance()
//                val storageRefTwo = storageTwo.getReferenceFromUrl(menuFile.Items?.Picture!!)


//                storageRefTwo.downloadUrl.addOnSuccessListener { uri ->
//                    Picasso.get().load(uri).fit().centerCrop()
//                        .into(imgDish)
//                }


//                val ONE_MEGABYTE = (4000 * 2000).toLong()
//                storageRefTwo.getBytes(ONE_MEGABYTE).addOnSuccessListener{
//                    val bm = BitmapFactory.decodeByteArray(it, 0, it.size)
//                    val dm = DisplayMetrics()
//                    imgDish.setImageBitmap(bm)
//                }

                val one: Long = 1
                count.text = one.toString()
                dialog.show();

            }

            val plus = dialog.findViewById(R.id.image_plus) as Button
            plus.setOnClickListener(){
                var zz = count.text
                var pz = Integer.valueOf(zz.toString())
                pz++
                count.text = pz.toString() // преобразовываем в строку и возвращаем в обьект "count"

                var sum = dialog.findViewById(R.id.cost) as TextView
                var priceDialog = menuFile.Items?.Cost?.toInt()

                val count44 = count.text
                val countFF = Integer.valueOf(count44.toString())
                val sums = (countFF * priceDialog!!)
                sum.text = sums.toString()

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
                count.text = pz.toString() // преобразовываем в строку и возвращаем в обьект "count"
                var sum = dialog.findViewById(R.id.cost) as TextView
                var priceDialog = menuFile.Items?.Cost?.toInt()
                val count44 = count.text
                val countTwo = Integer.valueOf(count44.toString())
                var sums = (countTwo * priceDialog!!)
                sum.text = sums.toString()

            }

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
                        val position: Int = 0
                        BasketSingleton.delPos(position)
                        BasketSingleton.notifyTwo()
                    }
                }

                dialog.cancel()


            }
            val cancel = dialog.findViewById(R.id.cancel) as Button
            cancel.setOnClickListener() {
                dialog.cancel()
            }
        }
    }
}