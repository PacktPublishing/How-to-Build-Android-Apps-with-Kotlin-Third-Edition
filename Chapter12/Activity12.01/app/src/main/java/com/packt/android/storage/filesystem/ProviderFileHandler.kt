package com.packt.android.storage.filesystem

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream

class ProviderFileHandler(
    private val context: Context,
    private val fileToUriMapper: FileToUriMapper
) {

    suspend fun writeStream(name: String, inputStream: InputStream) {
        withContext(Dispatchers.IO) {
            val fileToSave = File(context.externalCacheDir, name)
            val outputStream = context.contentResolver.openOutputStream(
                fileToUriMapper.getUriForFile(context, fileToSave),
                "rw"
            )
            outputStream?.let {
                inputStream.copyTo(outputStream)
            }
        }
    }
}