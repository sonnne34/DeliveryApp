package com.sushi.Sushi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sushi.Sushi.R
import com.sushi.Sushi.models.MenuModel
import kotlinx.android.synthetic.main.fragment_choice.*

class BasketAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mBasketList: ArrayList<MenuModel> = ArrayList()

    fun setupBasket(basketList: ArrayList<MenuModel>){
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
            holder.minusbtn()
            holder.plusbtn()

        }
    }

    class BasketViewHoldel(itemView: View): RecyclerView.ViewHolder(itemView){

        private var nameDish : TextView = itemView.findViewById(R.id.txt_name_dish_basket)
        private var description : TextView = itemView.findViewById(R.id.txt_description_basket)
        private var prise : TextView = itemView.findViewById(R.id.txt_prise_dish_basket)
        private var valueDish : TextView = itemView.findViewById(R.id.txt_value_basket)
        private var btnPlus : Button = itemView.findViewById(R.id.btn_plus_basket)
        private var btnMin : Button = itemView.findViewById(R.id.btn_minus_basket)


        fun bind (menuModel: MenuModel){

            nameDish.text = "${menuModel.Name}"
//            prise.text = "${menuModel.Count}"

//            var count = menuModel.Items?.CountDialog
//            var cost = menuCategoryModel?.Items?.Cost
//            var price = count!! * cost!!
//            counter.text = "${price}"
        }

        fun minusbtn() {
            btnMin.setOnClickListener {
                var textNumber = valueDish.text
                var text = Integer.valueOf(textNumber.toString())
                if(text >= 1){
                    text--
                }else{
                    text = 0
                }
                valueDish.text = Integer.toString(text)

                var count = valueDish.text
                var txt  = Integer.valueOf(count.toString())
                val sums = (txt * 100)

                prise.text = sums.toString()
            }


        }

        fun plusbtn() {
            btnPlus.setOnClickListener {
                var textNumber = valueDish.text
                var text = Integer.valueOf(textNumber.toString())
                text++
                valueDish.text = Integer.toString(text)

                var count = valueDish.text
                var txt = Integer.valueOf(count.toString())
                val sums = (txt * 100)

                prise.text = sums.toString()


            }
        }
    }
}