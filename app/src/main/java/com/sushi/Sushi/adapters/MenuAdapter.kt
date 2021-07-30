package com.sushi.Sushi.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sushi.Sushi.R
import com.sushi.Sushi.dialog.CountDialog
import com.sushi.Sushi.models.CatMenuModel
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.service.LoadImage
import com.sushi.Sushi.singleton.BasketSingleton

class MenuAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mMenuList: ArrayList<MenuModelcatMenu> = ArrayList()

    private val LAYOUT_HEADER = 0
    private val LAYOUT_CHILD = 1

    fun setupMenu(menuList: ArrayList<CatMenuModel>) {
        mMenuList.clear()

        for (categoryModel in menuList) {
            Log.d("UUU", "UUUCat = " + categoryModel.CategoryName)
            var headerModel = MenuModelcatMenu()
            headerModel.CategoryName = categoryModel.CategoryName
            headerModel.isHeader = true
            mMenuList.add(headerModel)

            for (i in categoryModel.Items) {
//
//                Log.d("UUU", "UUUmenu = " + i.value.Name)
//                Log.d("UUU", "UUUmenu = " + i.value.Cost)
//                Log.d("UUU", "UUUmenu = " + i.value.Description)
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
                holder.bindMenu(menuCategoryModel = mMenuList[position])
            }
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryHeader: TextView = itemView.findViewById(R.id.category_item_menu)

        fun bindHeader(menuCategoryModel: MenuModelcatMenu) {

            categoryHeader.text = "${menuCategoryModel.CategoryName}"

        }

    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var name: TextView = itemView.findViewById(R.id.text_roll)
        private var discription: TextView = itemView.findViewById(R.id.discription_text)
        private var cost: TextView = itemView.findViewById(R.id.txt_roll_price)
        private var checkBoxItem: TextView = itemView.findViewById(R.id.checkBoxItem)
        private var imgDish: ImageView = itemView.findViewById(R.id.image_dish_menu)
        private var wt: TextView = itemView.findViewById(R.id.txt_roll_weight)

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bindMenu(menuCategoryModel: MenuModelcatMenu) {

            name.text = "${menuCategoryModel.Items?.Name}"
            discription.text = "${menuCategoryModel.Items?.Description}"
            cost.text = "${menuCategoryModel.Items?.Cost}" + " р."

            val wtVal = menuCategoryModel.Items?.Wt
                if(wtVal?.toInt() == 0){
                    wt.visibility = View.GONE
                }else {
                    wt.visibility = View.VISIBLE
                    wt.text = "$wtVal гр."
                }
            Log.d("URR", "uri= Прошло 1 ")

            LoadImage().loadImageDish(menuCategoryModel, imgDish)

//            val storage = FirebaseStorage.getInstance()
//            Log.d("URR", "uri= Прошло 2 ")
//            val storageRef = storage.getReferenceFromUrl(menuCategoryModel.Items?.Picture!!)
//            Log.d("URR", "uri= Прошло 3")
//            storageRef.downloadUrl.addOnSuccessListener { uri ->
//                Log.d("URR", "uri= Прошло 4 ")
//                Log.d("URR", "uri= $uri")
////                    Picasso.get().load(uri).fit().centerCrop().noFade().into(imgDish)
//                }

//            val ONE_MEGABYTE = (2000 * 2000).toLong()
//            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener{
//                val bm = BitmapFactory.decodeByteArray(it, 0, it.size)
//                val dm = DisplayMetrics()
//                imgDish.setImageBitmap(bm)
//
//            }


            itemView.setOnClickListener {
                CountDialog.openDialog(itemView.context, menuCategoryModel)
            }

            Log.d("Color", "menu = " + menuCategoryModel)

            var rr = BasketSingleton.checkingThelist(menuCategoryModel)
            Log.d("Color", "Bolean = " + rr)

            if (rr == true) {
                checkBoxItem.setBackgroundResource(R.color.qgreen)
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
}