package com.sushi.Sushi.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sushi.Sushi.R
import com.sushi.Sushi.dialog.CountDialog
import com.sushi.Sushi.models.CatMenuModel
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.service.LoadImage
import com.sushi.Sushi.singleton.BasketSingleton

class MenuAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var mMenuList: ArrayList<MenuModelcatMenu> = ArrayList()
    private var mCatList: ArrayList<CatMenuModel>? = null
    private val LAYOUT_HEADER = 0
    private val LAYOUT_CHILD = 1
    private var mContext = context

    fun setupMenu(menuList: ArrayList<CatMenuModel>) {
        mMenuList.clear()

        if (mCatList == null) {
            mCatList = menuList
        }

        for (categoryModel in menuList) {
            Log.d("UUU", "UUUCat = " + categoryModel.CategoryName)
            var headerModel = MenuModelcatMenu()
            headerModel.CategoryName = categoryModel.CategoryName
            headerModel.isHeader = true
            mMenuList.add(headerModel)

            for (i in categoryModel.Items) {
                var menuModel = MenuModelcatMenu()
                menuModel.Items = i.value
                mMenuList.add(menuModel)
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (mMenuList[position].isHeader) {
            return LAYOUT_HEADER
        } else {
            return LAYOUT_CHILD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == LAYOUT_HEADER) {
            var layoutInflater = LayoutInflater.from(parent.context)
            var itemView = layoutInflater.inflate(R.layout.category_item_menu, parent, false)
            return HeaderViewHolder(itemView = itemView)

        } else {

            var layoutInflater = LayoutInflater.from(parent.context)
            var itemView = layoutInflater.inflate(R.layout.item_menu_recyclerview, parent, false)
            return MenuViewHolder(itemView = itemView)
        }

    }

    override fun getItemCount(): Int {
        return mMenuList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == LAYOUT_HEADER) {
            if (holder is HeaderViewHolder) {
                holder.bindHeader(menuCategoryModel = mMenuList[position])
            } else {
            }

        } else {
            if (holder is MenuViewHolder) {
                holder.bindMenu(menuCategoryModel = mMenuList[position], position = position, context = mContext)
            }
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryHeader: TextView = itemView.findViewById(R.id.category_item_menu)

        fun bindHeader(menuCategoryModel: MenuModelcatMenu) {

            categoryHeader.text = "${menuCategoryModel.CategoryName}"

        }

    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var name: TextView = itemView.findViewById(R.id.text_roll)
        private var discription: TextView = itemView.findViewById(R.id.discription_text)
        private var cost: TextView = itemView.findViewById(R.id.txt_roll_price)
        private var newCost: TextView = itemView.findViewById(R.id.txt_roll_price_new_cost)
        private var imgLine: ImageView = itemView.findViewById(R.id.img_roll_prise)
        private var checkBoxItem: TextView = itemView.findViewById(R.id.checkBoxItem)
        private var imgDish: ImageView = itemView.findViewById(R.id.image_dish_menu)
        private var wt: TextView = itemView.findViewById(R.id.txt_roll_weight)

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bindMenu(menuCategoryModel: MenuModelcatMenu, position: Int, context: Context) {

            name.text = "${menuCategoryModel.Items?.Name}"
            discription.text = "${menuCategoryModel.Items?.Description}"
            cost.text = "${menuCategoryModel.Items?.Cost}" + " р."
            val newCostt = "${menuCategoryModel.Items?.NewCost}"
            newCost.visibility = View.GONE
            imgLine.visibility = View.GONE

            //здесь костыль: при пустых значаниях приходят странные цифры, но они не больше 10000)
            if (newCostt.toLong() in 1..9999) {
                newCost.text = "${newCostt.toString()} р.".toString()
                newCost.visibility = View.VISIBLE
                imgLine.visibility = View.VISIBLE
            }

            val wtVal = menuCategoryModel.Items?.Wt
                if(wtVal?.toInt() == 0){
                    wt.visibility = View.GONE
                }else {
                    wt.visibility = View.VISIBLE
                    wt.text = "$wtVal гр."
                }
            Log.d("URR", "uri= Прошло 1 ")

            LoadImage().loadImageDish(mContext, menuCategoryModel, imgDish)

            itemView.setOnClickListener {
                CountDialog.openDialog(context, menuCategoryModel, position)
            }

            Log.d("Color", "menu = " + menuCategoryModel)

            var rr = BasketSingleton.checkingThelist(menuCategoryModel)
            Log.d("Color", "Bolean = " + rr)

            if (rr == true) {
                checkBoxItem.setBackgroundResource(R.drawable.okrugl)
            } else {
                checkBoxItem.setBackgroundResource(R.color.transparent)
            }
        }
    }

    fun scrollToCategory(name: String): Int {
        var position = 0
        for (i in mMenuList.indices) {
            val element: MenuModelcatMenu = mMenuList[i]
            if (element.isHeader) {
                if (element.CategoryName.equals(name)) {
                    position = i
                }
            }
        }
        return position
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                val filterResults = FilterResults()
                if (charSearch.isNotEmpty()) {
                    val filter: ArrayList<CatMenuModel> = ArrayList()

                    for(categoryModel in mCatList!!) {
                        val filterCategory = CatMenuModel(categoryModel.CategoryName)

                        for (item in categoryModel.Items) {
                            if (item.value.Name?.contains(charSearch, true) == true
                            ) {
                                filterCategory.Items[item.key] = item.value
                            }
                        }
                        if (filterCategory.Items.isNotEmpty())
                            filter.add(filterCategory)
                    }
                    filterResults.values =  filter
                } else {
                    filterResults.values =  mCatList
                }
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                setupMenu(results?.values as ArrayList<CatMenuModel>)
            }
        }
    }
}