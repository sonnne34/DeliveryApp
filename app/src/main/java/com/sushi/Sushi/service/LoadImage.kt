package com.sushi.Sushi.service

import android.content.Context
import android.icu.text.Transliterator
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.sushi.Sushi.models.CatMenuModel
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.models.PromoModel

class LoadImage {

    fun loadImageDish(context: Context, menuCategoryModel:MenuModelcatMenu, imageDish: ImageView) {

        val glide = Glide.with(context)
        if (menuCategoryModel.Items?.PictureForLoad == null) {
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.getReferenceFromUrl(menuCategoryModel.Items?.Picture!!)
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                menuCategoryModel.Items?.PictureForLoad = uri
                val img = glide.load(uri)
                img.diskCacheStrategy(DiskCacheStrategy.NONE)
                img.centerCrop().into(imageDish)
            }
        } else {
            val img = glide.load(menuCategoryModel.Items?.PictureForLoad)
            img.diskCacheStrategy(DiskCacheStrategy.NONE)
            img.into(imageDish)
        }
    }


    fun loadImagePromo(context: Context, promoModel:PromoModel, imagePromo: ImageView) {

        val glide = Glide.with(context)
        if (promoModel.PictureLoad == null) {
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.getReferenceFromUrl(promoModel.Picture!!)
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                promoModel.PictureLoad = uri
                val img = glide.load(uri)
                img.diskCacheStrategy(DiskCacheStrategy.NONE)
                img.centerCrop().into(imagePromo)
            }
        } else {
            val img = glide.load(promoModel.PictureLoad)
            img.diskCacheStrategy(DiskCacheStrategy.NONE)
            img.centerCrop().into(imagePromo)
        }
    }

    fun loadImagePromoDescription(context: Context, imagePromoDescription: ImageView, position: Int, promoList: ArrayList<PromoModel>) {

        val promoModel = promoList[position]
//
//        val storage = FirebaseStorage.getInstance()
//        val storageRef = storage.getReferenceFromUrl(promoModel.PictureDescription!!)
//        storageRef.downloadUrl.addOnSuccessListener { uri ->
//
//            Picasso.get().load(uri).fit().centerCrop().into(imagePromoDescription)
//        }

        val glide = Glide.with(context)
        if (promoList == null) {
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.getReferenceFromUrl(promoModel.Picture!!)
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                promoModel.PictureLoad = uri
                val img = glide.load(uri)
                img.diskCacheStrategy(DiskCacheStrategy.NONE)
                img.centerCrop().into(imagePromoDescription)
            }
        } else {
            val img = glide.load(promoModel.PictureLoad)
            img.diskCacheStrategy(DiskCacheStrategy.NONE)
            img.centerCrop().into(imagePromoDescription)
        }
    }
}