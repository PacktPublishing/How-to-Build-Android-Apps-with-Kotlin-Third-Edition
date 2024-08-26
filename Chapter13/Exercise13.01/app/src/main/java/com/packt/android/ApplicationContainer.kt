package com.packt.android

import java.util.Random

class ApplicationContainer {
    val numberRepository: NumberRepository = NumberRepositoryImpl(Random())
}
