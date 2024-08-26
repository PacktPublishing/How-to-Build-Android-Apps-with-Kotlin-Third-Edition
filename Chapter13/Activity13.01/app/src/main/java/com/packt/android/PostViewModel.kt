package com.packt.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.android.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {

    private val _state = MutableStateFlow<State>(State())
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            _state.emit(State(postRepository.getPosts().map {
                PostUi(title = it.title, body = it.body)
            }))
        }
    }


    data class State(
        val posts: List<PostUi> = emptyList()
    )

    data class PostUi(
        val title: String = "",
        val body: String = ""
    )
}