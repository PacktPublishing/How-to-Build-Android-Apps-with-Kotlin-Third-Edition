import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

// Define the Navigation Items
sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Home : NavigationItem("home", Icons.Default.Home, "Home")
    object Shopping : NavigationItem("cart", Icons.Default.ShoppingCart, "Cart")
    object Favorites : NavigationItem("favorites", Icons.Default.Favorite, "Favorites")
    object Calendar : NavigationItem("calendar", Icons.Default.DateRange, "Calendar")
    object Bin : NavigationItem("bin", Icons.Default.Delete, "Bin")
}