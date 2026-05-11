package com.example.pokemonapp.util

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

sealed interface RequestState<out T> {
    data object Idle : RequestState<Nothing>
    data object Loading : RequestState<Nothing>
    data class Success<out T>(val data: T) : RequestState<T>
    data class Error(val message: String) : RequestState<Nothing>

    fun isIdle(): Boolean = this is Idle
    fun isLoading(): Boolean = this is Loading
    fun isError(): Boolean = this is Error
    fun isSuccess(): Boolean = this is Success

    fun getSuccessData(): T = (this as Success).data
    fun getSuccessDataOrNull(): T? = when (this) {
        is Success -> this.data
        else -> null
    }

    fun getErrorMessage(): String = (this as Error).message
    fun getErrorMessageOrNull(): String? = when (this) {
        is Error -> this.message
        else -> null
    }
}

@Composable
fun <T> RequestState<T>.DisplayResult(
    modifier: Modifier = Modifier,
    onIdle: (@Composable () -> Unit)? = null,
    onLoading: (@Composable () -> Unit)? = null,
    onError: (@Composable (String) -> Unit)? = null,
    onSuccess: (@Composable (T) -> Unit),
    backgroundColor: Color? = null,
) {
    AnimatedContent(
        modifier = modifier.background(color = backgroundColor ?: Color.Unspecified),
        targetState = this,
        label = "Content Animation"
    ) { state ->
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is RequestState.Idle -> {
                    onIdle?.invoke()
                }

                is RequestState.Loading -> {
                    onLoading?.invoke()
                }

                is RequestState.Success -> {
                    onSuccess.invoke(state.data)
                }

                is RequestState.Error -> {
                    onError?.invoke(state.message)
                }
            }
        }
    }
}