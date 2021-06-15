package com.sushi.Sushi.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.sushi.Sushi.R
import com.sushi.Sushi.dialog.CountDialog
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.service.CircleTransform
import com.sushi.Sushi.singleton.BasketSingleton


class BasketAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
     var mBasketList: ArrayList<MenuModelcatMenu> = ArrayList()

    fun setupBasket(basketList: ArrayList<MenuModelcatMenu>){
        mBasketList.clear()
        mBasketList.addAll(basketList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView  = layoutInflater.inflate(R.layout.item_basket_recyclerview, parent, false)
        return BasketViewHoldel(itemView = itemView)

    }

    override fun getItemCount(): Int {
        return mBasketList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is BasketViewHoldel ){
            holder.bind(menuModel = mBasketList[position])
            btnDel(holder, position)

        }
    }

    class BasketViewHoldel(itemView: View): RecyclerView.ViewHolder(itemView){

        private var nameDish : TextView = itemView.findViewById(R.id.txt_name_dish_basket)
        private var description : TextView = itemView.findViewById(R.id.txt_description_basket)
        private var imgDish: ImageView = itemView.findViewById(R.id.img_dish_basket)
        private var prise : TextView = itemView.findViewById(R.id.txt_prise_dish_basket)
        private var valueDish : TextView = itemView.findViewById(R.id.txt_value_dish_basket)
        private var btnPlus : Button = itemView.findViewById(R.id.btn_plus_basket)
        private var btnMin : Button = itemView.findViewById(R.id.btn_minus_basket)
        var btnDel : Button = itemView.findViewById(R.id.btn_del_basket)
        private lateinit var circleTransform: CircleTransform


        fun bind(menuModel: MenuModelcatMenu){

            nameDish.text = "${menuModel.Items?.Name}"
            valueDish.text = "${menuModel.Items?.CountDialog}"
            description.text = "${menuModel.Items?.Description}"

            Log.d("img", "ooops")

            Log.d("log", "storage" + menuModel.Items?.Picture)

            val storageTwo = FirebaseStorage.getInstance()

            val storageRefTwo = storageTwo.getReferenceFromUrl(menuModel.Items?.Picture!!)

            storageRefTwo.downloadUrl.addOnSuccessListener { uri ->
                Picasso.get().load(uri).transform(CircleTransform())
                .into(imgDish)
            }

            itemView.setOnClickListener {

                CountDialog.openDialog(itemView.context, menuModel)

            }

            val  count = menuModel.Items?.CountDialog?.toInt()
            val cost = menuModel.Items?.Cost?.toInt()
            val sum  = cost!! * count!!

            prise.text = sum.toString()

            btnMin.setOnClickListener {
                var textNumber = valueDish.text
                var text = Integer.valueOf(textNumber.toString())
                if(text >= 1){
                    text--
                }else{
                    text = 0
                }

                valueDish.text = Integer.toString(text)

                var count = menuModel.Items?.Cost
                var txt  = Integer.valueOf(count.toString())
                val sums = (txt * text)

                prise.text = sums.toString()
                BasketSingleton.notifyTwo()
                Log.d("img", "ooops")
            }


            btnPlus.setOnClickListener {
                var textNumber = valueDish.text
                var text = Integer.valueOf(textNumber.toString())
                text++

                valueDish.text = Integer.toString(text)

                var count = menuModel.Items?.Cost
                var txt = Integer.valueOf(count.toString())
                val sums = (txt * text)

                prise.text = sums.toString()

            }
        }
    }

    private fun btnDel(holder: RecyclerView.ViewHolder, position: Int){
        if(holder is BasketViewHoldel ){
            holder.bind(menuModel = mBasketList[position])
            holder.btnDel.setOnClickListener {

                val delPosDialog = AlertDialog.Builder(holder.itemView.context
                )
                delPosDialog.setTitle("Аннигилирование")
                delPosDialog.setTitle("Удалить блюдо?")
                delPosDialog.setPositiveButton(
                    "Да"
                ) { _, _ ->
                    BasketSingleton.delPos(position)
                    BasketSingleton.notifyTwo()
                    Log.d("list", "del= $mBasketList")
                }
                delPosDialog.setNegativeButton(
                    "Ой, нет!"
                ) { _, _ -> }
                delPosDialog.show()
            }
            }
        }
}
