package com.example.myrecipes

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.myrecipes.model.RecipeUiModel
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Recipe(
    recipe: RecipeUiModel,
    onClick: () -> Unit = {},
    onSwipe: () -> Unit = {}
) {
    val density = LocalDensity.current

    val dragState = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.START,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween()
        )
    }

    LaunchedEffect(dragState.currentValue) {
        if (dragState.currentValue == DragAnchors.END) {
            onSwipe()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp)
            .onSizeChanged { layoutSize ->
                dragState.updateAnchors(
                    DraggableAnchors {
                        DragAnchors.START at 0f
                        DragAnchors.END at layoutSize.width.toFloat()
                    }
                )
            }
            .offset {
                IntOffset(
                    x = dragState
                        .requireOffset()
                        .roundToInt(),
                    y = 0
                )
            }
            .anchoredDraggable(
                state = dragState,
                orientation = Orientation.Horizontal
            )
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = recipe.title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp, 0.dp)
        )
        Text(
            text = recipe.description,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp, 0.dp)
        )
    }
}

private enum class DragAnchors {
    START,
    END,
}
