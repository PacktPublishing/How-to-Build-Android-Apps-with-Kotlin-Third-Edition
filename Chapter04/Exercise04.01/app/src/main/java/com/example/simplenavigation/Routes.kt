// Sealed class to define the app routes without any parameters
package com.example.simplenavigation

sealed class Routes(val route: String) {
    data object Home : Routes("HOME")
    data object Red : Routes("RED")
    data object Green : Routes("GREEN")
    data object Blue : Routes("BLUE")
}