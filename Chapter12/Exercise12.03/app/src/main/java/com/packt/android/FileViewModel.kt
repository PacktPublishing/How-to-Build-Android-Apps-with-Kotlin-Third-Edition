package com.packt.android

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FileViewModel(
    private val assetFileManager: AssetFileManager,
    private val providerFileManager: ProviderFileManager
) : ViewModel() {

    fun copyUsingFileProvider() {
        viewModelScope.launch {
            providerFileManager.writeStream(
                "Copied.txt",
                assetFileManager.getMyAppFileInputStream()
            )
        }
    }

    fun copyUsingSaf(uri: Uri) {
        viewModelScope.launch {
            providerFileManager.writeStreamFromUri(
                assetFileManager.getMyAppFileInputStream(),
                uri
            )
        }
    }
}