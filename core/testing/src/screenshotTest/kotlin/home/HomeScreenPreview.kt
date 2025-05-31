package com.droidknights.app.core.testing.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.droidknights.app.core.designsystem.theme.KnightsTheme
import com.droidknights.app.feature.home.HomeScreen

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    KnightsTheme {
        HomeScreen(
            onSessionClick = {},
            onContributorClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
