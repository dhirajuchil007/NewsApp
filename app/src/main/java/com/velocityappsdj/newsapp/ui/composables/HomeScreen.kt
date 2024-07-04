package com.velocityappsdj.newsapp.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.velocityappsdj.newsapp.network.models.Article
import com.velocityappsdj.newsapp.ui.states.NewsState
import com.velocityappsdj.newsapp.ui.theme.NewsAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: NewsState,
    query: String,
    onQueryChange: (String) -> Unit,
    navigateToDetails: (Article) -> Unit
) {
    Scaffold { _ ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    16.dp
                )
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicTextField(
                value = query,
                textStyle = MaterialTheme.typography.bodyLarge.copy(Color.Gray),
                onValueChange = {
                    onQueryChange(it)
                },
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    .fillMaxWidth()
                    .padding(16.dp),
                decorationBox = { innerTextField ->
                    if (query.isEmpty())
                        Text(
                            "Search news", style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Gray
                            )
                        )
                    else
                        innerTextField()
                }

            )

            when (state) {
                is NewsState.Success -> {

                    ArticlesList(
                        articles = state.articles.collectAsLazyPagingItems(),
                        navigateToDetails = navigateToDetails
                    )
                }

                is NewsState.Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = state.msg,
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                is NewsState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable

private fun HomeScreenPreview() {
    NewsAppTheme {
        HomeScreen(state = NewsState.Error("123"), query = "", onQueryChange = {}) {

        }
    }
}