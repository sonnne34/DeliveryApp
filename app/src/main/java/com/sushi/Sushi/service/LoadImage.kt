package com.sushi.Sushi.service

import android.widget.ImageView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.sushi.Sushi.models.MenuModelcatMenu

class LoadImage {

    fun loadImage(menuCategoryModel:MenuModelcatMenu, imageDish: ImageView) {

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl(menuCategoryModel.Items?.Picture!!)
        storageRef.downloadUrl.addOnSuccessListener { uri ->

            Picasso.get().load(uri).fit().transform(CircleTransform()).noFade().into(imageDish)
        }

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
}