import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

// Define the Navigation Items
sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Home : NavigationItem("home", Icons.Default.Home, "Home")
    object Shopping : NavigationItem("recent", Icons.Default.ShoppingCart, "Recent")
    object Favorites : NavigationItem("favorites", Icons.Default.Favorite, "Favorites")
    object Calendar : NavigationItem("archive", Icons.Default.DateRange, "Archive")
    object Bin : NavigationItem("bin", Icons.Default.Delete, "Bin")
}