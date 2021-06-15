package com.sushi.Sushi.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sushi.Sushi.MenuFragment
import com.sushi.Sushi.R
import com.sushi.Sushi.models.CategoryModel
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.singleton.BasketSingleton

class CategoryAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mCategoryList: ArrayList<CategoryModel> = ArrayList()

    fun setupCategory(categoryList: ArrayList<CategoryModel>){
        mCategoryList.clear()
        mCategoryList.addAll(categoryList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.category_recyclerview_items, parent, false)
        return CategoryViewHolder(itemView = itemView)
    }


    override fun getItemCount(): Int {
        return mCategoryList.count()

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CategoryViewHolder){
            holder.bind(categoryModel = mCategoryList[position])
//            btnCateg(holder, position)
        }

    }
    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private var categoryText: TextView = itemView.findViewById(R.id.text_items)

        fun bind(categoryModel: CategoryModel){
            categoryText.text = categoryModel.CategoryName

            var mList: ArrayList<MenuModelcatMenu> = ArrayList()
            lateinit var mMenuFragment: MenuFragment

            //            picture = itemView.findViewById(R.id.image_items);

            //анимация альфа канала (прозрачности от 0 до 1)
//            Animation animation = new AlphaAnimation(0.7f, 1.0f);
////длительность анимации 1/10 секунды
//            animation.setDuration(700);
////сдвижка начала анимации (с середины)
//            animation.setStartOffset(50);
////режим повтора - сначала или в обратном порядке
//            animation.setRepeatMode(Animation.REVERSE);
////режим повтора (бесконечно)
//            animation.setRepeatCount(Animation.INFINITE);
////накладываем анимацию на TextView
//            category.startAnimation(animation);

            itemView.setOnClickListener {
//                val name  = mList[position]
//                val pos: String? = name.CategoryName
//                Log.d("RRR", "pos = $pos")
//                if (pos == "Все") {
//                    Log.d("RRR", "poss = $pos")
//                    mMenuFragment.firstSortListCategories(pos)
//                    mMenuFragment.isFiltered = false
//                } else {
//                    mMenuFragment.sortListCategories(pos)
//                    mMenuFragment.isFiltered = true
//                }
            }
        }
    }


}