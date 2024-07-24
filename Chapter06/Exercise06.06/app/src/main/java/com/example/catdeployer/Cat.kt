package com.example.catdeployer

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.catdeployer.model.CatUiModel
import com.example.catdeployer.model.Gender
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Cat(cat: CatUiModel, onClick: () -> Unit = {}, onSwipe: () -> Unit = {}) {
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
        if (cat.imageUrl.isEmpty()) {
            Spacer(modifier = Modifier.size(64.dp))
        } else {
            LoadedImage(
                imageUrl = cat.imageUrl,
                modifier = Modifier.size(64.dp)
            )
        }
        Column {
            Text(text = cat.name)
            Text(text = cat.biography)
        }
    }
}

private enum class DragAnchors {
    START,
    END,
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Cat(
        CatUiModel(
            gender = Gender.MALE,
            name = "Oliver",
            biography = "An expert slacker.",
            imageUrl = ""
        )
    )
}

