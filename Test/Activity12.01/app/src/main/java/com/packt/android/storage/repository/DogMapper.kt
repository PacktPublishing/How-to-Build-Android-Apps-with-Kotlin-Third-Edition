package com.packt.android.storage.repository

import com.packt.android.api.Dog
import com.packt.android.storage.room.DogEntity

class DogMapper {

    fun mapServiceToEntity(dog: Dog): List<DogEntity> = dog.urls.map {
        DogEntity(0, it)
    }

    fun mapEntityToUi(dogEntity: DogEntity): DogUi = DogUi(dogEntity.url)
}