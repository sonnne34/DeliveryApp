package com.sushi.Sushi.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sushi.Sushi.MenuFragment
import com.sushi.Sushi.R
import com.sushi.Sushi.models.CatMenuModel
//import com.sushi.Sushi.models.CategoryModel
import com.sushi.Sushi.models.MenuModelcatMenu

class CategoryAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mCategoryList: ArrayList<CatMenuModel> = ArrayList()

    fun setupCategory(categoryList: ArrayList<CatMenuModel>){
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
            holder.bind(categoryModel = mCategoryList[position], position = position)
//            btnCateg(holder, position)
        }

    }
    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private var categoryText: TextView = itemView.findViewById(R.id.text_items)

        fun bind(categoryModel: CatMenuModel, position: Int){
            categoryText.text = categoryModel.CategoryName
            Log.d("category", "cat= $categoryText")

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
                mMenuFragment = MenuFragment()
                mMenuFragment.catScroll(position)
            }
        }
    }
}