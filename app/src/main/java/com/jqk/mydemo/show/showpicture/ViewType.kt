package com.jqk.mydemo.show.showpicture

import android.graphics.Bitmap

data class ViewType(
        var position: Int,
        var type: Int,
        var path: String,
        var bitmap: Bitmap?
) {

}