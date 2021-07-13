package com.sushi.Sushi.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sushi.Sushi.R
import com.sushi.Sushi.models.MenuModelcatMenu

class TotalAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mMenuList: ArrayList<MenuModelcatMenu> = ArrayList()

    fun setupTotal(List: ArrayList<MenuModelcatMenu>){

        for(i in List){
            Log.d("GGG","Menulist = " + i.Items?.Name)
        }

        mMenuList.clear()
        mMenuList.addAll(List)
        notifyDataSetChanged()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.list_registration_total,parent,false)
        return CategoryViewHolder(itemView = itemView)
    }


    override fun getItemCount(): Int {
        return mMenuList.count()

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CategoryViewHolder){
            holder.bind(menu = mMenuList[position])
        }

    }
    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var costtotal: TextView? = itemView.findViewById(R.id.cost_total)
        var numbertotal: TextView? = itemView.findViewById(R.id.number_total)
        var namedishtotal: TextView? = itemView.findViewById(R.id.name_dish_total)

        fun bind (menu: MenuModelcatMenu ){

            val cost: Long = menu.Items?.Cost!!
            val numberTotal: Long = menu.Items?.CountDialog!!
            val costTotal = cost * numberTotal

            namedishtotal?.text = "${menu.Items?.Name}"
            numbertotal?.text = numberTotal.toString()
            costtotal?.text = costTotal.toString()

        }
    }
}