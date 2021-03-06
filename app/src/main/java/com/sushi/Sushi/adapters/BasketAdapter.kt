package com.sushi.Sushi.adapters

import android.annotation.SuppressLint
import android.content.Context
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
import com.sushi.Sushi.service.LoadImage
//import com.sushi.Sushi.service.CircleTransform
import com.sushi.Sushi.singleton.BasketSingleton


class BasketAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
     var mBasketList: ArrayList<MenuModelcatMenu> = ArrayList()
     private var mContext = context

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
            holder.bind(menuModel = mBasketList[position], position = position, context = mContext)
            btnDel(holder, position)

        }
    }

    class BasketViewHoldel(itemView: View): RecyclerView.ViewHolder(itemView){

        private var nameDish : TextView = itemView.findViewById(R.id.txt_name_dish_basket)
        private var description : TextView = itemView.findViewById(R.id.txt_description_basket)
        private var imgDish: ImageView = itemView.findViewById(R.id.img_dish_basket)
        private var prise : TextView = itemView.findViewById(R.id.txt_prise_dish_basket)
        private var priseNewCost : TextView = itemView.findViewById(R.id.txt_new_prise_dish_basket)
        private var valueDish : TextView = itemView.findViewById(R.id.txt_value_dish_basket)
        private var btnPlus : Button = itemView.findViewById(R.id.btn_plus_basket)
        private var btnMin : Button = itemView.findViewById(R.id.btn_minus_basket)
        private var imgLine : ImageView = itemView.findViewById(R.id.img_roll_prise_basket)
        var btnDel : Button = itemView.findViewById(R.id.btn_del_basket)


        @SuppressLint("SetTextI18n")
        fun bind(menuModel: MenuModelcatMenu, position: Int, context: Context){

            nameDish.text = "${menuModel.Items?.Name}"
            valueDish.text = "${menuModel.Items?.CountDialog}"
            description.text = "${menuModel.Items?.Description}"

            LoadImage().loadImageDish(context, menuModel, imgDish)

            priseNewCost.visibility = View.GONE
            imgLine.visibility = View.GONE

            val costNewCostt = menuModel.Items?.NewCost?.toInt()

            Log.d("NewCost", "newC = $costNewCostt")

            //?????????? ??????????????: ?????? ???????????? ?????????????????? ???????????????? ???????????????? ??????????, ???? ?????? ???? ???????????? 10000)
            if (costNewCostt?.toLong()!! in 1..9999) {
                priseNewCost.text = "${costNewCostt.toString()} ??.".toString()
                priseNewCost.visibility = View.VISIBLE
                imgLine.visibility = View.VISIBLE

            }

            itemView.setOnClickListener {

                CountDialog.openDialog(itemView.context, menuModel, position)

            }

            val  count = menuModel.Items?.CountDialog?.toInt()
            val cost = menuModel.Items?.Cost?.toInt()
            val sum  = cost!! * count!!

            val costNewCosttt = menuModel.Items?.NewCost?.toInt()
            val sumNewCost = costNewCosttt!! * count!!

            prise.text = "$sum ??."
            priseNewCost.text = "$sumNewCost ??."

            btnMin.setOnClickListener {
                val textNumber = valueDish.text
                var text = Integer.valueOf(textNumber.toString())
                if(text >= 1){
                    text--
                }else{
                    text = 0
                }

                valueDish.text = text.toString()

                val count = menuModel.Items?.Cost
                val txt  = Integer.valueOf(count.toString())
                val sums = (txt * text)

                prise.text = "$sums ??."
                BasketSingleton.notifyTwo()
                Log.d("img", "ooops")
            }


            btnPlus.setOnClickListener {
                val textNumber = valueDish.text
                var text = Integer.valueOf(textNumber.toString())
                text++

                valueDish.text = text.toString()

                val count = menuModel.Items?.Cost
                val txt = Integer.valueOf(count.toString())
                val sums = (txt * text)

                prise.text = "$sums ??."

            }
        }
    }

    fun btnDel(holder: RecyclerView.ViewHolder, position: Int){
        if(holder is BasketViewHoldel ){
            holder.bind(menuModel = mBasketList[position], position = position, context = mContext)
            holder.btnDel.setOnClickListener {

                val delPosDialog = AlertDialog.Builder(holder.itemView.context
                )
                delPosDialog.setTitle("??????????????????????????????")
                delPosDialog.setMessage("?????????????? ???????????")
                delPosDialog.setPositiveButton(
                    "????"
                ) { _, _ ->
                    BasketSingleton.delPos(position)
                    BasketSingleton.notifyTwo()
                    Log.d("list", "del= $mBasketList")
                }
                delPosDialog.setNegativeButton(
                    "????, ??????!"
                ) { _, _ -> }
                delPosDialog.show()
            }
            }
        }
}
