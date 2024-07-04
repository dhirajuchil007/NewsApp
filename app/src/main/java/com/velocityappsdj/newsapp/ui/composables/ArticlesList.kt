package com.velocityappsdj.newsapp.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.velocityappsdj.newsapp.network.models.Article
import com.velocityappsdj.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    navigateToDetails: (Article) -> Unit
) {
    val handlePagingResult = handlePagingResult(articles)
    if (handlePagingResult)
        LazyColumn {
            items(articles.itemCount) { index ->
                articles[index]?.let {
                    ArticlesListItem(article = it, navigateToDetails = navigateToDetails)
                }
            }
        }

}

@Composable
fun ArticlesListItem(
    modifier: Modifier = Modifier,
    article: Article,
    navigateToDetails: (Article) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                navigateToDetails(article)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.clip(RoundedCornerShape(8.dp))) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp),
            )
        }


        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.height(100.dp)) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.getTimeAgo(),
                style = MaterialTheme.typography.bodySmall.copy(Color.Gray),

                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

    }

}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>
): Boolean {
    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            false
        }

        error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                error.error.message?.let {
                    Text(
                        text = it, style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            false
        }

        articles.itemCount == 0 -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No articles found",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            false
        }

        else -> true
    }
}


@Preview(showBackground = true)
@Composable
private fun ArticleListItemPreview() {
    NewsAppTheme {
        ArticlesListItem(
            article = Article(
                publishedAt = "2024-07-03T12:40:00Z",
                urlToImage = "https://place.dog/300",
                title = "My article title is ver long and tiresome",
                content = "kjfkadfh adfih asdfkl kdsaflk adls f" +
                        "d osailh dafs"
            )
        ){}
    }
}