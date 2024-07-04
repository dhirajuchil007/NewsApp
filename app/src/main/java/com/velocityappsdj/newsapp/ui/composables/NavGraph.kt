package com.velocityappsdj.newsapp.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.velocityappsdj.newsapp.network.models.Article
import com.velocityappsdj.newsapp.ui.viewmodel.NewsViewModel
import kotlinx.serialization.Serializable

@Composable
fun NavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = HomeScreenRoute
    ) {
        composable<HomeScreenRoute> {
            val viewModel = hiltViewModel<NewsViewModel>()
            HomeScreen(
                state = viewModel.state.value,
                query = viewModel.query.collectAsState().value,
                onQueryChange = viewModel::onQueryChanged
            ) {
                navController.navigate(DetailsRoute(it.title))
            }
        }
        composable<DetailsRoute> {
            val article = it.toRoute<DetailsRoute>()
            DetailsScreen(articleTitle = article.articleTitle)
        }

    }


}


@Serializable
object HomeScreenRoute

@Serializable
data class DetailsRoute(val articleTitle: String)