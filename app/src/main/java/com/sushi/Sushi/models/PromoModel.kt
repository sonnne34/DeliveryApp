package com.sushi.Sushi.models

import android.net.Uri
import java.util.HashMap

class PromoModel (var Picture : String?, var PictureLoad: Uri?, var PictureDescription : String? ) {
    constructor(): this(Picture = String(), PictureDescription = String(), PictureLoad = null)

}
