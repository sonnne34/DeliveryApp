package com.sushi.Sushi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sushi.Sushi.BasketFragment
import com.sushi.Sushi.R
import com.sushi.Sushi.dialog.CountDialog
import com.sushi.Sushi.listener.EventListenerss
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.singleton.BasketSingleton

//import kotlinx.android.synthetic.main.fragment_choice.*

class BasketAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var mBasketList: ArrayList<MenuModelcatMenu> = ArrayList()

    fun setupBasket(basketList: ArrayList<MenuModelcatMenu>){
        mBasketList.clear()
        mBasketList.addAll(basketList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView  = layoutInflater.inflate(R.layout.item_basket_recyclerview,parent,false)
        return BasketViewHoldel(itemView= itemView)

    }

    override fun getItemCount(): Int {
        return mBasketList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is BasketViewHoldel ){
            holder.bind(menuModel = mBasketList.get(position))


        }
    }

    class BasketViewHoldel(itemView: View): RecyclerView.ViewHolder(itemView){

        private var nameDish : TextView = itemView.findViewById(R.id.txt_name_dish_basket)
        private var description : TextView = itemView.findViewById(R.id.txt_description_basket)
        private var prise : TextView = itemView.findViewById(R.id.txt_prise_dish_basket)
        private var valueDish : TextView = itemView.findViewById(R.id.txt_value_basket)
        private var btnPlus : Button = itemView.findViewById(R.id.btn_plus_basket)
        private var btnMin : Button = itemView.findViewById(R.id.btn_minus_basket)


        fun bind (menuModel: MenuModelcatMenu){

            nameDish.text = "${menuModel.Items?.Name}"
            valueDish.text = "${menuModel.Items?.CountDialog}"


            itemView.setOnClickListener {

//                var item = BasketSingleton.basketItem
//                var menufile = MenuModelcatMenu()
//                for(i in item){
//                    menufile = i
//
//                }
//
//                CountDialog.openDialog(itemView.context, menufile)

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

                BasketSingleton.itemCountBasket(text.toString())
                valueDish.text = Integer.toString(text)

                var count = menuModel.Items?.Cost
                var txt  = Integer.valueOf(count.toString())
                val sums = (txt * text)

                prise.text = sums.toString()


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


}