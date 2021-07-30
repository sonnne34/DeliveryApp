package com.sushi.Sushi.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sushi.Sushi.R
import com.sushi.Sushi.models.CatMenuModel

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
            holder.bind(categoryModel = mCategoryList[position])
        }
    }
    class CategoryViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView){
        var categoryText: TextView = itemView.findViewById(R.id.text_items)

        fun bind(categoryModel: CatMenuModel){

            categoryText.text = categoryModel.CategoryName
            Log.d("category", "cat= $categoryText")
        }
    }
}