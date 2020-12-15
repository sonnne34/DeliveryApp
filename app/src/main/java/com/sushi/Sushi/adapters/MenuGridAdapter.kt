package com.sushi.Sushi.adapters

import android.content.Context
import android.util.Log
import android.util.MonthDisplayHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import com.sushi.Sushi.R
import com.sushi.Sushi.models.CatMenuModel
import com.sushi.Sushi.models.MenuModel

class MenuGridAdapter(val context: Context, val list: ArrayList<CatMenuModel>) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {




        val view = LayoutInflater.from(context).inflate(R.layout.item_grid_roll,parent,false)

        val gridItem = view.findViewById<AppCompatTextView>(R.id.text_roll)
        val discription = view.findViewById<AppCompatTextView>(R.id.discription_text)

        var mList : ArrayList<MenuModel> = ArrayList()

        for(i in list){
            for(y in i.Items){

                mList.add(y.value)

            }
        }
        gridItem.text = mList[position].Name
        discription.text = mList[position].Description

        return  view
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }


}