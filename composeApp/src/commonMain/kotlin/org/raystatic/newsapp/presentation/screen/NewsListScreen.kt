package org.raystatic.newsapp.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.raystatic.newsapp.domain.model.NewsArticle
import org.raystatic.newsapp.presentation.NewsListViewModel
import org.raystatic.newsapp.presentation.components.NewsItemComposable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel = koinInject(), // Inject ViewModel using Koin for Compose
    onArticleClick: (NewsArticle) -> Unit // Callback for when an article is clicked
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    var showCategoryMenu by remember { mutableStateOf(false) }
    val categories = listOf(
        "technology",
        "business",
        "entertainment",
        "health",
        "science",
        "sports"
    ) // Example categories

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NewsApp (${uiState.selectedCategory.replaceFirstChar { it.titlecase() }})") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    Box {
                        IconButton(onClick = { showCategoryMenu = true }) {
                            Icon(Icons.Filled.Category, contentDescription = "Select Category")
                        }
                        DropdownMenu(
                            expanded = showCategoryMenu,
                            onDismissRequest = { showCategoryMenu = false }
                        ) {
                            categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category.replaceFirstChar { it.titlecase() }) },
                                    onClick = {
                                        viewModel.onCategorySelected(category)
                                        showCategoryMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from Scaffold
        ) {
            if (uiState.isLoading && uiState.articles.isEmpty()) {
                // Show a full-screen loader only when initially loading and no articles are present
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null && uiState.articles.isEmpty()) {
                // Show a full-screen error only if articles are empty
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.Error,
                            contentDescription = "Error",
                            modifier = Modifier.padding(bottom = 8.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "Error: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            // Retry fetching the initial list for the selected category
                            viewModel.loadNews(
                                category = uiState.selectedCategory,
                                isNewCategory = true
                            )
                        }) {
                            Text("Retry")
                        }
                    }
                }
            } else {
                // Display the list of articles
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f), // Takes up remaining space
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(
                        items = uiState.articles,
                        key = { article ->
                            article.url ?: article.title ?: article.hashCode()
                        } // Unique key for items
                    ) { article ->
                        NewsItemComposable(
                            article = article,
                            onItemClick = onArticleClick
                        )
                    }

                    // Loading indicator or "Load More" button at the bottom
                    if (uiState.isLoading && uiState.articles.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    } else if (uiState.error != null && uiState.articles.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        "Error loading more: ${uiState.error}",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                    Button(onClick = { viewModel.loadMoreNews() }) {
                                        Text("Retry Load More")
                                    }
                                }
                            }
                        }
                    } else if (uiState.canLoadMore && uiState.articles.isNotEmpty()) {
                        item {
                            Button(
                                onClick = { viewModel.loadMoreNews() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                enabled = !uiState.isLoading // Disable button while loading
                            ) {
                                Text("Load More")
                            }
                        }
                    }
                }
            }
        }
    }

    // Optional: Auto-load more when near the end of the list (basic implementation)
    // A more sophisticated approach would involve checking derivedStateOf { listState.layoutInfo ... }
    LaunchedEffect(listState.canScrollForward, uiState.isLoading, uiState.canLoadMore) {
        if (!listState.canScrollForward && !uiState.isLoading && uiState.canLoadMore && uiState.articles.isNotEmpty()) {
            viewModel.loadMoreNews()
        }
    }
}