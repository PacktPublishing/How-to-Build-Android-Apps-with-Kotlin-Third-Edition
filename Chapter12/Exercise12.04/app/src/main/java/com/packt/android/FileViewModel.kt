package com.packt.android

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FileViewModel(private val providerFileManager: ProviderFileManager) : ViewModel() {

    private var photoFileInfo: FileInfo? = null
    private var videoFileInfo: FileInfo? = null

    fun generatePhotoUri(time: Long): Uri {
        val info = providerFileManager.generatePhotoUri(time)
        photoFileInfo = info
        return info.uri
    }

    fun generateVideoUri(time: Long): Uri {
        val info = providerFileManager.generateVideoUri(time)
        videoFileInfo = info
        return info.uri
    }

    fun insertImageToStore() {
        viewModelScope.launch {
            providerFileManager.insertImageToStore(photoFileInfo)
        }
    }

    fun insertVideoToStore() {
        viewModelScope.launch {
            providerFileManager.insertVideoToStore(videoFileInfo)
        }
    }
}