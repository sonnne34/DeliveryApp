package com.sushi.Sushi.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sushi.Sushi.R
import com.sushi.Sushi.dialog.CountDialog
import com.sushi.Sushi.models.CatMenuModel
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.singleton.BasketSingleton

class MenuAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var  mMenuList : ArrayList<MenuModelcatMenu> = ArrayList()

    private val LAYOUT_HEADER = 0
    private val LAYOUT_CHILD = 1

    fun setupMenu(menuList : ArrayList<CatMenuModel>){
        mMenuList.clear()

        for (categoryModel in menuList){
            Log.d("UUU", "UUUCat = " + categoryModel.CategoryName)
            var headerModel = MenuModelcatMenu()
            headerModel.CategoryName = categoryModel.CategoryName
            headerModel.isHeader = true
            mMenuList.add(headerModel)

            for( i in categoryModel.Items){

                Log.d("UUU", "UUUmenu = " + i.value.Name)
                Log.d("UUU", "UUUmenu = " + i.value.Cost)
                Log.d("UUU", "UUUmenu = " + i.value.Description)
                var menuModel = MenuModelcatMenu()
                menuModel.Items = i.value

                mMenuList.add(menuModel)



            }




        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) : Int{
        if(mMenuList.get(position).isHeader == true){
            return LAYOUT_HEADER
        }else{
            return LAYOUT_CHILD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        if(viewType == LAYOUT_HEADER){
            var layoutInflater = LayoutInflater.from(parent.context)
            var itemView = layoutInflater.inflate(R.layout.category_item_menu,parent,false)
            return HeaderViewHolder(itemView = itemView)

        }else {

            var layoutInflater = LayoutInflater.from(parent.context)
            var itemView = layoutInflater.inflate(R.layout.item_menu_recyclerview, parent, false)
            return MenuViewHolder(itemView = itemView)
        }

    }

    override fun getItemCount(): Int {
        return mMenuList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder.itemViewType == LAYOUT_HEADER){
            if( holder is HeaderViewHolder){
                holder.bindHeader(menuCategoryModel = mMenuList[position])
            }else{


            }


        }else{
            if(holder is MenuViewHolder){
                holder.bindMenu(menuCategoryModel = mMenuList[position])
            }

        }

    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var categoryHeader: TextView = itemView.findViewById(R.id.category_item_menu)

        fun bindHeader(menuCategoryModel: MenuModelcatMenu){

            categoryHeader.text = "${menuCategoryModel.CategoryName}"
        }

    }


    class  MenuViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        private var name : TextView = itemView.findViewById(R.id.text_roll)
        private var discription: TextView  = itemView.findViewById(R.id.discription_text)
        private var cost: TextView = itemView.findViewById(R.id.txt_roll_price)
        private var checkBoxItem : TextView = itemView.findViewById(R.id.checkBoxItem)

        @SuppressLint("ResourceAsColor")
        fun bindMenu (menuCategoryModel: MenuModelcatMenu){

            name.text = "${menuCategoryModel.Items?.Name}"
            discription.text = "${menuCategoryModel.Items?.Description}"
            cost.text = "${menuCategoryModel.Items?.Cost}"


            itemView.setOnClickListener {
                CountDialog.openDialog(itemView.context, menuCategoryModel)
            }

            Log.d("Color", "menu = " + menuCategoryModel)

            var rr = BasketSingleton.checkingThelist(menuCategoryModel)
            Log.d("Color", "Bolean = " + rr)

            if(rr == true){
                checkBoxItem.setBackgroundResource(R.color.colorSky)
            }else{
                checkBoxItem.setBackgroundResource(R.color.transparent)
            }





        }


    }
}