package com.droidknights.app.core.testing.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun SamplePreview() {
    Column {
        Text(text = "Sample Text")
        Text(text = "Another Sample Text")
        Button(onClick = {}) {
            Text(text = "Click Me")
        }
        Box(modifier = Modifier
            .size(100.dp)
            .background(Color.Blue))
    }
}
