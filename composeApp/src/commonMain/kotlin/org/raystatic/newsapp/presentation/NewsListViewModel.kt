package org.raystatic.newsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.raystatic.newsapp.domain.usecases.GetNewsUseCase
import org.raystatic.newsapp.presentation.model.NewsListUiState
import org.raystatic.newsapp.util.Result

class NewsListViewModel(
    private val getNewsUseCase: GetNewsUseCase
): ViewModel() {
//    private val viewModelScope = getViewModelScope() // Use the expected CoroutineScope

    private val _uiState = MutableStateFlow(NewsListUiState())
    val uiState: StateFlow<NewsListUiState> = _uiState.asStateFlow()

    init {
        loadNews(category = _uiState.value.selectedCategory, isNewCategory = true)
    }

    fun loadNews(category: String, isNewCategory: Boolean = false) {
        if (_uiState.value.isLoading) return // Prevent multiple simultaneous loads

        val currentPage = if (isNewCategory) 1 else _uiState.value.page

        _uiState.update {
            it.copy(
                isLoading = true,
                selectedCategory = category,
                page = currentPage,
                // Clear errors and articles if it's a new category search
                error = if (isNewCategory) null else it.error,
                articles = if (isNewCategory) emptyList() else it.articles
            )
        }

        viewModelScope.launch { // Perform network call on IO dispatcher
            when (val result = getNewsUseCase(category, currentPage)) {
                is Result.Success -> {
                    val newArticles = result.data
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            articles = if (isNewCategory) newArticles else it.articles + newArticles,
                            page = if (newArticles.isNotEmpty()) currentPage + 1 else currentPage,
                            canLoadMore = newArticles.isNotEmpty() // Basic check, could be more robust
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.exception.message ?: "An unknown error occurred",
                            canLoadMore = !isNewCategory // Don't allow pagination on error if it's not the first load
                        )
                    }
                }
            }
        }
    }

    fun loadMoreNews() {
        if (_uiState.value.canLoadMore && !_uiState.value.isLoading) {
            loadNews(category = _uiState.value.selectedCategory, isNewCategory = false)
        }
    }

    fun onCategorySelected(category: String) {
        if (category != _uiState.value.selectedCategory) {
            loadNews(category = category, isNewCategory = true)
        }
    }
}