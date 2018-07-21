package net.yuzumone.recordachi.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import java.util.concurrent.Executors

fun ioThread(f : () -> Unit) {
    Executors.newSingleThreadExecutor().execute(f)
}

fun getBitmapFromUri(context: Context, uri: Uri): Bitmap {
    val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
    val fileDescriptor = parcelFileDescriptor.fileDescriptor
    val tmpOptions = BitmapFactory.Options()
    tmpOptions.inJustDecodeBounds = true
    BitmapFactory.decodeFileDescriptor(fileDescriptor, null, tmpOptions)

    var image: Bitmap
    val imageSizeMax = 512
    val imageScaleWidth = tmpOptions.outWidth.toFloat() / imageSizeMax
    val imageScaleHeight = tmpOptions.outHeight.toFloat() / imageSizeMax

    if (imageScaleWidth > 2 && imageScaleHeight > 2) {
        val options = BitmapFactory.Options()
        val imageScale = Math.floor((if (imageScaleWidth > imageScaleHeight)
            imageScaleHeight
        else
            imageScaleWidth).toDouble()).toInt()
        var i = 2
        while (i <= imageScale) {
            i *= 2
        }
        options.inSampleSize = i
        image = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options)
        val toFileSize = 2090000
        if (image.byteCount > toFileSize) {
            val scale = Math.sqrt((toFileSize / image.byteCount).toDouble()).toFloat()
            val matrix = Matrix()
            matrix.postScale(scale, scale)
            image = Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)
        }
    } else {
        image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
    }
    parcelFileDescriptor.close()
    return image
}