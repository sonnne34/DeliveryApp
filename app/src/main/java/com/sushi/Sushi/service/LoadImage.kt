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

//        val storage = FirebaseStorage.getInstance()
//        val storageRef = storage.getReferenceFromUrl(menuCategoryModel.Items?.Picture!!)
//        storageRef.downloadUrl.addOnSuccessListener { uri ->

//            Picasso.get().load(uri).fit().transform(CircleTransform()).noFade().into(imageDish)
//        }

    }
//        val storage = FirebaseStorage.getInstance()
//
//        val storageRef = storage.getReferenceFromUrl(menuCategoryModel.Items?.Picture!!)
//
//        val ONE_MEGABYTE = (120 * 120).toLong()
//        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//            val bm = BitmapFactory.decodeByteArray(it, 0, it.size)
//            val dm = DisplayMetrics()
//            imageDish.setImageBitmap(bm)
////            imageDish.getImageBitmap(bm)
//        }
//    }

    fun loadImagePromo(promoModel:PromoModel, imagePromo: ImageView) {

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl(promoModel.Picture!!)
        storageRef.downloadUrl.addOnSuccessListener { uri ->

            Picasso.get().load(uri).fit().centerCrop().noFade().into(imagePromo)
        }
    }

    fun loadImagePromoDescription(imagePromoDescription: ImageView, position: Int, promoList: ArrayList<PromoModel>) {

        val promoModel = promoList[position]

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl(promoModel.PictureDescription!!)
        storageRef.downloadUrl.addOnSuccessListener { uri ->

            Picasso.get().load(uri).fit().centerCrop().into(imagePromoDescription)
        }

    }
}