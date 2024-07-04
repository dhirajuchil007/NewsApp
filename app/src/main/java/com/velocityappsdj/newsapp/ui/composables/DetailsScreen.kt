package com.velocityappsdj.newsapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.velocityappsdj.newsapp.network.models.Article

@Composable
fun DetailsScreen(modifier: Modifier = Modifier, articleTitle: String) {
    Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(
            articleTitle, style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}