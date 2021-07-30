package com.sushi.Sushi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sushi.Sushi.R
import com.sushi.Sushi.models.PromoModel
import com.sushi.Sushi.service.LoadImage

class PromoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mPromoList: ArrayList<PromoModel> = ArrayList()

    fun setupPromo(promoList: ArrayList<PromoModel>){
        mPromoList.clear()
        mPromoList.addAll(promoList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_promo, parent, false)

        return PromoViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return mPromoList.count()

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PromoViewHolder){
            holder.bind(promoModel = mPromoList[position])
        }
    }
    class PromoViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView){
        var promoImage: ImageView = itemView.findViewById(R.id.image_items_promo)

        fun bind(promoModel: PromoModel){

            LoadImage().loadImagePromo(promoModel, promoImage)

            //анимация альфа канала (прозрачности от 0 до 1)
            val animation: Animation = AlphaAnimation(0.9f, 1.0f)
//длительность анимации 1/10 секунды
            //длительность анимации 1/10 секунды
            animation.duration = 800
//сдвижка начала анимации (с середины)
            //сдвижка начала анимации (с середины)
            animation.startOffset = 50
//режим повтора - сначала или в обратном порядке
            //режим повтора - сначала или в обратном порядке
            animation.repeatMode = Animation.REVERSE
//режим повтора (бесконечно)
            //режим повтора (бесконечно)
            animation.repeatCount = Animation.INFINITE
//накладываем анимацию на TextView
            //накладываем анимацию на TextView
            promoImage.startAnimation(animation)

        }
    }
}