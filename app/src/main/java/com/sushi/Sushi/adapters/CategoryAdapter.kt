package com.sushi.Sushi.adapters

//import com.sushi.Sushi.models.CategoryModel
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

//            //анимация альфа канала (прозрачности от 0 до 1)
//            val animation: Animation = AlphaAnimation(0.7f, 1.0f)
////длительность анимации 1/10 секунды
//            //длительность анимации 1/10 секунды
//            animation.duration = 700
////сдвижка начала анимации (с середины)
//            //сдвижка начала анимации (с середины)
//            animation.startOffset = 50
////режим повтора - сначала или в обратном порядке
//            //режим повтора - сначала или в обратном порядке
//            animation.repeatMode = Animation.REVERSE
////режим повтора (бесконечно)
//            //режим повтора (бесконечно)
//            animation.repeatCount = Animation.INFINITE
////накладываем анимацию на TextView
//            //накладываем анимацию на TextView
//            categoryText.startAnimation(animation)

        }
    }
}