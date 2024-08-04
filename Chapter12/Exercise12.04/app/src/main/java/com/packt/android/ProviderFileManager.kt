package com.packt.android

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ProviderFileManager(
    private val context: Context,
    private val fileHelper: FileHelper,
    private val contentResolver: ContentResolver,
    private val mediaContentHelper: MediaContentHelper
) {

    fun generatePhotoUri(time: Long): FileInfo {
        val name = "img_$time.jpg"
        val file = File(
            context.getExternalFilesDir(fileHelper.getPicturesFolder()),
            name
        )
        return FileInfo(
            fileHelper.getUriFromFile(file),
            file,
            name,
            fileHelper.getPicturesFolder(),
            "image/jpeg"
        )
    }

    fun generateVideoUri(time: Long): FileInfo {
        val name = "video_$time.mp4"
        val file = File(
            context.getExternalFilesDir(fileHelper.getVideosFolder()),
            name
        )
        return FileInfo(
            fileHelper.getUriFromFile(file),
            file,
            name,
            fileHelper.getVideosFolder(),
            "video/mp4"
        )
    }

    suspend fun insertImageToStore(fileInfo: FileInfo?) {
        fileInfo?.let {
            insertToStore(
                fileInfo,
                mediaContentHelper.getImageContentUri(),
                mediaContentHelper.generateImageContentValues(it)
            )
        }
    }

    suspend fun insertVideoToStore(fileInfo: FileInfo?) {
        fileInfo?.let {
            insertToStore(
                fileInfo,
                mediaContentHelper.getVideoContentUri(),
                mediaContentHelper.generateVideoContentValues(it)
            )
        }
    }

    private suspend fun insertToStore(
        fileInfo: FileInfo,
        contentUri: Uri,
        contentValues: ContentValues
    ) {
        withContext(Dispatchers.IO) {
            val insertedUri = contentResolver.insert(contentUri, contentValues)
            insertedUri?.let {
                val inputStream = contentResolver.openInputStream(fileInfo.uri)
                val outputStream = contentResolver.openOutputStream(insertedUri)
                outputStream?.let {
                    inputStream?.copyTo(outputStream)
                }
            }
        }
    }
}