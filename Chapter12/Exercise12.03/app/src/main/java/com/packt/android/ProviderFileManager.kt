package com.packt.android

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream


class ProviderFileManager(
    private val context: Context,
    private val fileToUriMapper: FileToUriMapper
) {

    private fun getDocsFolder(): File {
        val folder = File(context.getExternalFilesDir(null), "docs")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        return folder
    }

    suspend fun writeStream(name: String, inputStream: InputStream) {
        withContext(Dispatchers.IO) {
            val fileToSave = File(getDocsFolder(), name)
            val outputStream = context.contentResolver.openOutputStream(
                fileToUriMapper.getUriFromFile(
                    context,
                    fileToSave
                ), "rw"
            )
            outputStream?.let {
                inputStream.copyTo(outputStream)
            }
        }
    }

    suspend fun writeStreamFromUri(inputStream: InputStream, uri: Uri) {
        withContext(Dispatchers.IO) {
            val outputStream = context.contentResolver.openOutputStream(uri, "rw")
            outputStream?.let {
                inputStream.copyTo(outputStream)
            }
        }
    }
}
