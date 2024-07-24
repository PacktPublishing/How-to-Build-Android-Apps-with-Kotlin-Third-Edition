package com.example.catdeployer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catdeployer.model.CatUiModel
import com.example.catdeployer.model.Gender

@Composable
fun Cat(cat: CatUiModel, onClick: () -> Unit = {}) {
    Row(modifier = Modifier.clickable {
        onClick()
    }) {
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

