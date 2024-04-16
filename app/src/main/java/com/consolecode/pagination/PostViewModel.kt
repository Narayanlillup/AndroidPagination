package com.consolecode.pagination

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostViewModel : ViewModel() {
    private val apiRepo = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRepo::class.java)

    private var currentPage = 1


    private val _posts = MutableStateFlow<List<PostData>>(emptyList())
    val posts: StateFlow<List<PostData>> = _posts

    private val _loading = mutableStateOf(false)

    val loading: State<Boolean> = _loading
    init {
        loadNextPage()
    }
    val PAGE_SIZE = 10
    fun loadNextPage() {
        if (!loading.value) {
            _loading.value = true
            viewModelScope.launch {
                val newPosts = apiRepo.getPosts(currentPage, PAGE_SIZE)
                _posts.value += newPosts
                currentPage++
                _loading.value = false
            }
        }
    }
}